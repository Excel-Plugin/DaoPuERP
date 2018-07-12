package trapx00.lightx00.client.bl.logbl.factory;

import trapx00.lightx00.client.bl.logbl.LogBlController;

public class LogBlFactory {
    private static LogBlController controller = new LogBlController();

    public static LogBlController getController() {
        return controller;
    }
}
