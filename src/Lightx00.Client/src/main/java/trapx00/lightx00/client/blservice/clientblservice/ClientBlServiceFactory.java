package trapx00.lightx00.client.blservice.clientblservice;

import trapx00.lightx00.client.bl.clientbl.factory.ClientBlFactory;

public class ClientBlServiceFactory {
    private static ClientBlService clientBlService = ClientBlFactory.getClientBlController();

    public static ClientBlService getInstance() {
        return clientBlService;
    }
}
