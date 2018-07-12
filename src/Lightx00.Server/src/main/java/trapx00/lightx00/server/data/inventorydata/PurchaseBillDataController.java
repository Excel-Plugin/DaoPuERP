package trapx00.lightx00.server.data.inventorydata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.inventorydata.factory.PurchaseBillDataDaoFactory;
import trapx00.lightx00.server.data.util.CommonBillDataController;
import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseBillDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.PurchaseBillPo;
import trapx00.lightx00.shared.queryvo.PurchaseBillQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PurchaseBillDataController extends UnicastRemoteObject implements PurchaseBillDataService {

    private Dao<PurchaseBillPo, String> purchaseBillDao = PurchaseBillDataDaoFactory.getPurchaseBillDao();
    private CommonBillDataController<PurchaseBillPo> commonBillDataController = new CommonBillDataController<PurchaseBillPo>(purchaseBillDao, this);

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
    public PurchaseBillDataController() throws RemoteException {
    }

    /**
     * Activates a PurchaseBill.
     * The bill must be in BillState.WaitingForApproval state.
     * Otherwise a BillInvalidStateException will be thrown.
     *
     * @param id id for the bill that have been approved of
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage activate(String id) {
        return commonBillDataController.activate(id);
    }

    /**
     * Abandons a PurchaseBill.
     * If a Bill is in BillState.Draft, it will be deleted.
     * If a Bill is in BillState.Rejected/Approved/WaitingForApproval, it will be changed as Abandoned.
     * If a bill is in other state, a BillInvalidStateException will be thrown.
     *
     * @param id id for the bill to be abandoned
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(String id) {
        return commonBillDataController.abandon(id);
    }

    /**
     * Queries PurchaseBill.
     *
     * @param query query condition
     * @return PurchaseBillPos that match the query condition
     */
    @Override
    public PurchaseBillPo[] query(PurchaseBillQueryVo query) {
        List<PurchaseBillPo> result = commonBillDataController.query(query);
        return result.toArray(new PurchaseBillPo[result.size()]);
    }

    /**
     * submit a purchaseBill
     *
     * @param purchaseBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(PurchaseBillPo purchaseBill) {
        return commonBillDataController.submit(purchaseBill);
    }

    /**
     * get the id of the next purchase bill
     *
     * @return the id of the next purchase bill
     */
    @Override
    public String getId() {
        return commonBillDataController.getId("JHD");
    }

    /**
     * Changes the state of a bill if approval is completed.
     *
     * @param billId    the id of the bill.
     * @param billState new bill state. Only Approved and Rejected is allowed.
     * @return whether the operation is done successfully.
     */
    @Override
    public ResultMessage approvalComplete(String billId, BillState billState) {
        return commonBillDataController.approvalComplete(billId, billState);
    }
}
