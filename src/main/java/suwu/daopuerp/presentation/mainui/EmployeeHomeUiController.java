package suwu.daopuerp.presentation.mainui;

import javafx.scene.text.Text;
import suwu.daopuerp.presentation.helpui.ExternalLoadableUiController;
import suwu.daopuerp.presentation.helpui.ExternalLoadedUiPackage;
import suwu.daopuerp.presentation.helpui.FrameworkUiManager;
import suwu.daopuerp.presentation.helpui.UiLoader;

public class EmployeeHomeUiController implements ExternalLoadableUiController {
    public Text textWelcome;

    private EmployeeUiController uiController = (EmployeeUiController) FrameworkUiManager.getFrameworkUiController();

    public void initialize() {
        textWelcome.setText("欢迎:" + FrameworkUiManager.getCurrentUser().getUsername());
    }

    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/mainui/EmployeeHomeUi.fxml").loadAndGetPackageWithoutException();
    }

    public void onManageFormulaTemplate() {
        /**
         * 调用父类切换界面方法实现切换界面。
         * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
         * @see #switchFunction(Class, String, boolean)
         */
        uiController.onManageFormulaTemplate();
    }

    public void onManageProductionBill() {
        /**
         * 调用父类切换界面方法实现切换界面。
         * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
         * @see #switchFunction(Class, String, boolean)
         */
        uiController.onManageProductionBill();
    }

    public void onGenerateTotalBill() {
        uiController.onGenerateTotalBill();
    }

}
