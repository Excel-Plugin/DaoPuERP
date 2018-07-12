package trapx00.lightx00.client.presentation.financeui.paymentandreceival;

import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.financestaff.ReceivalBillVo;

public class ReceivalBillDetailUi extends ReceivalPaymentDetailUiBase<ReceivalBillVo> {
    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/ReceivalBillDetailUi.fxml").loadAndGetPackageWithoutException();
    }
}
