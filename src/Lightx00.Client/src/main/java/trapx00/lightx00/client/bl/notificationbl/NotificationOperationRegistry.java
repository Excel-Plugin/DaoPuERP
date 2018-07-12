package trapx00.lightx00.client.bl.notificationbl;

import trapx00.lightx00.client.bl.notificationbl.controller.BillApprovalNotificationOperationController;
import trapx00.lightx00.client.bl.notificationbl.controller.OtherNotificationOperationController;
import trapx00.lightx00.client.vo.notification.NotificationVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.notification.NotificationType;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class NotificationOperationRegistry {
    private static HashMap<NotificationType, NotificationOperationService> map = new HashMap<>();
    static {
        scanAndRegister();
    }



    private static void scanAndRegister() {
        register(NotificationType.BillApproval, new BillApprovalNotificationOperationController());
        register(NotificationType.Others, new OtherNotificationOperationController());
    }

    private static void register(NotificationType type, NotificationOperationService service) {
        map.put(type,service);
    }

    public static ResultMessage abandon(NotificationVo vo) {
        return map.get(vo.getType()).abandon(vo);
    }

    public static ResultMessage activate(NotificationVo vo) {
        return map.get(vo.getType()).activate(vo);
    }

    public static <T extends NotificationOperationService> T getNotificationOperationService(NotificationType notificationType) {
        return (T) map.get(notificationType);
    }
}
