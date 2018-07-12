package trapx00.lightx00.client.blservicestub.approvalblservice;

import trapx00.lightx00.client.blservice.approvalblservice.AuditBlService;
import trapx00.lightx00.client.vo.manager.AuditIdVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.AuditIdQueryVo;

import java.util.Date;

public class AuditBlServiceStub implements AuditBlService {
    /**
     * Reject to approve the bill.
     * @param billInfo bill to be audited
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage reject(AuditIdVo billInfo) {
        return ResultMessage.Success;
    }

    /**
     * Approve the bill.
     * @param billInfo bill to be audited
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage pass(AuditIdVo billInfo) {
        return ResultMessage.Success;
    }

    /**
     * Filter bills.
     * @param query the filter conditions
     * @return array of bills which match the conditions
     */
    @Override
    public AuditIdVo[] query(AuditIdQueryVo query) {
        return new AuditIdVo[]{new AuditIdVo("XJFYD-20171112-00001",new Date())
        };
    }

}
