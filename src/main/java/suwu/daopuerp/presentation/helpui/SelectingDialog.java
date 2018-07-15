package suwu.daopuerp.presentation.helpui;

public abstract class SelectingDialog implements ExternalLoadableUiController {
    public void onClose() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}
