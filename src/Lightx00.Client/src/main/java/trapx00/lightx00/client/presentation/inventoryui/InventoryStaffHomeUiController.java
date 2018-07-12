package trapx00.lightx00.client.presentation.inventoryui;

import javafx.event.ActionEvent;
import trapx00.lightx00.client.presentation.helpui.BaseHomepageUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.FrameworkUiManager;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.presentation.mainui.InventoryStaffUiController;

public class InventoryStaffHomeUiController extends BaseHomepageUiController {

    private InventoryStaffUiController uiController=(InventoryStaffUiController) FrameworkUiManager.getFrameworkUiController();
    public void initialize() {
        super.refresh();
        textWelcome.setText("欢迎：库存管理人员" + FrameworkUiManager.getCurrentEmployee().getName());
    }


    public void onBtnNotificationClicked(ActionEvent actionEvent) {
        uiController.onNotificationFunctionButtonClicked(actionEvent);
    }

    public void onBtnDraftButtonClicked(ActionEvent actionEvent) {
        uiController.onDraftFunctionButtonClicked(actionEvent);
    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/InventoryHomeUi.fxml").loadAndGetPackageWithoutException();
    }
    public void onCommodityFunctionClicked(ActionEvent actionEvent) {
        /**
         * 调用父类切换界面方法实现切换界面。
         * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
         * @see #switchFunction(Class, String, boolean)
         */
         uiController.onCommodityFunctionClicked(actionEvent);
    }

    public void onInventoryCheckFunctionClicked(ActionEvent actionEvent) {
        /**
         * 调用父类切换界面方法实现切换界面。
         * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
         * @see #switchFunction(Class, String, boolean)
         */
      uiController.onInventoryCheckFunctionClicked(actionEvent);
    }

    public void onInventoryPictureFunctionClicked(ActionEvent actionEvent){
        uiController.onInventoryPictureFunctionClicked(actionEvent);
    }

    public void onInventoryGiftFunctionClicked(ActionEvent actionEvent) {
        /**
         * 调用父类切换界面方法实现切换界面。
         * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
         * @see #switchFunction(Class, String, boolean)
         */
        uiController.onInventoryGiftFunctionClicked(actionEvent);
    }

    public void onInventoryWarningFunctionClicked(ActionEvent actionEvent) {
        /**
         * 调用父类切换界面方法实现切换界面。
         * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
         * @see #switchFunction(Class, String, boolean)
         */
        uiController.onInventoryWarningFunctionClicked(actionEvent);
    }

}
