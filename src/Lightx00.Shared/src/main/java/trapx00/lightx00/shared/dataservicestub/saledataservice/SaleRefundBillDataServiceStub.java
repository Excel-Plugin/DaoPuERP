package trapx00.lightx00.shared.dataservicestub.saledataservice;

import trapx00.lightx00.shared.dataservice.saledataservice.SaleRefundBillDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.SaleRefundBillPo;
import trapx00.lightx00.shared.queryvo.SaleRefundBillQueryVo;

import java.rmi.RemoteException;

public class SaleRefundBillDataServiceStub implements SaleRefundBillDataService {
    /**
     * Activates a SaleRefundBill.
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
     * Abandons a SaleRefundBill.
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
     * Queries SaleRefundBill.
     *
     * @param query query condition
     * @return SaleRefundBillPos that match the query condition
     */
    @Override
    public SaleRefundBillPo[] query(SaleRefundBillQueryVo query) {
        return new SaleRefundBillPo[0];
    }

    @Override
    public ResultMessage submit(SaleRefundBillPo saleRefundBill) {
        return ResultMessage.Success;
    }

    /**
     * get the id of the next sale refund bill
     *
     * @return the id of the next sale refund bill
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
