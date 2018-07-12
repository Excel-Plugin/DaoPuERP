package trapx00.lightx00.client.blservice.notificationblservice;

import trapx00.lightx00.client.bl.notificationbl.factory.NotificationBlFactory;

public class NotificationBlServiceFactory {

    public static NotificationBlService getInstance() {
        return NotificationBlFactory.getController();
    }
}
