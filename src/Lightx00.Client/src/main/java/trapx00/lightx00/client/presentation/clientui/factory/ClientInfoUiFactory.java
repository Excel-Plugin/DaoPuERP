package trapx00.lightx00.client.presentation.clientui.factory;

import trapx00.lightx00.client.presentation.clientui.ClientDialogUiController;
import trapx00.lightx00.client.presentation.clientui.ClientInfoUi;

public class ClientInfoUiFactory {
    private static ClientInfoUi clientInfoUi=new ClientDialogUiController();

    public static ClientInfoUi getClientInfoUi() {
        return clientInfoUi;
    }
}
