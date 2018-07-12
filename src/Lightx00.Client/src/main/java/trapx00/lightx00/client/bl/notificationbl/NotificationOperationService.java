package trapx00.lightx00.client.bl.notificationbl;

import trapx00.lightx00.client.vo.notification.NotificationVo;
import trapx00.lightx00.shared.po.ResultMessage;

public interface NotificationOperationService<T extends NotificationVo> {
    ResultMessage abandon(T notification);
    ResultMessage activate(T notification);
}
