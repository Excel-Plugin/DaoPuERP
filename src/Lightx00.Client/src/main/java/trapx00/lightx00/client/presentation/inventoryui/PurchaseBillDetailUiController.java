package trapx00.lightx00.client.presentation.inventoryui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlService;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.FrameworkUiManager;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.salestaff.PurchaseBillVo;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;

public class PurchaseBillDetailUiController extends BillDetailUi {
    @FXML
    JFXTextField tfBillId;
    @FXML
    JFXTextField tfOperator;
    @FXML
    JFXTextField tfClientId;
    @FXML
    JFXComboBox<String> cbRepository;
    @FXML
    JFXTextField tfBillTotal;
    @FXML
    private JFXTextField tfDate;
    @FXML
    private JFXTextField tfClientName;
    @FXML
    private JFXTextField tfComment;
    @FXML
    private JFXTreeTableView<CommodityItemModel> tbCommodityList;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityIdColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityNameColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityTypeColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityNumberColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityPriceColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityTotalColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityCommentColumn;

    private ObjectProperty<EmployeeVo> currentEmployee = new SimpleObjectProperty<>();
    private ClientBlService clientBlService= ClientBlServiceFactory.getInstance();
    private ObservableList<CommodityItemModel> commodityItemModelObservableList = FXCollections.observableArrayList();

    @Override
    public ExternalLoadedUiPackage showContent(BillVo arg) {
        currentEmployee.setValue(FrameworkUiManager.getCurrentEmployee());

        PurchaseBillVo purchaseBillVo = (PurchaseBillVo) arg;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        PurchaseBillDetailUiController purchaseBillDetailUiController = externalLoadedUiPackage.getController();
        ObservableList<String> stringObservableList = FXCollections.observableArrayList(
                "1", "2", "3"
        );
        purchaseBillDetailUiController.cbRepository.setItems(stringObservableList);

        purchaseBillDetailUiController.tfBillId.setText(purchaseBillVo.getId());
        purchaseBillDetailUiController.tfDate.setText(purchaseBillVo.getDate().toString());
        purchaseBillDetailUiController.tfOperator.setText(String.format("%s(id: %s)", currentEmployee.getValue().getName(), currentEmployee.getValue().getId()));
        purchaseBillDetailUiController.tfClientId.setText(purchaseBillVo.getClientId());
        purchaseBillDetailUiController.tfClientName.setText(clientBlService.queryById(purchaseBillVo.getClientId()).getName());
        purchaseBillDetailUiController.cbRepository.setValue(purchaseBillVo.getRepository() + "");
        purchaseBillDetailUiController.tfBillTotal.setText(purchaseBillVo.getTotal() + "");
        purchaseBillDetailUiController.tfComment.setText(purchaseBillVo.getComment());
        purchaseBillDetailUiController.addCommodityListItems(purchaseBillVo.getCommodityList());
        return externalLoadedUiPackage;
    }

    public void addCommodityListItems(CommodityItem[] commodityItems) {
        for (CommodityItem commodityItem : commodityItems) {
            commodityItemModelObservableList.add(new CommodityItemModel(commodityItem));
        }
    }

    public void initialize() {
        tcCommodityIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getCommodityId()));
        tcCommodityNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getName()));
        tcCommodityTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getType()));
        tcCommodityNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getNumber() + ""));
        tcCommodityPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getPrice() + ""));
        tcCommodityTotalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getTotal() + ""));
        tcCommodityCommentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getComment()));
        TreeItem<CommodityItemModel> root = new RecursiveTreeItem<>(commodityItemModelObservableList, RecursiveTreeObject::getChildren);
        tbCommodityList.setRoot(root);
        tbCommodityList.setShowRoot(false);
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/PurchaseBillDetailUi.fxml").loadAndGetPackageWithoutException();
    }
}
