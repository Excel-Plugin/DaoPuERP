package trapx00.lightx00.client.presentation.financeui.paymentandreceival;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import trapx00.lightx00.client.presentation.bankaccountui.factory.BankAccountUiFactory;
import trapx00.lightx00.client.presentation.financeui.TranscationModel;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;

import java.util.function.Consumer;

public class AddTranscationDialog implements ExternalLoadableUiController {
    public JFXTextField tfBankAccount;
    public JFXTextField tfPrice;
    public JFXTextField tfComment;

    private ObjectProperty<BankAccountVo> bankAccountProperty = new SimpleObjectProperty<>();

    private Consumer<TranscationModel> callback;


    public void show(Consumer<TranscationModel> callback) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        AddTranscationDialog controller = externalLoadedUiPackage.getController();
        controller.callback = callback;
        PromptDialogHelper.start("","").setContent(externalLoadedUiPackage.getComponent()).createAndShow();
    }

    public void initialize() {
        bankAccountProperty.addListener((observable, oldValue, newValue) ->{
            tfBankAccount.setText(String.format("%s (id: %d)", newValue.getName(), newValue.getId()));
        });

        DoubleValidator validator = new DoubleValidator();
        validator.setMessage("请输入数字！");
        tfPrice.getValidators().add(validator);
        tfPrice.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                tfPrice.validate();
            }
        });

        RequiredFieldValidator bankAccountValidator = new RequiredFieldValidator();
        bankAccountValidator.setMessage("请选择银行账户！");
        tfBankAccount.getValidators().add(bankAccountValidator);
        tfBankAccount.focusedProperty().addListener((observable, old, newValue) -> {
            if (!newValue) {
                tfBankAccount.validate();
            }
        });

    }


    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/AddTranscationDialog.fxml").loadAndGetPackageWithoutException();
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
                callback.accept(new TranscationModel(
                    bankAccountProperty.getValue(),
                    Double.parseDouble(tfPrice.getText()),
                    tfComment.getText()));
                close();
            }
        }
    }

    public void close() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }

    public void onTfBankAccountClicked(MouseEvent mouseEvent) {
        BankAccountUiFactory.getBankAccountSelectionUi()
            .showBankAccountSelectDialog(vo ->  {
                this.bankAccountProperty.set(vo);
                tfBankAccount.validate();
            } );
    }
}
