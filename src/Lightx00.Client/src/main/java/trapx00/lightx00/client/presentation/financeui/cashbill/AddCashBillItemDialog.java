package trapx00.lightx00.client.presentation.financeui.cashbill;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.shared.po.financestaff.CashBillItem;

import java.util.function.Consumer;

public class AddCashBillItemDialog implements ExternalLoadableUiController {
    public JFXTextField tfName;
    public JFXTextField tfPrice;
    public JFXTextField tfComment;

    private Consumer<CashBillItem> callback;

    public void show(Consumer<CashBillItem> callback) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        AddCashBillItemDialog dialog = externalLoadedUiPackage.getController();
        dialog.callback = callback;
        PromptDialogHelper.start("","").setContent(externalLoadedUiPackage.getComponent()).createAndShow();
    }
    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/AddCashBillItemDialog.fxml").loadAndGetPackageWithoutException();
    }

    @FXML
    private void initialize() {
        DoubleValidator validator = new DoubleValidator();
        validator.setMessage("请输入数字");
        tfPrice.getValidators().add(validator);
        tfPrice.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfPrice.validate();
            }
        });
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        close();
    }

    private boolean validate() {
        return tfPrice.validate();
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        if (validate()) {
            if (callback != null) {
                callback.accept(new CashBillItem(
                    tfName.getText(),
                    Double.parseDouble(tfPrice.getText()),
                    tfComment.getText()));
                close();
            }
        }
    }

    public void close() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}
