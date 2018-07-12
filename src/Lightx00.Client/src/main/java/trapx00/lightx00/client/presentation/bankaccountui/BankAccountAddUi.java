package trapx00.lightx00.client.presentation.bankaccountui;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import trapx00.lightx00.client.blservice.bankaccountblservice.BankAccountManagementBlService;
import trapx00.lightx00.client.blservice.bankaccountblservice.BankAccountManagementBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.shared.util.BillHelper;

import java.util.Date;

public class BankAccountAddUi implements ExternalLoadableUiController {
    public JFXTextField tfNewName;
    public JFXTextField tfInitialValue;
    private BankAccountManagementBlService blService = BankAccountManagementBlServiceFactory.getInstance();
    private Runnable runnable;
    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/bankaccountui/BankAccountAddUi.fxml").loadAndGetPackageWithoutException();
    }

    public void show(Runnable runnable) {
        ExternalLoadedUiPackage uiPackage = load();
        PromptDialogHelper.start("","").setContent(uiPackage.getComponent()).createAndShow();
        ((BankAccountAddUi)uiPackage.getController()).runnable = runnable;
    }

    public void initialize() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("请输入新用户名。");
        tfNewName.getValidators().add(validator);
        tfNewName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                tfNewName.validate();
            }
        });

        DoubleValidator doubleValidator = new DoubleValidator();
        doubleValidator.setMessage("请输入数字。");
        tfInitialValue.getValidators().add(doubleValidator);
        tfInitialValue.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue && tfInitialValue.validate()) {
                tfInitialValue.setText(BillHelper.toFixed(Double.parseDouble(tfInitialValue.getText())));
            }
        }));
    }

    public boolean validate() {
        return tfNewName.validate() & tfInitialValue.validate();
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        if (validate()) {
            blService.add(new BankAccountVo(tfNewName.getText(), Double.parseDouble(tfInitialValue.getText()), new Date()));
            PromptDialogHelper.start("创建成功！","已经创建新的银行账户。")
                .addCloseButton("好","CHECK",e -> close())
                .createAndShow();
        }
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        close();
    }

    private void close() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
        runnable.run();
    }
}
