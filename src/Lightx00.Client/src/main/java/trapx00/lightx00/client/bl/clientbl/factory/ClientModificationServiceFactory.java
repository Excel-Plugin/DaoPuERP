package trapx00.lightx00.client.bl.clientbl.factory;

import trapx00.lightx00.client.bl.clientbl.ClientBlController;
import trapx00.lightx00.client.bl.clientbl.ClientModificationService;

public class ClientModificationServiceFactory {
    private static ClientModificationService clientModificationService=new ClientBlController();

    public static ClientModificationService getInstance() {
        return clientModificationService;
    }
}
