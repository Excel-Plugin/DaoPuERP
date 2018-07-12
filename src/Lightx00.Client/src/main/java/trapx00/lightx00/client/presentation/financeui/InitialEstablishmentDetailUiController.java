package trapx00.lightx00.client.presentation.financeui;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import trapx00.lightx00.client.presentation.adminui.EmployeeSelection;
import trapx00.lightx00.client.presentation.adminui.factory.UserManagementUiFactory;
import trapx00.lightx00.client.presentation.bankaccountui.BankAccountModel;
import trapx00.lightx00.client.presentation.clientui.ClientSelectionItemModel;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelectionItemModel;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.financestaff.SystemSnapshotVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InitialEstablishmentDetailUiController extends BillDetailUi {
    public JFXTextField tfId;
    public JFXTextField tfDate;
    public JFXTextField tfOperator;
    @FXML
    private JFXTreeTableView<BankAccountModel> tableBankAccounts;
    @FXML private JFXTreeTableColumn<BankAccountModel, Integer> tcBankId;
    @FXML private JFXTreeTableColumn<BankAccountModel, String> tcBankName;
    @FXML private JFXTreeTableColumn<BankAccountModel, Double> tcBankBalance;

    @FXML
    private TreeTableView<ClientSelectionItemModel> tableClient;
    @FXML
    private TreeTableColumn<ClientSelectionItemModel, String> tcClientId;
    @FXML
    private TreeTableColumn<ClientSelectionItemModel, String> tcClientName;
    @FXML
    private TreeTableColumn<ClientSelectionItemModel, String> tcClientType;

    public JFXTreeTableView<CommoditySelectionItemModel> tableCommodity;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tcCommodityName;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tcCommodityId;

    private ObservableList<BankAccountModel> bankAccountModels = FXCollections.observableArrayList();
    private ObservableList<ClientSelectionItemModel> clientModels = FXCollections.observableArrayList();
    private ObservableList<CommoditySelectionItemModel> commodityModels = FXCollections.observableArrayList();


    public void initialize() {
        TableInitialization.initBankAccountTable(tableBankAccounts, tcBankId, tcBankName, tcBankBalance, bankAccountModels);
        TableInitialization.initClientTable(tableClient, tcClientId, tcClientName, tcClientType, clientModels);
        TableInitialization.initCommodityTable(tableCommodity, tcCommodityName, tcCommodityId, commodityModels);
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/InitialEstablishmentDetailUi.fxml").loadAndGetPackageWithoutException();
    }

    @Override
    public ExternalLoadedUiPackage showContent(BillVo arg) {
        SystemSnapshotVo snapshot = (SystemSnapshotVo) arg;
        ExternalLoadedUiPackage uiPackage = load();
        InitialEstablishmentDetailUiController controller = uiPackage.getController();
        EmployeeSelection employeeSelection = UserManagementUiFactory.getEmployeeSelectionUi();
        EmployeeVo operator = employeeSelection.queryId(arg.getOperatorId());
        controller.tfId.setText(arg.getId());
        controller.tfDate.setText(DateHelper.fromDate(arg.getDate()));
        controller.tfOperator.setText(String.format("%s(id: %s)", operator.getName(), operator.getId()));
        controller.bankAccountModels.addAll(Arrays.stream(snapshot.getBankAccounts()).map(BankAccountModel::new).collect(Collectors.toList()));
        controller.clientModels.addAll(Arrays.stream(snapshot.getClients()).map(ClientSelectionItemModel::new).collect(Collectors.toList()));
        controller.commodityModels.addAll(Arrays.stream(snapshot.getCommodities()).map(CommoditySelectionItemModel::new).collect(Collectors.toList()));
        return uiPackage;
    }





}
