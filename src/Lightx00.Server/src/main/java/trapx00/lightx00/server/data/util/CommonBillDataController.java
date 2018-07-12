package trapx00.lightx00.server.data.util;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.exception.database.*;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillPo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.queryvo.BaseQueryVo;
import trapx00.lightx00.shared.util.BillHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class CommonBillDataController<Po extends BillPo> {
    private static final int MAX_BILL_NUM_FOR_A_DAY = 99999;
    private Dao<Po,String> dao;
    private Object delegate = this;
    private ServerLogService logService = ServerLogServiceFactory.getService();

    public CommonBillDataController(Dao<Po, String> dao) {
        this.dao = dao;
    }

    /**
     * Constructor with delegate. Pass in (this) directly to log with actual caller.
     * @param dao dao
     * @param delegate delegate object. Pass in (this) directly to log with actual caller.
     */
    public CommonBillDataController(Dao<Po, String> dao, Object delegate) {
        this.dao = dao;
        this.delegate = delegate;
    }

    private void handleSQLException(SQLException e) {
        logService.printLog(delegate, "failed at a database operation. Error message: " + e.getMessage());
        throw new DbSqlException(e);
    }

    /**
     * This function is a util to assert whether the id is used.
     * If assertExists is true but the id is not used, a IdNotExistsException is thrown.
     * If assertExists is false but the id is used, a IdExistsException is thrown.
     * If assertExists is true and the id is used, the bill for the id is returned and no exception is thrown.
     * If assertExists is false and the id is not used, null will be returned and no exception is thrown.
     * @param id id
     * @param assertExists assert exists flag
     * @return BillPo if assertExists is true and the id is used.
     */
    public Po assertIdExistence(String id, boolean assertExists) {
        try {
            Po cashBillPo = dao.queryForId(id);
            boolean actualExistence = cashBillPo != null;
            if (actualExistence && !assertExists) {
                throw new IdExistsException(id);
            }
            if (!actualExistence && assertExists) {
                throw new IdNotExistsException(id);
            }
            return cashBillPo;
        } catch (SQLException e) {
            handleSQLException(e);
            return null;
        }
    }

    /**
     * Submits a bill or save it as a draft.
     * If there is a bill with the same id as passed-in parameter do,
     *    if the existing bill is in BillState.Draft state, it will be updated/replaced by parameter.
     *    otherwise a IdExistsException would be thrown.
     *
     * @param bill bill
     * @return whether the operation is done successfully
     */
    public ResultMessage submit(Po bill) {
        try {
            Po po = dao.queryForId(bill.getId());
            if (po != null && po.getState().equals(BillState.Draft)) {
                dao.deleteById(po.getId());
                logService.printLog(delegate,String.format("updated a draft %s (id: %s). New content: %s", bill.getState().toString(), bill.getId(), bill.toString()));
            } else if (po != null) {
                throw new IdExistsException(bill.getId());
            }
            dao.create(bill);
            bill.setId(dao.extractId(bill));
            logService.printLog(delegate, String.format("created a %s (id: %s). Content: %s", bill.getBillType().toString(), bill.getId(), bill.toString()));
            return ResultMessage.Success;
        } catch (SQLException e) {
            handleSQLException(e);
            return ResultMessage.Failure;
        }
    }

    /**
     * Activates a bill.
     * The bill must be in BillState.Approved state.
     * Otherwise a BillInvalidStateException will be thrown.
     *
     * @param id id for the bill that have been approved of
     * @return whether the operation is done successfully
     */
    public ResultMessage activate(String id) {
        try {
            Po po = assertIdExistence(id,true);
            if (po.getState().equals(BillState.Approved)) {
                po.setState(BillState.Activated);
                dao.update(po);
                logService.printLog(delegate, String.format("activated a bill (id: %s)", id));
                return ResultMessage.Success;
            } else {
                throw new BillInvalidStateException(po.getState(), BillState.WaitingForApproval);
            }
        } catch (SQLException e) {
            handleSQLException(e);
            return ResultMessage.Failure;
        }
    }

    /**
     * Abandons a bill.
     * If a Bill is in BillState.Draft, it will be deleted.
     * If a Bill is in BillState.Rejected/Approved/WaitingForApproval, it will be changed as Abandoned.
     * If a bill is in other state, a BillInvalidStateException will be thrown.
     * @param id id for the CashBill to be abandoned
     * @return whether the operation is done successfully
     */
    public ResultMessage abandon(String id) {
        try {
            Po po = assertIdExistence(id,true);
            switch (po.getState()) {
                case Draft:
                    dao.deleteById(id);
                    logService.printLog(delegate, String.format("deletes a draft %s (id: %s)", po.getBillType(), id));
                    return ResultMessage.Success;
                case Rejected:
                case Approved:
                case WaitingForApproval:
                    BillState previousState = po.getState();
                    po.setState(BillState.Abandoned);
                    dao.update(po);
                    logService.printLog(delegate, String.format("marked a %s (id: %s) as Abandoned (previously %s)", po.getBillType(), po.getId(), previousState));
                    return ResultMessage.Success;
                default:
                    throw new BillInvalidStateException(po.getState(),BillState.Draft, BillState.Rejected, BillState.Approved,BillState.WaitingForApproval);
            }
        } catch (SQLException e) {
            handleSQLException(e);
            return ResultMessage.Failure;
        }
    }


    /**
     * Queries bills.
     *
     * @param query query condition
     * @return bills that match the query condition
     */
    @SuppressWarnings("unchecked")
    public <Q extends BaseQueryVo> List<Po> query(Q query) {
        try {
            List<Po> results = dao.query(query.prepareQuery(dao));
            logService.printLog(delegate, String.format("queried bills and got %d results.", results.size()));
            return results;
        } catch (SQLException e) {
            handleSQLException(e);
            return new ArrayList<>();
        }
    }

    /**
     * Changes the state of a bill if approval is completed.
     * @param billId id for the bill
     * @param billState new bill state. Only Approved and Rejected is allowed.
     * @return whether the operation is done successfully.
     */
    public ResultMessage approvalComplete(String billId, BillState billState) {
        try {
            Po bill = assertIdExistence(billId, true);
            if (!bill.getState().equals(BillState.WaitingForApproval)) {
                throw new BillInvalidStateException(bill.getState(), BillState.WaitingForApproval);
            }
            switch (billState) {
                case Approved:
                case Rejected:
                    bill.setState(billState);
                    break;
                default:
                    throw new BillInvalidStateException(bill.getState(), BillState.Rejected, BillState.Approved);
            }
            logService.printLog(this, String.format("changed bill (id: %s) state to %s", billId, billState.toString()));
            dao.update(bill);
            return ResultMessage.Success;
        } catch (SQLException e) {
            handleSQLException(e);
            return ResultMessage.Failure;
        }
    }


    /**
     * Gets the id for the next bill.
     * If there are already 99999 bills for this day, a NoMoreBillException will be thrown.
     *
     * @param leadingText leading text for the bill. For instance 现金费用单's leading text is XJFYD. It can be found in the doc
     * @return id for the next bill
     */
    public String getId(String leadingText) {
        try {
            OptionalInt maxId = dao.queryBuilder().selectColumns("id").query().stream()
                .map(BillPo::getId)
                .filter(x -> x.split("-")[1].equals(BillHelper.currentDateStringForBill()))
                .map(x -> x.split("-")[2])
                .mapToInt(Integer::parseInt)
                .max();
            if (maxId.orElse(-1) == MAX_BILL_NUM_FOR_A_DAY) {
                logService.printLog(delegate, "got a new id and it has been full.");
                throw new NoMoreBillException();
            }
            String newId = leadingText + "-" + BillHelper.currentDateStringForBill() + "-" + BillHelper.formatId(maxId.orElse(0) + 1);
            logService.printLog(delegate, "got a new id " + newId);
            return newId;
        } catch (SQLException e) {
            handleSQLException(e);
            return "";
        }
    }


}
