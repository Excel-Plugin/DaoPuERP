package trapx00.lightx00.client.bl.notificationbl.controller;

import trapx00.lightx00.client.bl.notificationbl.NotificationOperationService;
import trapx00.lightx00.client.datafactory.notificationdataservicefactory.NotificationDataServiceFactory;
import trapx00.lightx00.client.vo.notification.others.OtherNotificationVo;
import trapx00.lightx00.shared.dataservice.notificationdataservice.NotificationDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.ResultMessage;

import java.rmi.RemoteException;

public class OtherNotificationOperationController implements NotificationOperationService<OtherNotificationVo> {
    private NotificationDataService dataService = NotificationDataServiceFactory.getService();
    @Override
    public ResultMessage abandon(OtherNotificationVo notification) {
        try {
            return dataService.acknowledge(notification.getId());
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    @Override
    public ResultMessage activate(OtherNotificationVo notification) {
        try {
            return dataService.acknowledge(notification.getId());
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }
}
