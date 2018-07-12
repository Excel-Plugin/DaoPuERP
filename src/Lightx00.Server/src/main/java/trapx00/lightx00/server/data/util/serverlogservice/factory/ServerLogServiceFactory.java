package trapx00.lightx00.server.data.util.serverlogservice.factory;

import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogServiceController;

public class ServerLogServiceFactory {
    private static ServerLogServiceController controller = new ServerLogServiceController();

    public static ServerLogService getService() {
        return controller;
    }
}
