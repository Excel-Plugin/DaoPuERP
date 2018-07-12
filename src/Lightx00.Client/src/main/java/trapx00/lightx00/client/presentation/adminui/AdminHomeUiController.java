package trapx00.lightx00.client.presentation.adminui;

import javafx.event.ActionEvent;
import trapx00.lightx00.client.presentation.helpui.BaseHomepageUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.FrameworkUiManager;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.presentation.mainui.AdminStaffUiController;

public class AdminHomeUiController extends BaseHomepageUiController {

    private AdminStaffUiController uiController = (AdminStaffUiController) FrameworkUiManager.getFrameworkUiController();

    public void initialize() {
        super.refresh();
        textWelcome.setText("欢迎：管理员" + FrameworkUiManager.getCurrentEmployee().getName());
    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/adminui/AdminHome.fxml").loadAndGetPackageWithoutException();
    }

    public void onBtnNotificationClicked(ActionEvent actionEvent) {
        uiController.onNotificationFunctionButtonClicked(actionEvent);
    }

    public void onBtnDraftButtonClicked(ActionEvent actionEvent) {
        uiController.onDraftFunctionButtonClicked(actionEvent);
    }


    public void onBtnUserManagementClicked(ActionEvent actionEvent) {
        uiController.onUserManagementFunctionClicked(actionEvent);
    }

    public void onBtnFaceIdClicked(ActionEvent actionEvent) {
        uiController.onFaceIdRegistrationFunctionClicked(actionEvent);
    }


    public void onBtnLogBackUpClicked(ActionEvent actionEvent) {
        uiController.onLogBackUpFunctionClicked(actionEvent);
    }

}
