package trapx00.lightx00.client.presentation.financeui.mock;

import trapx00.lightx00.client.presentation.financeui.paymentandreceival.ReceivalBillUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.vo.Draftable;

public class ReceivalBillUiControllerMock extends ReceivalBillUiController {
    /**
     * Start continuing write a draft. Returns a External loaded ui package.
     * Overrides to return a specific ui controller.
     *
     * @param draft draft
     * @return External loaded ui package including a controller and the component.
     */
    @Override
    public ExternalLoadedUiPackage continueWriting(Draftable draft) {
        return super.continueWriting(draft);
    }
}
