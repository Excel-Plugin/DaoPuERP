package trapx00.lightx00.client.presentation.clientui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlService;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlServiceFactory;
import trapx00.lightx00.client.presentation.adminui.EmployeeSelection;
import trapx00.lightx00.client.presentation.adminui.factory.UserManagementUiFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.client.vo.salestaff.SaleStaffVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.presentation.NotCompleteException;
import trapx00.lightx00.shared.po.client.ClientType;

import java.io.IOException;
import java.util.HashMap;

public class ClientModifyUiController extends ClientModifyUi implements DraftContinueWritableUiController, ExternalLoadableUiController {

    private static final HashMap<String, ClientType> clientTypeMap = new HashMap<>();
    @FXML
    private JFXTextField clientId;
    @FXML
    private JFXTextField clientName;
    @FXML
    private JFXComboBox clientType;
    @FXML
    private JFXComboBox clientLevel;
    @FXML
    private JFXTextField clientPhone;
    @FXML
    private JFXTextField clientAddress;
    @FXML
    private JFXTextField clientZipCode;
    @FXML
    private JFXTextField clientEmail;
    @FXML
    private JFXTextField clientReceivableQuota;
    @FXML
    private JFXTextField clientReceivable;
    @FXML
    private JFXTextField clientPayable;
    @FXML
    private JFXTextField clientDefaultOperator;
    private ClientBlService blService = ClientBlServiceFactory.getInstance();
    private EmployeeSelection employeeSelection = UserManagementUiFactory.getEmployeeSelectionUi();
    private ObjectProperty<EmployeeVo> currentEmployee = new SimpleObjectProperty<>();

    @FXML
    private void initialize() {
        currentEmployee.set(FrameworkUiManager.getCurrentEmployee());
        if (((SaleStaffVo) currentEmployee.getValue()).isRoot()) {
            clientReceivableQuota.setEditable(true);
        }
        clientTypeMap.put(ClientType.Retailer.toString(), ClientType.Retailer);
        clientTypeMap.put(ClientType.Supplier.toString(), ClientType.Supplier);

        ObservableList<String> stringObservableList = FXCollections.observableArrayList(
                "销售商", "进货商"
        );
        clientType.setItems(stringObservableList);
        clientType.setValue("销售商");
        ObservableList<String> stringObservableList2 = FXCollections.observableArrayList(
                "1", "2", "3", "4", "5"
        );
        clientLevel.setItems(stringObservableList2);
        clientLevel.setValue("1");

        NumberValidator numberValidator = new NumberValidator();
        numberValidator.setMessage("请输入数字类型");
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.setMessage("请输入信息");

        clientName.getValidators().add(requiredValidator);
        clientPhone.getValidators().add(requiredValidator);
        clientAddress.getValidators().add(requiredValidator);
        clientZipCode.getValidators().add(requiredValidator);
        clientZipCode.getValidators().add(numberValidator);
        clientDefaultOperator.getValidators().add(requiredValidator);

        clientName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                clientName.validate();
            }
        });

        clientPhone.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                clientPhone.validate();
            }
        });

        clientAddress.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                clientAddress.validate();
            }
        });

        clientZipCode.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                clientZipCode.validate();
            }
        });

        clientDefaultOperator.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                clientDefaultOperator.validate();
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
        return new UiLoader("/fxml/clientui/ClientModifyUi.fxml").loadAndGetPackageWithoutException();
    }

    /**
     * Start continuing write a draft. Returns a External loaded ui package.
     * Overrides to return a specific ui controller.
     *
     * @param draft draft
     * @return External loaded ui package including a ExternalLoadableUiController and the component.
     */
    @Override
    public ExternalLoadedUiPackage continueWriting(Draftable draft) throws IOException {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        ClientVo clientVo = (ClientVo) draft;
        ClientModifyUiController clientModifyUiController = externalLoadedUiPackage.getController();
        clientModifyUiController.clientId.setText(clientVo.getId());
        clientModifyUiController.clientName.setText(clientVo.getName());
        clientModifyUiController.clientType.setValue(clientVo.getClientType().toString());
        clientModifyUiController.clientLevel.setValue(clientVo.getClientLevel() + "");
        clientModifyUiController.clientPhone.setText(clientVo.getAddress());
        clientModifyUiController.clientAddress.setText(clientVo.getAddress());
        clientModifyUiController.clientZipCode.setText(clientVo.getEmail());
        clientModifyUiController.clientEmail.setText(clientVo.getEmail());
        clientModifyUiController.clientReceivableQuota.setText(clientVo.getReceivableQuota() + "");
        clientModifyUiController.clientReceivable.setText(clientVo.getReceivable() + "");
        clientModifyUiController.clientPayable.setText(clientVo.getPayable() + "");
        clientModifyUiController.clientDefaultOperator.setText(clientVo.getDefaultOperatorId());
        return externalLoadedUiPackage;
    }

    @FXML
    private void onBtnSubmitClicked() {
        try {
            ClientVo clientVo = getCurrentClientVo();
            PromptDialogHelper.start("确认客户信息", "").setContent(
                    clientVo.detailUi().showContent(clientVo).getComponent())
                    .addCloseButton("确定", "CHECK", e -> {
                        try {
                            blService.modify(clientVo);
                            PromptDialogHelper.start("提交成功！", "你的客户信息已经提交成功！")
                                    .addCloseButton("继续填写", "EDIT", e1 -> {
                                        onBtnResetClicked();
                                        autofill();
                                    })
                                    .addCloseButton("返回客户管理界面", "CHECK", e1 ->{
                                        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                                        FrameworkUiManager.switchFunction(ClientUiController.class, "管理客户", true);
                                    })
                                    .createAndShow();
                        } catch (UncheckedRemoteException e1) {
                            PromptDialogHelper.start("提交失败！", "网络错误。详细信息：\n" + e1.getRemoteException().getMessage())
                                    .addCloseButton("好的", "CHECK", null)
                                    .createAndShow();
                        } catch (IdExistsException e1) {
                            PromptDialogHelper.start("提交失败", "ID已经存在，请重新获取ID！")
                                    .addCloseButton("好的", "CHECK", null)
                                    .createAndShow();
                        }
                    })
                    .addCloseButton("取消", "CLOSE", null)
                    .createAndShow();
        } catch (NotCompleteException ignored) {

        }

    }

    private ClientVo getCurrentClientVo() {
        if (clientName.getText().length() == 0 || clientType.getValue().toString().length() == 0 || clientLevel.getValue().toString().length() == 0 || clientPhone.getText().length() == 0 || clientAddress.getText().length() == 0 || clientZipCode.getText().length() == 0 || clientEmail.getText().length() == 0 || clientDefaultOperator.getText().length() == 0) {
            PromptDialogHelper.start("提交失败！", "请先填写完客户信息。")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
        return new ClientVo(
                clientId.getText(),
                clientTypeMap.get(clientType.getValue()),
                Integer.parseInt(clientLevel.getValue().toString()),
                clientName.getText(),
                clientPhone.getText(),
                clientAddress.getText(),
                clientZipCode.getText(),
                clientEmail.getText(),
                Double.parseDouble(clientReceivableQuota.getText()),
                Double.parseDouble(clientReceivable.getText()),
                Double.parseDouble(clientPayable.getText()),
                clientDefaultOperator.getText()
        );
    }

    private void autofill() {
        try {
            clientId.setText(blService.getId());
            currentEmployee.setValue(FrameworkUiManager.getCurrentEmployee());
        } catch (Exception e) {
            PromptDialogHelper.start("初始化失败！", "请重试！")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        }
    }

    @FXML
    private void onBtnResetClicked() {
        PromptDialogHelper.start("是否要重置", null)
                .addCloseButton("确定", "DONE", e -> reset())
                .addCloseButton("取消", "UNDO", null)
                .createAndShow();
    }

    private void reset() {
        clientName.clear();
        clientType.setValue("销售商");
        clientLevel.setValue("1");
        clientPhone.clear();
        clientAddress.clear();
        clientZipCode.clear();
        clientEmail.clear();
        clientReceivableQuota.clear();
        clientReceivable.clear();
        clientPayable.clear();
        clientDefaultOperator.clear();
    }

    @FXML
    private void onBtnCancelClicked() {
        PromptDialogHelper.start("是否要存入草稿箱", null)
                .addCloseButton("存入", "DONE", e -> saveAsDraft())
                .addCloseButton("不存入", "CLOSE", e -> FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext())
                .addCloseButton("取消", "UNDO", null)
                .createAndShow();
    }

    @FXML
    private void onBtnSaveAsDraftClicked() {
        saveAsDraft();
    }

    private void saveAsDraft() {
        try {
            blService.saveAsDraft(getCurrentClientVo());
            PromptDialogHelper.start("保存草稿成功", "你的单据已经保存为草稿。")
                    .addCloseButton("好的", "CHECK", e -> FrameworkUiManager.switchFunction(ClientUiController.class, "管理客户", true))
                    .createAndShow();
        } catch (NotCompleteException ignored) {
        } catch (UncheckedRemoteException e) {
            PromptDialogHelper.start("提交失败！", "网络错误。详细信息：\n" + e.getRemoteException().getMessage())
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        } catch (Exception e) {
            PromptDialogHelper.start("提交失败", "错误信息如下：\n" + e.getMessage())
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        }
    }

    @FXML
    private void selectEmployee() {
        employeeSelection.showEmployeeSelectDialog(x -> {
            clientDefaultOperator.setText(x.get(0).getId() + " " + x.get(0).getName());
        });
    }

    @Override
    public ExternalLoadedUiPackage showContent(ClientVo arg) {
        ClientVo clientVo = arg;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        ClientModifyUiController clientModifyUiController = externalLoadedUiPackage.getController();
        clientModifyUiController.clientId.setText(clientVo.getId());
        clientModifyUiController.clientName.setText(clientVo.getName());
        clientModifyUiController.clientType.setValue(clientVo.getClientType().toString());
        clientModifyUiController.clientLevel.setValue(clientVo.getClientLevel() + "");
        clientModifyUiController.clientPhone.setText(clientVo.getPhone());
        clientModifyUiController.clientAddress.setText(clientVo.getAddress());
        clientModifyUiController.clientZipCode.setText(clientVo.getZipCode() + "");
        clientModifyUiController.clientEmail.setText(clientVo.getEmail());
        clientModifyUiController.clientReceivableQuota.setText(clientVo.getReceivableQuota()+"");
        clientModifyUiController.clientReceivable.setText(clientVo.getReceivable() + "");
        clientModifyUiController.clientPayable.setText(clientVo.getPayable() + "");
        clientModifyUiController.clientDefaultOperator.setText(clientVo.getDefaultOperatorId());
        return externalLoadedUiPackage;
    }
}
