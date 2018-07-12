package trapx00.lightx00.server.data.inventorydata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.inventorydata.factory.InventoryDataDaoFactory;
import trapx00.lightx00.server.data.util.CommonBillDataController;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.dataservice.inventorydataservice.InventoryGiftDataService;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.InventoryGiftPo;
import trapx00.lightx00.shared.queryvo.InventoryGiftQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class InventoryGiftDataController extends UnicastRemoteObject implements InventoryGiftDataService {
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
    public InventoryGiftDataController() throws RemoteException {
    }

    private Dao<InventoryGiftPo, String> inventoryGiftDao = InventoryDataDaoFactory.getInventoryGiftDao();
    private Object delegate = this;
    private ServerLogService logService = ServerLogServiceFactory.getService();
    private CommonBillDataController<InventoryGiftPo> commonBillDataController = new CommonBillDataController<>(inventoryGiftDao, this);

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
    public InventoryGiftPo assertIdExistence(String id, boolean assertExists) {
        try {
            InventoryGiftPo inventoryGiftPo = inventoryGiftDao.queryForId(id);
            boolean actualExistence = inventoryGiftPo != null;
            if (actualExistence && !assertExists) {
                throw new IdExistsException(id);
            }
            if (!actualExistence && assertExists) {
                throw new IdNotExistsException(id);
            }
            return inventoryGiftPo;
        } catch (SQLException e) {
            handleSQLException(e);
            return null;
        }
    }

    /**
     * Submits a InventoryGiftBill or save it as a draft.
     * If there is a bill with the same id as passed-in parameter do,
     *    if the existing bill is in BillState.Draft state, it will be updated/replaced by parameter.
     *    otherwise a IdExistsException would be thrown.
     *
     * @param bill  InventoryGiftBill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(InventoryGiftPo bill)throws RemoteException  {
        return commonBillDataController.submit(bill);
    }

    /**
     * Activates a  InventoryGiftBill.
     * The bill must be in BillState.WaitingForApproval state.
     * Otherwise a BillInvalidStateException will be thrown.
     *
     * @param id id for the  InventoryGiftBill that have been approved of
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage activate(String id)throws RemoteException  {
        return commonBillDataController.activate(id);
    }

    /**
     * Abandons a  InventoryGiftBill.
     * If a Bill is in BillState.Draft, it will be deleted.
     * If a Bill is in BillState.Rejected/Approved/WaitingForApproval, it will be changed as Abandoned.
     * If a bill is in other state, a BillInvalidStateException will be thrown.
     * @param id id for the InventoryGiftill to be abandoned
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(String id)throws RemoteException  {
        return commonBillDataController.abandon(id);
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
    public String getId() throws RemoteException  {
        return commonBillDataController.getId("GIFT");
    }

    /**
     * Queries  InventoryGiftBill.
     *
     * @param query query condition
     * @return  InventoryGiftBillVos that match the query condition
     */
    @Override
    public InventoryGiftPo[] query(InventoryGiftQueryVo query)  {
        List<InventoryGiftPo> result = commonBillDataController.query(query);
        return result.toArray(new InventoryGiftPo[result.size()]);
    }


}
