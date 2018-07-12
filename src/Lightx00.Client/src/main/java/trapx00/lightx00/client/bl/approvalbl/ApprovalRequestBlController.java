package trapx00.lightx00.client.bl.approvalbl;

import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.datafactory.approvaldataservicefactory.AuditDataServiceFactory;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.shared.dataservice.approvaldataservice.AuditDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.log.LogSeverity;

import java.rmi.RemoteException;

public class ApprovalRequestBlController implements ApprovalRequest {
    private AuditDataService dataService = AuditDataServiceFactory.getService();
    private LogService logService = LogServiceFactory.getLogService();

    /**
     * Submitted bills request approval.
     * @param bill bill has been submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage requestApproval(BillVo bill) {
        try {
            return dataService.requestApproval(bill.getId());
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format("审批单据并拒绝通过操作失败，原因网络原因，具体信息是%s，单据编号是%s",e.getMessage(), bill.getId()));
            throw new UncheckedRemoteException(e);
        }
    }
}
