package trapx00.lightx00.client.vo.notification.others;

import trapx00.lightx00.client.bl.notificationbl.NotificationOperationRegistry;
import trapx00.lightx00.client.bl.notificationbl.NotificationOperationService;
import trapx00.lightx00.client.presentation.notificationui.NotificationDetailUi;
import trapx00.lightx00.client.presentation.notificationui.OtherNotificationDetailDisplayUiController;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.notification.NotificationVo;
import trapx00.lightx00.shared.po.notification.NotificationType;

import java.util.Date;

public class OtherNotificationVo extends NotificationVo {
    private String content;

    public OtherNotificationVo(int id, Date date, EmployeeVo sender, EmployeeVo[] receivers, String content) {
        super(id, date, sender, receivers, NotificationType.Others);
        this.content = content;
    }

    public OtherNotificationVo(Date date, EmployeeVo sender, EmployeeVo[] receivers, String content) {
        super(date, sender, receivers, NotificationType.Others);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public NotificationDetailUi notificationDetailUi() {
        return new OtherNotificationDetailDisplayUiController();
    }

    @Override
    public NotificationOperationService operationService() {
        return NotificationOperationRegistry.getNotificationOperationService(NotificationType.Others);
    }
}
