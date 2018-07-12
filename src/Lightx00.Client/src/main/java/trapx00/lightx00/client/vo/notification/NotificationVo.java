package trapx00.lightx00.client.vo.notification;

import trapx00.lightx00.client.bl.notificationbl.NotificationOperationService;
import trapx00.lightx00.client.presentation.notificationui.NotificationDetailUi;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.po.notification.NotificationType;

import java.util.Date;

public abstract class NotificationVo {
    private int id;
    private Date date;
    private EmployeeVo sender;
    private EmployeeVo[] receivers;
    private NotificationType type;

    public NotificationVo(int id, Date date, EmployeeVo sender, EmployeeVo[] receivers, NotificationType type) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.receivers = receivers;
        this.type = type;
    }

    public NotificationVo(Date date, EmployeeVo sender, EmployeeVo[] receivers, NotificationType type) {
        this.date = date;
        this.sender = sender;
        this.receivers = receivers;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EmployeeVo getSender() {
        return sender;
    }

    public void setSender(EmployeeVo sender) {
        this.sender = sender;
    }

    public EmployeeVo[] getReceivers() {
        return receivers;
    }

    public void setReceivers(EmployeeVo[] receivers) {
        this.receivers = receivers;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract NotificationDetailUi notificationDetailUi();

    public abstract NotificationOperationService operationService();
}
