package trapx00.lightx00.client.bl.notificationbl;

import trapx00.lightx00.client.vo.notification.NotificationVo;
import trapx00.lightx00.shared.po.ResultMessage;

public interface NotificationService  {
    /**
     * Adds a notification.
     * @param notification notification to be added
     * @return whether the operation is done successfully
     */
    ResultMessage addNotification(NotificationVo notification);
}
