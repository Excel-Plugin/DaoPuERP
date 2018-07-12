package trapx00.lightx00.client.bl.clientbl.factory;

import trapx00.lightx00.client.bl.clientbl.ClientBlController;
import trapx00.lightx00.client.bl.util.ClientPoVoConverter;

public class ClientPoVoCoverterFactory {
    private static ClientPoVoConverter clientPoVoConverter = new ClientBlController();

    public static ClientPoVoConverter getClientPoVoConverter() {
        return clientPoVoConverter;
    }
}
