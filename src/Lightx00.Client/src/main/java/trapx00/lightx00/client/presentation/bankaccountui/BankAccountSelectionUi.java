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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import trapx00.lightx00.client.blservice.bankaccountblservice.BankAccountManagementBlService;
import trapx00.lightx00.client.blservice.bankaccountblservice.BankAccountManagementBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.shared.queryvo.BankAccountQueryVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class BankAccountSelectionUi extends SelectingDialog implements BankAccountSelection {
    @FXML private JFXTreeTableView<BankAccountModel> tableBankAccounts;
    @FXML private JFXTreeTableColumn<BankAccountModel, Integer> tcId;
    @FXML private JFXTreeTableColumn<BankAccountModel, String> tcName;
    @FXML private JFXTreeTableColumn<BankAccountModel, Double> tcBalance;
    @FXML private JFXTreeTableColumn<BankAccountModel, String> tcCreateTime;
    @FXML private JFXTextField tfSearch;

    private ObservableList<BankAccountModel> bankAccountModels = FXCollections.observableArrayList();
    private Consumer<BankAccountVo> callback;
    private BankAccountManagementBlService blService = BankAccountManagementBlServiceFactory.getInstance();
    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {

        return new UiLoader("/fxml/bankaccountui/BankAccountSelectionUi.fxml").loadAndGetPackageWithoutException();
    }

    @FXML private void initialize() {
        initTable();
        initSearch();
        update();
    }

    private void initSearch() {
        tfSearch.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                if (tfSearch.getText().length() > 0) {
                    update(new BankAccountQueryVo().eq("name", tfSearch.getText()));
                } else {
                    update();
                }

            }
        });
    }

    private void update() {
        update(new BankAccountQueryVo());
    }

    private void update(BankAccountQueryVo queryVo) {
        BankAccountVo[] queryResult = blService.query(queryVo);
        bankAccountModels.clear();
        bankAccountModels.addAll(Arrays.stream(queryResult).map(BankAccountModel::new).collect(Collectors.toList()));
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
        tableBankAccounts.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                onBtnSelectClicked(null);
            }
        });
    }

    @Override
    public void showBankAccountSelectDialog(Consumer<BankAccountVo> callback) {
        ExternalLoadedUiPackage uiPackage = load();
        BankAccountSelectionUi controller = uiPackage.getController();
        controller.callback = callback;
        JFXDialog dialog = PromptDialogHelper.start("","").create();
        dialog.setContent((Region) uiPackage.getComponent());
        FrameworkUiManager.getCurrentDialogStack().pushAndShow(dialog);
    }

    @Override
    public BankAccountVo queryId(int id) {
        return blService.query(new BankAccountQueryVo().idEq(id))[0];
    }

    private BankAccountVo getSelected() {
        try {
            return tableBankAccounts.getSelectionModel().getSelectedItem().getValue().toBankAccountVo();
        } catch (Exception e) {
            return null;
        }

    }

    @FXML private void onBtnSelectClicked(ActionEvent actionEvent) {
        BankAccountVo selected = getSelected();
        if (callback != null && selected != null) {
            callback.accept(selected);
        }
        onClose();
    }

    @FXML private void onBtnCloseClicked(ActionEvent actionEvent) {
        onClose();
    }
}
