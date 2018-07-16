package suwu.daopuerp.presentation.helpui;

import com.jfoenix.controls.JFXDialog;
import javafx.scene.layout.StackPane;
import suwu.daopuerp.dto.account.UserDto;
import suwu.daopuerp.presentation.mainui.FrameworkUiController;

public class FrameworkUiManager {
    private static FrameworkUiController frameworkUiController;
    private static UserDto currentUser;

    public static FrameworkUiController getFrameworkUiController() {
        return frameworkUiController;
    }

    public static void setFrameworkUiController(FrameworkUiController frameworkUiController) {
        FrameworkUiManager.frameworkUiController = frameworkUiController;
    }

    public static DialogStack getCurrentDialogStack() {
        return frameworkUiController.getDialogStack();
    }

    public static JFXDialog createDialog(PromptDialogHelper promptDialogHelper) {
        return promptDialogHelper.create(frameworkUiController.dialogContainer);
    }

    public static StackPane getWholePane() {
        return frameworkUiController.dialogContainer;
    }

    public static StackPane getDialogContainer() {
        return frameworkUiController.dialogContainer;
    }

    public static UserDto getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentEmployee(UserDto userDto) {
        currentUser = userDto;
    }

    public static void switchBackToHome() {
        frameworkUiController.switchBackToHome();
    }

    public static void switchFunction(Class<? extends ExternalLoadableUiController> clazz, String title, boolean refresh) {
        frameworkUiController.switchFunction(clazz, title, refresh);
    }

    public static void switchFunction(ExternalLoadedUiPackage uiPackage, String title, boolean refresh) {
        frameworkUiController.switchFunction(uiPackage, title, refresh);
    }

}
