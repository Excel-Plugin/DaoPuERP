package trapx00.lightx00.client.bl.logbl.factory;

import trapx00.lightx00.client.bl.logbl.LogBackupBlController;

public class LogBackupBlFactory {
    private static LogBackupBlController controller = new LogBackupBlController();

    public static LogBackupBlController getController() {
        return controller;
    }
}
