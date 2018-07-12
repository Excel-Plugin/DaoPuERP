package trapx00.lightx00.client.blservice.logblservice;

import trapx00.lightx00.client.bl.logbl.factory.LogBackupBlFactory;

public class LogBackupBlServiceFactory {

    public static LogBackupBlService getInstance() {
        return LogBackupBlFactory.getController();
    }
}
