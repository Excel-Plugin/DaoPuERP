package trapx00.lightx00.client.bl.approvalbl;

import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.EmployeeInfoFactory;
import trapx00.lightx00.client.bl.financebl.BillInfo;
import trapx00.lightx00.client.bl.financebl.factory.BillInfoBlFactory;
import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.bl.loginbl.CurrentUserService;
import trapx00.lightx00.client.bl.loginbl.factory.CurrentUserServiceFactory;
import trapx00.lightx00.client.bl.notificationbl.NotificationService;
import trapx00.lightx00.client.bl.notificationbl.factory.NotificationServiceFactory;
import trapx00.lightx00.client.blservice.approvalblservice.AuditBlService;
import trapx00.lightx00.client.datafactory.approvaldataservicefactory.AuditDataServiceFactory;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.manager.AuditIdVo;
import trapx00.lightx00.client.vo.notification.billapproval.BillApprovalNotificationVo;
import trapx00.lightx00.shared.dataservice.approvaldataservice.AuditDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.po.manager.AuditIdPo;
import trapx00.lightx00.shared.queryvo.AuditIdQueryVo;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AuditBlController implements AuditBlService {
    private LogService logService = LogServiceFactory.getLogService();
    private AuditDataService dataService = AuditDataServiceFactory.getService();
    private BillInfo billDetailService = BillInfoBlFactory.getBillInfo();
    private NotificationService notificationService = NotificationServiceFactory.getNotificationService();
    private CurrentUserService currentUserService = CurrentUserServiceFactory.getCurrentUserService();
    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();

    private AuditIdVo fromPoToVo(AuditIdPo po) {
        return new AuditIdVo(po.getId(), po.getApprovalTime());

    }

    private AuditIdPo fromVoToPo(AuditIdVo vo) {
        return new AuditIdPo(vo.getId(), vo.getApprovalTime());
    }

    /**
     * Reject to approve the bill.
     * @param auditId id of the bill to be rejected
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage reject(AuditIdVo auditId) {
        try {
            //查看单据详情时获得billVo
            BillVo currentBill = query(auditId.getId());
            ResultMessage opResult = dataService.reject(fromVoToPo(auditId));
            if (opResult.isSuccess()) {
                //调用接口，修改bill的状态属性
                BillApprovalCompleteService approvalService = currentBill.billApprovalCompleteService();
                approvalService.approvalComplete(auditId.getId(),BillState.Rejected);
                //通知相关人员
                EmployeeVo[] receivers = new EmployeeVo[1];
                receivers[0] = employeeInfo.queryById(currentBill.getOperatorId());
                notificationService.addNotification(new BillApprovalNotificationVo(new Date(),currentUserService.getCurrentUser(),receivers,currentBill));
                logService.log(LogSeverity.Success, String.format("审批单据并拒绝通过，单据编号是%s。", auditId.getId()));
            } else {
                logService.log(LogSeverity.Failure, String.format("审批单据并拒绝通过操作失败，原因不明。单据编号是%s。",auditId.getId()));
            }
            return opResult;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format("审批单据并拒绝通过操作失败，原因网络原因，具体信息是%s，单据编号是%s",e.getMessage(), auditId.getId()));
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Approve the bill.
     * @param auditId id of the bill to be approved
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage pass(AuditIdVo auditId) {
        try {
            //查看单据详情时获得billVo
            BillVo currentBill = query(auditId.getId());
            ResultMessage opResult = dataService.pass(fromVoToPo(auditId));
            if (opResult.isSuccess()) {
                //调用接口，修改bill的状态属性
                BillApprovalCompleteService approvalService = currentBill.billApprovalCompleteService();
                approvalService.approvalComplete(auditId.getId(),BillState.Approved);
                //通知相关人员
                EmployeeVo[] receivers = new EmployeeVo[1];
                receivers[0] = employeeInfo.queryById(currentBill.getOperatorId());
                notificationService.addNotification(new BillApprovalNotificationVo(new Date(),currentUserService.getCurrentUser(),receivers,currentBill));
                logService.log(LogSeverity.Success, String.format("审批单据并通过，单据编号是%s。", auditId.getId()));
            } else {
                logService.log(LogSeverity.Failure, String.format("审批单据并通过操作失败，原因不明。单据编号是%s。", auditId.getId()));
            }
            return opResult;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format("审批单据并通过操作失败，原因网络原因，具体信息是%s，单据编号是%s", e.getMessage(), auditId.getId()));
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Filter bills.
     * @param query the filter conditions
     * @return array of bills which match the conditions
     */
    @Override
    public AuditIdVo[] query(AuditIdQueryVo query) {
        try {
            AuditIdPo[] queryResult = dataService.query(query);
            List<AuditIdVo> listResult = Arrays.stream(queryResult).map(x -> fromPoToVo(x)).collect(Collectors.toList());
            return listResult.toArray(new AuditIdVo[listResult.size()]);
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Query for the detail of the bill.
     * @param id id of the bill
     * @return detail information of the bill
     */
    private BillVo query(String id) {
        return billDetailService.queryBill(id);
    }

}
