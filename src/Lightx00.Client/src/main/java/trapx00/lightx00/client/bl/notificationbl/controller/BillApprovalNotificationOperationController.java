package trapx00.lightx00.client.bl.notificationbl.controller;

import trapx00.lightx00.client.bl.notificationbl.NotificationOperationService;
import trapx00.lightx00.client.datafactory.notificationdataservicefactory.NotificationDataServiceFactory;
import trapx00.lightx00.client.vo.notification.billapproval.BillApprovalNotificationVo;
import trapx00.lightx00.shared.dataservice.notificationdataservice.NotificationDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.ResultMessage;

import java.rmi.RemoteException;

public class BillApprovalNotificationOperationController implements NotificationOperationService<BillApprovalNotificationVo> {
    private NotificationDataService dataService = NotificationDataServiceFactory.getService();

    @Override
    public ResultMessage abandon(BillApprovalNotificationVo notification) {
        try {
            return notification.notificationAbandonService().abandon(notification.getBill().getId()).isSuccess()
                && dataService.acknowledge(notification.getId()).isSuccess() ? ResultMessage.Success : ResultMessage.Failure;
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }

    }

    @Override
    public ResultMessage activate(BillApprovalNotificationVo notification) {
        try {
            return notification.notificationActivateService().activate(notification.getBill().getId()).isSuccess()
                && dataService.acknowledge(notification.getId()).isSuccess() ? ResultMessage.Success : ResultMessage.Failure;
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }
}
