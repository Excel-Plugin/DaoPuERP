package trapx00.lightx00.client.bl.notificationbl;

import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.bl.loginbl.CurrentUserService;
import trapx00.lightx00.client.bl.loginbl.factory.CurrentUserServiceFactory;
import trapx00.lightx00.client.blservice.notificationblservice.NotificationBlService;
import trapx00.lightx00.client.datafactory.notificationdataservicefactory.NotificationDataServiceFactory;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.notification.NotificationConvertRegistry;
import trapx00.lightx00.client.vo.notification.NotificationVo;
import trapx00.lightx00.shared.dataservice.notificationdataservice.NotificationDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.po.notification.NotificationPo;
import trapx00.lightx00.shared.queryvo.NotificationQueryVo;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationBlController implements NotificationBlService, NotificationService {
    private NotificationDataService dataService = NotificationDataServiceFactory.getService();
    private CurrentUserService currentUserService = CurrentUserServiceFactory.getCurrentUserService();
    private LogService logService = LogServiceFactory.getLogService();
    /**
     * Adds a notification.
     *
     * @param notification notification to be added
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage addNotification(NotificationVo notification) {
        try {
            return dataService.add(NotificationConvertRegistry.convertToPo(notification));
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Updates current user's notifications
     *
     * @return current users's notifications
     */
    @Override
    public NotificationVo[] update() {
        EmployeeVo currentUser = currentUserService.getCurrentUser();
        try {
            NotificationPo[] queryResult = dataService.query(new NotificationQueryVo());
            List<NotificationPo> filteredNotificationVo = Arrays.stream(queryResult).filter(x -> Arrays.asList(x.getReceiverIds()).contains(currentUser.getId())).collect(Collectors.toList());
            logService.log(LogSeverity.Info, String.format("查询了接受者%s(id: %s)的通知，查到%s条通知。", currentUser.getName(), currentUser.getId(), queryResult.length));
            return filteredNotificationVo.stream()
                .map(NotificationConvertRegistry::convertToVo).toArray(NotificationVo[]::new);
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, "查单子过程中出现了异常，异常信息：" + e.getMessage());
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Acknowledges a notification. For a BillApprovalNotification with a bill that is approved of, it also activates the bill.
     *
     * @param notification notification
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage acknowledge(NotificationVo notification) {
        NotificationOperationRegistry.activate(notification);
        try {
            ResultMessage rm =  dataService.acknowledge(notification.getId());
            if (rm.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("已读了一条通知(id: %s)。",notification.getId()));
            } else {
                logService.log(LogSeverity.Failure, String.format("已读通知(id: %s)过程中失败，原因不明。", notification.getId()));
            }
            return rm;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, "已读通知过程中出现了异常，异常信息：" + e.getMessage());
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Abandons a notification.
     *
     * @param notification notification with a bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(NotificationVo notification) {
        NotificationOperationRegistry.abandon(notification);
        try {
            ResultMessage rm =  dataService.acknowledge(notification.getId());
            if (rm.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("丢弃了一条通知(id: %s)。",notification.getId()));
            } else {
                logService.log(LogSeverity.Failure, String.format("丢弃通知(id: %s)过程中失败，原因不明。", notification.getId()));
            }
            return rm;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, "丢弃通知过程中出现了异常，异常信息：" + e.getMessage());
            throw new UncheckedRemoteException(e);
        }
    }

}
