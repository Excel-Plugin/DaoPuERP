package trapx00.lightx00.client.presentation.clientui.factory;

import trapx00.lightx00.client.presentation.clientui.ClientUiController;

public class ClientUiFactory {
    private static ClientUiController clientUiController = new ClientUiController();

    public static ClientUiController getClientUiController() {
        return clientUiController;
    }
}
