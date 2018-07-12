package trapx00.lightx00.server.data.inventorydata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.inventorydata.factory.PurchaseRefundBillDataDaoFactory;
import trapx00.lightx00.server.data.util.CommonBillDataController;
import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseRefundBillDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.PurchaseRefundBillPo;
import trapx00.lightx00.shared.queryvo.PurchaseRefundBillQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PurchaseRefundBillDataController extends UnicastRemoteObject implements PurchaseRefundBillDataService {

    private Dao<PurchaseRefundBillPo, String> purchaseRefundBillDao = PurchaseRefundBillDataDaoFactory.getPurchaseRefundBillDao();
    private CommonBillDataController<PurchaseRefundBillPo> commonBillDataController = new CommonBillDataController<PurchaseRefundBillPo>(purchaseRefundBillDao, this);

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
    public PurchaseRefundBillDataController() throws RemoteException {
    }

    /**
     * Activates a PurchaseRefundBill.
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
     * Abandons a PurchaseRefundBill.
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
     * Queries PurchaseRefundBill.
     *
     * @param query query condition
     * @return PurchaseRefundBillPos that match the query condition
     */
    @Override
    public PurchaseRefundBillPo[] query(PurchaseRefundBillQueryVo query) {
        List<PurchaseRefundBillPo> result = commonBillDataController.query(query);
        return result.toArray(new PurchaseRefundBillPo[result.size()]);
    }

    /**
     * submit a purchaseRefundBill
     *
     * @param purchaseRefundBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(PurchaseRefundBillPo purchaseRefundBill) {
        return commonBillDataController.submit(purchaseRefundBill);
    }

    /**
     * get the id of the next purchase refund bill
     *
     * @return get the id of the next purchase refund bill
     */
    @Override
    public String getId() {
        return commonBillDataController.getId("JHTHD");
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
