package trapx00.lightx00.client.presentation.approvalui;

import javafx.event.ActionEvent;
import trapx00.lightx00.client.presentation.helpui.BaseHomepageUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.FrameworkUiManager;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.presentation.mainui.ManagerUiController;

public class ManagerHomeUiController extends BaseHomepageUiController {

    private ManagerUiController uiController = (ManagerUiController) FrameworkUiManager.getFrameworkUiController();

    public void initialize() {
        super.refresh();
        textWelcome.setText("欢迎：总经理" + FrameworkUiManager.getCurrentEmployee().getName());
    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/managerui/ManagerHome.fxml").loadAndGetPackageWithoutException();
    }

    public void onBtnAuditClicked(ActionEvent actionEvent) {
        uiController.onAuditFunctionClicked(actionEvent);
    }

    public void onBtnNotificationClicked(ActionEvent actionEvent) {
        uiController.onNotificationFunctionButtonClicked(actionEvent);
    }

    public void onBtnDraftButtonClicked(ActionEvent actionEvent) {
        uiController.onDraftFunctionButtonClicked(actionEvent);
    }


    public void onBtnClientPromotionClicked(ActionEvent actionEvent) {
        uiController.onClientPromotionFunctionClicked(actionEvent);
    }

    public void onBtnComSalePromotionClicked(ActionEvent actionEvent) {
        uiController.onComSalePromotionFunctionClicked(actionEvent);
    }


    public void onBtnTotalPricePromotionClicked(ActionEvent actionEvent) {
        uiController.onTotalPricePromotionFunctionClicked(actionEvent);
    }

    public void onBtnSaleDetailClicked(ActionEvent actionEvent) {
        uiController.onSaleDetailFunctionClicked(actionEvent);
    }

    public void onBtnTradeHistoryClicked(ActionEvent actionEvent) {
        uiController.onTradeHistoryFunctionClicked(actionEvent);
    }

    public void onBtnTradeSituationClicked(ActionEvent actionEvent) {
        uiController.onTradeSituationFunctionClicked(actionEvent);
    }
}
