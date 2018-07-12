package trapx00.lightx00.client.presentation.bankaccountui;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import trapx00.lightx00.client.blservice.bankaccountblservice.BankAccountManagementBlService;
import trapx00.lightx00.client.blservice.bankaccountblservice.BankAccountManagementBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;

public class BankAccountModificationUi implements ExternalLoadableUiController {
    public JFXTextField tfNewName;
    public JFXTextField tfOldName;
    public JFXTextField tfId;

    private BankAccountManagementBlService blService = BankAccountManagementBlServiceFactory.getInstance();
    private Runnable runnable;
    private BankAccountVo oldBankAccount;
    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/bankaccountui/BankAccountModificationUi.fxml").loadAndGetPackageWithoutException();
    }

    public void show(BankAccountVo oldBankAccount, Runnable runnable) {
        ExternalLoadedUiPackage uiPackage = load();
        BankAccountModificationUi ui = uiPackage.getController();
        ui.oldBankAccount = oldBankAccount;
        ui.tfOldName.setText(oldBankAccount.getName());
        ui.tfId.setText(String.valueOf(oldBankAccount.getId()));
        PromptDialogHelper.start("","").setContent(uiPackage.getComponent()).createAndShow();
        ((BankAccountModificationUi)uiPackage.getController()).runnable = runnable;
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
    }



    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        if (tfNewName.validate()) {
            oldBankAccount.setName(tfNewName.getText());
            blService.modify(oldBankAccount);
            PromptDialogHelper.start("修改成功！","用户名已经修改成功。")
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
