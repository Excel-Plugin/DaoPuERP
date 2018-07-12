package trapx00.lightx00.client.presentation.mainui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import trapx00.lightx00.client.presentation.clientui.ClientUiController;
import trapx00.lightx00.client.presentation.inventoryui.PurchaseBillUiController;
import trapx00.lightx00.client.presentation.inventoryui.PurchaseRefundBillUiController;
import trapx00.lightx00.client.presentation.saleui.SaleBillUiController;
import trapx00.lightx00.client.presentation.saleui.SaleRefundBillUiController;

public class SaleStaffUiController extends FrameworkUiController {

    public void initialize() {
        super.initialize();
    }

    @FXML
    private void onClientFunctionClicked(ActionEvent actionEvent) {
        switchFunction(ClientUiController.class, "管理客户", true);
    }

    @FXML
    private void onPurchaseBillFunctionClicked(ActionEvent actionEvent) {
        switchFunction(PurchaseBillUiController.class, "制定进货单", true);
    }

    @FXML
    private void onPurchaseRefundBillFunctionClicked(ActionEvent actionEvent) {
        switchFunction(PurchaseRefundBillUiController.class, "制定进货退货单", true);
    }

    @FXML
    private void onSaleBillFunctionClicked(ActionEvent actionEvent) {
        switchFunction(SaleBillUiController.class, "制定销售单", true);
    }

    @FXML
    private void onSaleRefundBillFunctionClicked(ActionEvent actionEvent) {
        switchFunction(SaleRefundBillUiController.class, "制定销售退货单", true);
    }

    /**
     * 增加一个HomeUiController后，重写这个方法做到退回主界面。
     */
    @Override
    public void switchBackToHome() {
        switchFunction(SaleStaffHomeUiController.class,"主界面",true);
    }

}
