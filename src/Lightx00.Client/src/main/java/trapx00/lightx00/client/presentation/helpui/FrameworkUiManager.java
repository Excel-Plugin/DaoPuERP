package trapx00.lightx00.client.presentation.helpui;

import com.jfoenix.controls.JFXDialog;
import javafx.scene.layout.StackPane;
import trapx00.lightx00.client.presentation.mainui.FrameworkUiController;
import trapx00.lightx00.client.vo.EmployeeVo;

import java.util.Date;

public class FrameworkUiManager {
    private static FrameworkUiController frameworkUiController;
    private static EmployeeVo currentEmployee;

    public static Date getLoginDate() {
        return frameworkUiController.getLoginDate();
    }

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

    public static EmployeeVo getCurrentEmployee() {
        return currentEmployee;
    }

    public static void setCurrentEmployee(EmployeeVo employee) {
        currentEmployee = employee;
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
