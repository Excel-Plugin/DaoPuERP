package trapx00.lightx00.client.blservice.logblservice;

import trapx00.lightx00.client.bl.logbl.factory.LogBlFactory;

public class LogBlServiceFactory {

    public static LogBlService getInstance() {
        return LogBlFactory.getController();
    }
}
