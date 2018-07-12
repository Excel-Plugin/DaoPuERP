package trapx00.lightx00.client.presentation.financeui;

import javafx.event.ActionEvent;
import trapx00.lightx00.client.presentation.helpui.BaseHomepageUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.FrameworkUiManager;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.presentation.mainui.FinanceStaffUiController;

public class HomeUiController extends BaseHomepageUiController {

    private FinanceStaffUiController uiController = (FinanceStaffUiController) FrameworkUiManager.getFrameworkUiController();

    public void initialize() {
        super.refresh();
        textWelcome.setText("欢迎：财务人员" + FrameworkUiManager.getCurrentEmployee().getName());
    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/Home.fxml").loadAndGetPackageWithoutException();
    }

    public void onBtnBankAccountClicked(ActionEvent actionEvent) {
        uiController.onBankAccountFunctionClicked(actionEvent);
    }

    public void onBtnNotificationClicked(ActionEvent actionEvent) {
        uiController.onNotificationFunctionButtonClicked(actionEvent);
    }

    public void onBtnDraftButtonClicked(ActionEvent actionEvent) {
        uiController.onDraftFunctionButtonClicked(actionEvent);
    }


    public void onBtnReceivalBillClicked(ActionEvent actionEvent) {
        uiController.onReceivalBillFunctionClicked(actionEvent);
    }

    public void onBtnPaymentBillClicked(ActionEvent actionEvent) {
        uiController.onPaymentBillFunctionClicked(actionEvent);
    }


    public void onBtnCashBillClicked(ActionEvent actionEvent) {
        uiController.onCashBillFunctionClicked(actionEvent);
    }

    public void onBtnInitialEstablishmentClicked(ActionEvent actionEvent) {
        uiController.onInitialEstablishmentFunctionClicked(actionEvent);
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
