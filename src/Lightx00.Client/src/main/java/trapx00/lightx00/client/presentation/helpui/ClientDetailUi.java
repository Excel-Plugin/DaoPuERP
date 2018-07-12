package trapx00.lightx00.client.presentation.helpui;

import trapx00.lightx00.client.vo.salestaff.ClientVo;

public abstract class ClientDetailUi implements ContentDisplayUi<ClientVo>, ExternalLoadableUiController {

    public void onClose() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}
