package trapx00.lightx00.client.presentation.bankaccountui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.blservice.bankaccountblservice.BankAccountManagementBlService;
import trapx00.lightx00.client.blservice.bankaccountblservice.BankAccountManagementBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.client.vo.financestaff.FinanceStaffVo;
import trapx00.lightx00.shared.queryvo.BankAccountQueryVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BankAccountUiController implements ExternalLoadableUiController {

    @FXML private JFXDatePicker startDatePicker;
    @FXML private JFXDatePicker endDatePicker;
    @FXML private JFXCheckBox cbIsEnabled;
    @FXML private JFXButton btnFilter;
    @FXML private JFXTreeTableView<BankAccountModel> tableBankAccounts;
    @FXML private JFXTreeTableColumn<BankAccountModel, Integer> tcId;
    @FXML private JFXTreeTableColumn<BankAccountModel, String> tcName;
    @FXML private JFXTreeTableColumn<BankAccountModel, Double> tcBalance;
    @FXML private JFXTreeTableColumn<BankAccountModel, String> tcCreateTime;
    @FXML private JFXTextField tfSearch;

    private ObservableList<BankAccountModel> bankAccountModels = FXCollections.observableArrayList();
    private BankAccountManagementBlService blService = BankAccountManagementBlServiceFactory.getInstance();

    public void onBtnFilterClicked(ActionEvent actionEvent) {
        updateItems();
    }

    public void initialize() {
        initTable();
        updateItems();
    }

    public void updateItems() {
        bankAccountModels.clear();

        BankAccountQueryVo queryVo = new BankAccountQueryVo();
        if (cbIsEnabled.isSelected()) {
            try {
                queryVo.between("createTime",
                    DateHelper.fromLocalDate(startDatePicker.getValue()),
                    DateHelper.fromLocalDate(endDatePicker.getValue()));
            } catch (Exception ignored) { }
            if (tfSearch.getText().length() > 0) {
                if (queryVo.conditionCount() > 0) {
                    queryVo.and();
                }
                queryVo.eq("name", tfSearch.getText());
            }
        }

        bankAccountModels.addAll(Arrays.stream(blService.query(queryVo)).map(BankAccountModel::new).collect(Collectors.toList()));

    }

    @FXML
    private void initTable() {
        tcId.setCellValueFactory(cellData -> cellData.getValue().getValue().idProperty().asObject());
        tcName.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        tcBalance.setCellValueFactory(cellData -> cellData.getValue().getValue().balanceProperty().asObject());
        tcCreateTime.setCellValueFactory(cellData -> new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getCreateTime())));
        TreeItem<BankAccountModel> root = new RecursiveTreeItem<>(bankAccountModels, RecursiveTreeObject::getChildren);
        tableBankAccounts.setRoot(root);
        tableBankAccounts.setShowRoot(false);
        tableBankAccounts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/bankaccountui/BankAccountUi.fxml").loadAndGetPackageWithoutException();
    }

    public BankAccountVo getSelected() {
        try {
            return tableBankAccounts.getSelectionModel().getSelectedItem().getValue().toBankAccountVo();
        } catch (Exception e) {
            new PromptDialogHelper("未选择！","请先选择一个账户！")
                .addCloseButton("好","CHECK", null)
                .createAndShow();
            return null;
        }

    }

    public boolean checkAccess() {
        FinanceStaffVo staffVo = (FinanceStaffVo) FrameworkUiManager.getCurrentEmployee();
        if (!staffVo.isRoot()) {
            PromptDialogHelper.start("无最高权限！","此功能仅供最高权限财务人员操作！")
                .addCloseButton("好","CHECK", null)
                .createAndShow();
            return false;
        }
        return true;
    }

    public void onBtnDeleteClicked(ActionEvent actionEvent) {
        if (!checkAccess()) {
            return;
        }
        BankAccountVo selected = getSelected();
        if (selected != null) {
            new PromptDialogHelper("确定删除",
                String.format("您确定要删除账户(id: %d, 名称: %s)吗？", selected.getId(), selected.getName()))
                .addButton("确定","CHECK",e -> {
                    blService.delete(selected.getId());
                    new PromptDialogHelper("删除成功",String.format("账户(id: %d)已经删除！", selected.getId()))
                        .addCloseButton("好","CHECK", e2 -> {
                            FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                            updateItems();
                        })
                        .createAndShow();
                })
                .addCloseButton("取消","CLOSE",null)
                .createAndShow();
        }
    }

    public void onBtnModifyClicked(ActionEvent actionEvent) {
        if (!checkAccess()) {
            return;
        }
        BankAccountVo selected = getSelected();
        if (selected != null) {
            new BankAccountModificationUi().show(selected, this::updateItems);
        }
    }

    public void onBtnAddClicked(ActionEvent actionEvent) {
        if (!checkAccess()) {
            return;
        }
        new BankAccountAddUi().show(this::updateItems);
    }
}
