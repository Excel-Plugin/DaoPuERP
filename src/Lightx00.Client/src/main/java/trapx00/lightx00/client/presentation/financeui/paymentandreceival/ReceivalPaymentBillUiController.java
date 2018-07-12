package trapx00.lightx00.client.presentation.financeui.paymentandreceival;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.blservice.financeblservice.PaymentAndReceivalBlService;
import trapx00.lightx00.client.presentation.adminui.EmployeeSelection;
import trapx00.lightx00.client.presentation.adminui.factory.UserManagementUiFactory;
import trapx00.lightx00.client.presentation.clientui.ClientInfoUi;
import trapx00.lightx00.client.presentation.clientui.factory.ClientInfoUiFactory;
import trapx00.lightx00.client.presentation.financeui.TranscationModel;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.presentation.helpui.validator.ValidatorHelper;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.client.vo.financestaff.ReceivalPaymentBillVoBase;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.exception.presentation.NotCompleteException;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.Transcation;
import trapx00.lightx00.shared.util.BillHelper;
import trapx00.lightx00.shared.util.DateHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public abstract class ReceivalPaymentBillUiController<T extends ReceivalPaymentBillVoBase> implements DraftContinueWritableUiController, ExternalLoadableUiController, ReversibleUi {
    public JFXTextField tfId;
    public JFXTextField tfClient;
    public JFXTextField tfOperator;
    public JFXTreeTableView<TranscationModel> tbTranscations;
    public JFXTreeTableColumn<TranscationModel, String> tcBankAccount;
    public JFXTreeTableColumn<TranscationModel, String> tcAmount;
    public JFXTreeTableColumn<TranscationModel, String> tcComment;
    public JFXButton btnAdd;
    public JFXButton btnDelete;
    public Label lbTotal;
    public JFXTextField tfDate;

    protected EmployeeSelection employeeSelection = UserManagementUiFactory.getEmployeeSelectionUi();
    protected PaymentAndReceivalBlService blService;
    private Class<T> voClazz;
    private ClientInfoUi clientInfoUi = ClientInfoUiFactory.getClientInfoUi();

    private ObjectProperty<Date> currentDate = new SimpleObjectProperty<>();
    protected ObjectProperty<ClientVo> client = new SimpleObjectProperty<>();
    protected ObjectProperty<EmployeeVo> currentEmployee = new SimpleObjectProperty<>();

    private ObservableList<TranscationModel> transcationModels = FXCollections.observableArrayList();

    /**
     * Start continuing write a draft. Returns a External loaded ui package.
     * Overrides to return a specific ui controller.
     *
     * @param draft draft
     * @return External loaded ui package including a controller and the component.
     */
    @Override
    public ExternalLoadedUiPackage continueWriting(Draftable draft) {
        ExternalLoadedUiPackage uiPackage = load();
        ReceivalPaymentBillUiController<T> controller = uiPackage.getController();


        T bill = (T) draft;
        ClientVo client = clientInfoUi.queryById(bill.getClientId());
        EmployeeVo operator = employeeSelection.queryId(bill.getOperatorId());

        if (bill.getId().equals(BillHelper.refreshIdRequest)) {
            controller.tfId.setText(blService.getId());
        } else {
            controller.tfId.setText(bill.getId());
        }

        controller.client.set(client);
        controller.currentEmployee.set(operator);
        controller.initTranscationTable(bill.getTranscations());
        controller.currentDate.set(bill.getDate());
        return uiPackage;
    }

    public void initTranscationTable(Transcation[] transcations) {
        transcationModels.addAll(Arrays.stream(transcations).map(TranscationModel::fromTranscation).collect(Collectors.toList()));
        lbTotal.setText(BillHelper.toFixed(Arrays.stream(transcations).mapToDouble(Transcation::getTotal).sum()));
    }

    public ReceivalPaymentBillUiController(PaymentAndReceivalBlService service, Class<T> voClazz) {
        this.blService = service;
        this.voClazz = voClazz;
    }


    public void initialize() {
        tcBankAccount.setCellValueFactory(cellData -> {
            BankAccountVo bankAccountVo = cellData.getValue().getValue().getBankAccount();
            return new SimpleStringProperty(String.format("%s(id: %d)", bankAccountVo.getName(), bankAccountVo.getId()));
        });

        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(BillHelper.toFixed(cellData.getValue().getValue().getPrice())));
        tcComment.setCellValueFactory(cellData -> cellData.getValue().getValue().commentProperty());

        transcationModels.addListener((ListChangeListener<TranscationModel>) c -> {
            lbTotal.setText(BillHelper.toFixed(transcationModels.stream().mapToDouble(TranscationModel::getPrice).sum()));
        });

        client.addListener((observable, oldValue, newValue) -> {
            tfClient.setText(newValue == null ? "" : String.format("%s(id: %s)", newValue.getName(), newValue.getId()));
        });

        currentEmployee.addListener(((observable, oldValue, newValue) -> {
            tfOperator.setText(newValue == null ? "" : String.format("%s(id: %s)", newValue.getName(), newValue.getId()));
        }));

        currentDate.addListener(((observable, oldValue, newValue) -> {
            tfDate.setText(newValue == null ? "" : DateHelper.fromDate(newValue));
        }));

        TreeItem<TranscationModel> root = new RecursiveTreeItem<>(transcationModels, RecursiveTreeObject::getChildren);
        tbTranscations.setRoot(root);
        tbTranscations.setShowRoot(false);
        tbTranscations.setEditable(true);
        tbTranscations.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        addRequiredValidator(tfId, "请点击自动填写信息以填写ID。");
        addRequiredValidator(tfClient, "请点击选择客户");
        addRequiredValidator(tfOperator, "请点击自动填写信息以填充操作员。");
        addRequiredValidator(tfDate, "请点击自动填写信息以填写日期。");

        autofill();

    }

    public void addRequiredValidator(JFXTextField jfxTextField, String message) {
        ValidatorHelper.addValidator(RequiredFieldValidator.class, jfxTextField, message);
    }


    /**
     * Revert a reversible.
     *
     * @param reversible reversible
     * @return External loaded ui package including a controller and the component.
     */
    @Override
    public ExternalLoadedUiPackage revertReversible(Reversible reversible) {
        ExternalLoadedUiPackage uiPackage = load();
        ReceivalPaymentBillUiController<T> controller = uiPackage.getController();


        T bill = (T) reversible;
        ClientVo client = clientInfoUi.queryById(bill.getClientId());
        EmployeeVo operator = employeeSelection.queryId(bill.getOperatorId());

        controller.tfId.setText(controller.blService.getId());
        controller.client.set(client);
        controller.currentEmployee.set(operator);

        for (Transcation t : bill.getTranscations()) {
            t.setTotal(-t.getTotal());
        }
        controller.initTranscationTable(bill.getTranscations());
        controller.currentDate.set(new Date());
        return uiPackage;
    }

    public void onBtnAddItemClicked() {
        new AddTranscationDialog().show(x -> transcationModels.add(x));
    }

    public void onBtnDeleteItemClicked() {
        try {
            int index = tbTranscations.getSelectionModel().getSelectedIndex();
            transcationModels.remove(index);
        } catch (Exception ignored) {

        }
    }

    public boolean validate() {
        return tfId.validate() & tfClient.validate() & tfOperator.validate() & tfDate.validate();
    }

    public T getCurrent() {
        if (validate()) {
            try {
                return voClazz.getDeclaredConstructor(
                        String.class, Date.class, BillState.class, String.class, String.class, Transcation[].class, Double.TYPE)
                        .newInstance(blService.getId(),
                                currentDate.get(),
                                BillState.Draft,
                                client.get().getId(),
                                currentEmployee.getValue().getId(),
                                transcationModels.stream().map(TranscationModel::toTranscation).toArray(Transcation[]::new),
                                Double.parseDouble(lbTotal.getText()));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        } else {
            PromptDialogHelper.start("未填写完成！", "请填写完成所有字段！")
                    .addCloseButton("好", "CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
    }

    public void resetPrompt(Runnable runnable) {
        PromptDialogHelper.start("重置", "是否要重置？")
            .addCloseButton("是", "CHECK", e -> {
                runnable.run();
            }).addCloseButton("否", "CLOSE", null)
            .createAndShow();
    }


    public void onBtnResetClicked() {
        resetPrompt(this::reset);

    }

    public void finishReset() {
        resetPrompt(this::autofill);
    }

    public void onTfClientClicked() {
        clientInfoUi.showClientSelectDialog(x -> {
            client.set(x);
            tfClient.validate();
        });
    }



    public void autofill() {
        try {
            tfId.setText(blService.getId());
            currentDate.setValue(new Date());
            currentEmployee.setValue(FrameworkUiManager.getCurrentEmployee());
            transcationModels.clear();
        } catch (NoMoreBillException e) {
            PromptDialogHelper.start("ID不够！", "当日ID已经达到99999，无法增加新的单据。")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        }
    }

    public void onBtnSubmitClicked() {
        try {
            T bill = getCurrent();
            PromptDialogHelper.start("确认单据", "")
                    .setContent(bill.billDetailUi().showContent(bill).getComponent())
                    .addCloseButton("确定", "CHECK", event -> {
                        try {
                            blService.submit(getCurrent());
                            PromptDialogHelper.start("提交成功！", "你的单据已经提交成功！")
                                .addCloseButton("继续填写", "EDIT", e1 -> {
                                    finishReset();
                                })
                                .addCloseButton("返回主界面", "CHECK", e1 -> FrameworkUiManager.switchBackToHome())
                                .createAndShow();
                        } catch (UncheckedRemoteException e) {
                            PromptDialogHelper.start("提交失败！", "网络错误。详细信息：\n" + e.getRemoteException().getMessage())
                                    .addCloseButton("好的", "CHECK", null)
                                    .createAndShow();
                        } catch (IdExistsException e) {
                            PromptDialogHelper.start("提交失败", "ID已经存在，请重新获取ID！")
                                    .addCloseButton("好的", "CHECK", null)
                                    .createAndShow();
                        } catch (Exception e) {
                            PromptDialogHelper.start("提交失败", "错误信息如下：\n" + e.getMessage())
                                    .addCloseButton("好的", "CHECK", null)
                                    .createAndShow();
                        }
                    })
                    .addCloseButton("取消", "CLOSE", null)
                    .createAndShow();
        } catch (NotCompleteException ignored) {
        }


    }

    public void onBtnSaveAsDraftClicked() {
        saveAsDraft();
    }

    public void reset() {
        transcationModels.clear();
        tfId.setText(blService.getId());
        client.set(null);
    }


    public void onBtnCancelClicked(ActionEvent actionEvent) {
        PromptDialogHelper.start("是否退出？", "是否保存草稿？")
                .addCloseButton("保存", "SAVE", e -> saveAsDraft())
                .addCloseButton("不保存", "DELETE", e -> FrameworkUiManager.switchBackToHome())
                .addCloseButton("取消", "CLOSE", null)
                .createAndShow();
    }

    public void saveAsDraft() {
        try {
            blService.saveAsDraft(getCurrent());
            PromptDialogHelper.start("保存草稿成功", "你的单据已经保存为草稿。")
                    .addCloseButton("好的", "CHECK", e -> reset())
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


}
