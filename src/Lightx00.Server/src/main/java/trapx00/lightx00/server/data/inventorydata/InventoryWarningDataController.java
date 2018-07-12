package trapx00.lightx00.server.data.inventorydata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.commoditydata.factory.CommodityDataDaoFactory;
import trapx00.lightx00.server.data.inventorydata.factory.InventoryDataDaoFactory;
import trapx00.lightx00.server.data.util.CommonBillDataController;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.dataservice.inventorydataservice.InventoryWarningDataService;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.CommodityPo;
import trapx00.lightx00.shared.po.inventorystaff.InventoryDetailBillPo;
import trapx00.lightx00.shared.queryvo.InventoryBillQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class InventoryWarningDataController extends UnicastRemoteObject implements InventoryWarningDataService {
    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     * <p>
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public InventoryWarningDataController() throws RemoteException {
    }

    private Dao<InventoryDetailBillPo, String> dao = InventoryDataDaoFactory.getInventoryDetailBillDao();
    private CommonBillDataController<InventoryDetailBillPo> commonBillDataController = new CommonBillDataController<>(dao, this);
    private Object delegate = this;
    private ServerLogService logService = ServerLogServiceFactory.getService();


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
    public InventoryDetailBillPo assertIdExistence(String id, boolean assertExists) {
        try {
            InventoryDetailBillPo inventoryDetailBillPo = dao.queryForId(id);
            boolean actualExistence = inventoryDetailBillPo != null;
            if (actualExistence && !assertExists) {
                throw new IdExistsException(id);
            }
            if (!actualExistence && assertExists) {
                throw new IdNotExistsException(id);
            }
            return inventoryDetailBillPo;
        } catch (SQLException e) {
            handleSQLException(e);
            return null;
        }
    }
    /**
     * Submits a InventoryBill or save it as a draft.
     * If there is a bill with the same id as passed-in parameter do,
     *    if the existing bill is in BillState.Draft state, it will be updated/replaced by parameter.
     *    otherwise a IdExistsException would be thrown.
     *
     * @param bill InventoryBill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(InventoryDetailBillPo bill)  {
        return commonBillDataController.submit(bill);
    }

    @Override
    public ResultMessage modify(String id, double warningValue)   {

         Dao<CommodityPo, String> commodityDao = CommodityDataDaoFactory.getCommodityDao();
         ServerLogService logService = ServerLogServiceFactory.getService();

        try{
            CommodityPo po= commodityDao.queryForId(id);
            if (po != null) {
                commodityDao.update(po);
                logService.printLog(delegate,String.format("modify a commodity warningValue %s (id: %s).",po.getName() ,po.getId()));
                return ResultMessage.Success;
            }
            else{
                return ResultMessage.Failure;
            }
        }catch (SQLException e) {
            handleSQLException(e);
            return ResultMessage.Failure;
        }
    }

    /**
     * Activates a InventoryBill.
     * The bill must be in BillState.WaitingForApproval state.
     * Otherwise a BillInvalidStateException will be thrown.
     *
     * @param id id for the CashBill that have been approved of
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage activate(String id) {
        return commonBillDataController.activate(id);
    }

    /**
     * Abandons a InventoryBill.
     * If a Bill is in BillState.Draft, it will be deleted.
     * If a Bill is in BillState.Rejected/Approved/WaitingForApproval, it will be changed as Abandoned.
     * If a bill is in other state, a BillInvalidStateException will be thrown.
     * @param id id for the CashBill to be abandoned
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(String id)  {
        return commonBillDataController.abandon(id);
    }

    /**
     * Queries InventoryBill.
     *
     * @param query query condition
     * @return  InventoryBillVos that match the query condition
     */
    @Override
    public InventoryDetailBillPo[] query(InventoryBillQueryVo query)  {
        List<InventoryDetailBillPo> result = commonBillDataController.query(query);
        return result.toArray(new InventoryDetailBillPo[result.size()]);
    }

    /**
     * Changes the state of a bill if approval is completed.
     *
     * @param billId    the id of the bill.
     * @param billState new bill state. Only Approved and Rejected is allowed.
     * @return whether the operation is done successfully.
     */
    @Override
    public ResultMessage approvalComplete(String billId, BillState billState) throws RemoteException {
        return commonBillDataController.approvalComplete(billId, billState);
    }

    /**
     * Gets the id for the next bill.
     * If there are already 99999 bills for this day, a NoMoreBillException will be thrown.
     *
     * @return id for the next bill
     */
    @Override
    public String getId() {
        return commonBillDataController.getId("JKD");
    }
}
