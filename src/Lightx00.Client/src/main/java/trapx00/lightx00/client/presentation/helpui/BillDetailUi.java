package trapx00.lightx00.client.presentation.helpui;

import trapx00.lightx00.client.vo.BillVo;

public abstract class BillDetailUi implements ContentDisplayUi<BillVo>, ExternalLoadableUiController {

    public void onClose() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}
