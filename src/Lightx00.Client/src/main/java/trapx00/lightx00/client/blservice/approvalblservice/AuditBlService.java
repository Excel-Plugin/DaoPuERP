package trapx00.lightx00.client.blservice.approvalblservice;

import trapx00.lightx00.client.vo.manager.AuditIdVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.AuditIdQueryVo;


public interface AuditBlService {
    /**
     * Reject to approve the bill.
     * @param bill bill to be audited
     * @return whether the operation is done successfully
     */
    ResultMessage reject(AuditIdVo bill);

    /**
     * Approve the bill.
     * @param bill bill to be audited
     * @return whether the operation is done successfully
     */
    ResultMessage pass(AuditIdVo bill);

    /**
     * Filter bills.
     * @param query the filter conditions
     * @return array of bills which match the conditions
     */
    AuditIdVo[] query(AuditIdQueryVo query);
}
