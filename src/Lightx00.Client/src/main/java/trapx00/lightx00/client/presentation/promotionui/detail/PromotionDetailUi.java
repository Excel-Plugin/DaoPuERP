package trapx00.lightx00.client.presentation.promotionui.detail;

import trapx00.lightx00.client.presentation.helpui.ContentDisplayUi;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadableUiController;
import trapx00.lightx00.client.presentation.helpui.FrameworkUiManager;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;

public abstract class PromotionDetailUi implements ContentDisplayUi<PromotionVoBase>, ExternalLoadableUiController {
    public void onClose() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}