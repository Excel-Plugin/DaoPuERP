package trapx00.lightx00.client.presentation.promotionui.mock;

import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.promotionui.detail.ClientPromotionUiController;
import trapx00.lightx00.client.vo.Draftable;

public class ClientPromotionUiControllerMock extends ClientPromotionUiController {
    /**
     * Start continuing write a draft. Returns a ExternalLoadableUiController. It can be used to set the stage without casting to specific ui controller.
     * Overrides to return a specific ui controller.
     * @param draft draft
     * @return a ExternalLoadableUiController
     */
    @Override
    public ExternalLoadedUiPackage continueWriting(Draftable draft) {
        return null;
    }
}
