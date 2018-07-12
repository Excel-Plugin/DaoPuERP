package trapx00.lightx00.client.presentation.helpui;

import javafx.scene.control.Label;
import trapx00.lightx00.client.vo.DraftDemoVo;

import java.io.IOException;

public class DraftContinueWritableDemoUiController implements ExternalLoadableUiController, DraftContinueWritableUiController<DraftDemoVo> {
    public Label lbTest;

    /**
     * Start continuing write a draft. Returns a External loaded ui package.
     * Overrides to return a specific ui controller.
     *
     * @param draft draft
     * @return External loaded ui package including a ExternalLoadableUiController and the component.
     */
    @Override
    public ExternalLoadedUiPackage continueWriting(DraftDemoVo draft) throws IOException {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        DraftContinueWritableDemoUiController continueWritableDemoUiController = (DraftContinueWritableDemoUiController) externalLoadedUiPackage.getController();
        continueWritableDemoUiController.lbTest.setText(draft.getDraftContent());
        return externalLoadedUiPackage;

    }


    /**
     * Loads the controller.
     *
     * @return
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/DraftContinueWritingDemoUi.fxml").loadAndGetPackageWithoutException();
    }
}
