package trapx00.lightx00.client.bl.clientbl.factory;

import trapx00.lightx00.client.bl.clientbl.ClientBlController;

public class ClientBlFactory {

    private static ClientBlController clientBlController=new ClientBlController();

    public static ClientBlController getClientBlController() {
        return clientBlController;
    }
}
