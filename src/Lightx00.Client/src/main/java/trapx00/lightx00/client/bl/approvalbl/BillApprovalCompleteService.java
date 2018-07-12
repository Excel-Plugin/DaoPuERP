package trapx00.lightx00.client.bl.approvalbl;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;

public interface BillApprovalCompleteService {
    /**
     * When bill is approved, this method is called to modify the state of the bill.
     * @param billId id for the bill
     * @param state newState. Only BillState.Approved and BillState.Rejected are allowed.
     * @return whether the operation is done successfully
     */
    ResultMessage approvalComplete(String billId, BillState state);
}
