package trapx00.lightx00.client.bl.notificationbl.factory;

import trapx00.lightx00.client.bl.notificationbl.NotificationBlController;

public class NotificationBlFactory {
    private static NotificationBlController controller = new NotificationBlController();

    public static NotificationBlController getController() {
        return controller;
    }
}
