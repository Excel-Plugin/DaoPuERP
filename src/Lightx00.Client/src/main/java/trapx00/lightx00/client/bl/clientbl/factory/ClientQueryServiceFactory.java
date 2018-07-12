package trapx00.lightx00.client.bl.clientbl.factory;

import trapx00.lightx00.client.bl.clientbl.ClientBlController;
import trapx00.lightx00.client.bl.clientbl.ClientQueryService;

public class ClientQueryServiceFactory {
    private static ClientQueryService queryService=new ClientBlController();

    public static ClientQueryService getQueryService() {
        return queryService;
    }
}
