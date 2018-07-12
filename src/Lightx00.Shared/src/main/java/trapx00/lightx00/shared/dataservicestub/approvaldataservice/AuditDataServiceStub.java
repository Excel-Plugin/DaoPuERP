package trapx00.lightx00.shared.dataservicestub.approvaldataservice;

import trapx00.lightx00.shared.dataservice.approvaldataservice.AuditDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.AuditIdPo;
import trapx00.lightx00.shared.queryvo.AuditIdQueryVo;

import java.util.Date;

public class AuditDataServiceStub implements AuditDataService {
    /**
     * Filter some AuditIdPo.
     * @param query the filter conditions
     * @return array of AuditIdPo which match the conditions
     */
    @Override
    public AuditIdPo[] query(AuditIdQueryVo query) {
        return new AuditIdPo[]{new AuditIdPo("XJFYD-20171112-00001",new Date())};
    }

    /**
     * Delete AuditIdPo after rejecting the approval.
     * @param billInfo the corresponding AuditIdPo to the BillPo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage reject(AuditIdPo billInfo) {
        return ResultMessage.Success;
    }

    /**
     * Delete AuditIdPo after approving.
     * @param billInfo the corresponding AuditIdPo to the BillPo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage pass(AuditIdPo billInfo) {
        return ResultMessage.Success;
    }

    /**
     * Save the submitted bill.
     * @param id id of the bill has been submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage requestApproval(String id) {
        return ResultMessage.Success;
    }
}
