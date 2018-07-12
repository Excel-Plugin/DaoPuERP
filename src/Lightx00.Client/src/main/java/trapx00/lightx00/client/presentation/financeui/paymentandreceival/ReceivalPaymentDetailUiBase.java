package trapx00.lightx00.client.presentation.financeui.paymentandreceival;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.presentation.adminui.EmployeeSelection;
import trapx00.lightx00.client.presentation.adminui.factory.UserManagementUiFactory;
import trapx00.lightx00.client.presentation.clientui.ClientInfoUi;
import trapx00.lightx00.client.presentation.clientui.factory.ClientInfoUiFactory;
import trapx00.lightx00.client.presentation.financeui.TranscationModel;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.FrameworkUiManager;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.client.vo.financestaff.ReceivalPaymentBillVoBase;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.po.financestaff.Transcation;
import trapx00.lightx00.shared.util.BillHelper;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public abstract class ReceivalPaymentDetailUiBase<T extends ReceivalPaymentBillVoBase> extends BillDetailUi {
    public JFXTextField tfId;
    public JFXTextField tfClient;
    public JFXTextField tfOperator;
    public JFXTextField tfDate;
    public JFXTextField tfBillState;
    public Label lbTotal;
    public JFXTreeTableView<TranscationModel> tbTranscations;
    public JFXTreeTableColumn<TranscationModel, String> tcBankAccount;
    public JFXTreeTableColumn<TranscationModel, String> tcAmount;
    public JFXTreeTableColumn<TranscationModel, String> tcComment;

    private EmployeeSelection employeeSelection = UserManagementUiFactory.getEmployeeSelectionUi();
    private ClientInfoUi clientInfoUi = ClientInfoUiFactory.getClientInfoUi();


    protected ObservableList<TranscationModel> transcationModels = FXCollections.observableArrayList();

    public void onBtnBackClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }

    @Override
    public ExternalLoadedUiPackage showContent(BillVo arg) {
        ExternalLoadedUiPackage uiPackage = load();
        ReceivalPaymentDetailUiBase<T> controller = uiPackage.getController();
        T bill = ((T) arg);
        EmployeeVo employee = employeeSelection.queryId(bill.getOperatorId());
        ClientVo client = clientInfoUi.queryById(bill.getClientId());
        controller.tfId.setText(bill.getId());
        controller.tfDate.setText(DateHelper.fromDate(bill.getDate()));
        controller.tfOperator.setText(String.format("%s(id: %s)", employee.getName(), employee.getId()));
        controller.tfClient.setText(String.format("%s(id: %s)", client.getName(), client.getId()));
        controller.tfBillState.setText(bill.getState().toString());
        controller.initTranscationTable(bill.getTranscations());
        return uiPackage;
    }

    public void initialize() {
        initTable();
    }

    public void initTranscationTable(Transcation[] transcations) {
        transcationModels.addAll(Arrays.stream(transcations).map(TranscationModel::fromTranscation).collect(Collectors.toList()));
        lbTotal.setText(BillHelper.toFixed(Arrays.stream(transcations).mapToDouble(Transcation::getTotal).sum()));
    }

    public void initTable() {
        tcBankAccount.setCellValueFactory(cellData -> {
            BankAccountVo bankAccountVo = cellData.getValue().getValue().getBankAccount();
            return new SimpleStringProperty(String.format("%s(id: %d)", bankAccountVo.getName(), bankAccountVo.getId()));
        });

        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(BillHelper.toFixed(cellData.getValue().getValue().getPrice())));
        tcComment.setCellValueFactory(cellData -> cellData.getValue().getValue().commentProperty());
        TreeItem<TranscationModel> root = new RecursiveTreeItem<>(transcationModels, RecursiveTreeObject::getChildren);
        tbTranscations.setRoot(root);
        tbTranscations.setShowRoot(false);
        tbTranscations.setEditable(true);
        tbTranscations.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


}
