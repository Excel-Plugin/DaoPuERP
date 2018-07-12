package trapx00.lightx00.shared.dataservicestub.inventorydataservice;

import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseRefundBillDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.PurchaseRefundBillPo;
import trapx00.lightx00.shared.queryvo.PurchaseRefundBillQueryVo;

import java.rmi.RemoteException;

public class PurchaseRefundBillDataServiceStub implements PurchaseRefundBillDataService {
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
        return null;
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
        return null;
    }

    /**
     * Queries PurchaseRefundBill.
     *
     * @param query query condition
     * @return PurchaseRefundBillPos that match the query condition
     */
    @Override
    public PurchaseRefundBillPo[] query(PurchaseRefundBillQueryVo query) {
        return new PurchaseRefundBillPo[0];
    }

    @Override
    public ResultMessage submit(PurchaseRefundBillPo purchaseBill) {
        return ResultMessage.Success;
    }

    /**
     * get the id of the next purchase refund bill
     *
     * @return get the id of the next purchase refund bill
     */
    @Override
    public String getId() {
        return null;
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
        return ResultMessage.Success;
    }
}
