package trapx00.lightx00.client.presentation.helpui;

public abstract class SelectingDialog implements ExternalLoadableUiController {
    public void onClose() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}
