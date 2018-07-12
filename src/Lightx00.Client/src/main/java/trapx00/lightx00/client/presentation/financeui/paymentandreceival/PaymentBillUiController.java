package trapx00.lightx00.client.presentation.financeui.paymentandreceival;

import trapx00.lightx00.client.blservice.financeblservice.PaymentBillBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.financestaff.PaymentBillVo;

public class PaymentBillUiController extends ReceivalPaymentBillUiController<PaymentBillVo> {
    public PaymentBillUiController() {
        super(PaymentBillBlServiceFactory.getInstance(), PaymentBillVo.class);
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/PaymentBillUi.fxml").loadAndGetPackageWithoutException();
    }

}
