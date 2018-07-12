package trapx00.lightx00.shared.dataservicestub.notificationdataservice;

import trapx00.lightx00.shared.dataservice.notificationdataservice.NotificationDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.notification.NotificationPo;
import trapx00.lightx00.shared.po.notification.NotificationType;
import trapx00.lightx00.shared.queryvo.NotificationQueryVo;

import java.util.Date;

public class NotificationDataServiceStub implements NotificationDataService {


    /**
     * Updates current user's notification.
     *
     * @param query
     * @return current user's notification
     */
    @Override
    public NotificationPo[] query(NotificationQueryVo query) {
        return new NotificationPo[] {
                new NotificationPo(new Date(), "",new String[] {""}, NotificationType.BillApproval,"FXD-20171122-00001"),
                new NotificationPo(new Date(), "",new String[] {""},NotificationType.Others,"hahaha")
        };
    }

    /**
     * Acknowledges a notification.
     *
     * @param id id for the notification to be acknowledged
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage acknowledge(int id) {
        return ResultMessage.Success;
    }

    /**
     * Adds a notification.
     *
     * @param notificationPo NotificationPo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(NotificationPo notificationPo) {
        return ResultMessage.Success;
    }
}
