package trapx00.lightx00.client.bl.approvalbl;

import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.shared.po.ResultMessage;

public interface ApprovalRequest {
    /**
     * Submitted bills request approval.
     * @param bill bill has been submitted
     * @return whether the operation is done successfully
     */
    ResultMessage requestApproval(BillVo bill);
}
