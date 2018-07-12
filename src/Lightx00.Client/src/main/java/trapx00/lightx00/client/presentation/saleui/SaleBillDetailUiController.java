package trapx00.lightx00.client.presentation.saleui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.EmployeeInfoFactory;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlService;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.FrameworkUiManager;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.presentation.inventoryui.CommodityItemModel;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;

public class SaleBillDetailUiController extends BillDetailUi {
    @FXML
    JFXTextField tfBillId;
    @FXML
    JFXTextField tfSalesmanId;
    @FXML
    JFXTextField tfSalesmanName;
    @FXML
    JFXTextField tfOperator;
    @FXML
    JFXTextField tfDate;
    @FXML
    JFXTextField tfClientId;
    @FXML
    JFXTextField tfClientName;
    @FXML
    JFXTextField tfClientLevel;
    @FXML
    JFXComboBox<String> cbRepository;
    @FXML
    JFXTextField tfOriginTotal;
    @FXML
    JFXTextField tfMinusProfits;
    @FXML
    JFXTextField tfToken;
    @FXML
    private JFXTextField tfUltiTotal;
    @FXML
    JFXTextField tfPromotionId;
    @FXML
    JFXTextField tfGiftToken;
    @FXML
    JFXTextField tfComment;
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

    @FXML
    private JFXTreeTableView<CommodityItemModel> tbGiftList;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftIdColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftNameColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftTypeColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftNumberColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftPriceColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftTotalColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftCommentColumn;

    private ObjectProperty<EmployeeVo> currentEmployee = new SimpleObjectProperty<>();
    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
    private ObservableList<CommodityItemModel> commodityItemModelObservableList = FXCollections.observableArrayList();
    private ObservableList<CommodityItemModel> giftItemModelObservableList = FXCollections.observableArrayList();
    private ClientBlService clientBlService= ClientBlServiceFactory.getInstance();

    @Override
    public ExternalLoadedUiPackage showContent(BillVo arg) {
        currentEmployee.setValue(FrameworkUiManager.getCurrentEmployee());
        SaleBillVo saleBillVo=(SaleBillVo) arg;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        SaleBillDetailUiController saleBillDetailUiController = externalLoadedUiPackage.getController();
        ObservableList<String> stringObservableList = FXCollections.observableArrayList(
                "1", "2", "3"
        );
        saleBillDetailUiController.cbRepository.setItems(stringObservableList);

        saleBillDetailUiController.tfBillId.setText(saleBillVo.getId());
        saleBillDetailUiController.tfDate.setText(saleBillVo.getDate().toString());
        saleBillDetailUiController.tfSalesmanId.setText(saleBillVo.getSalesmanId());
        saleBillDetailUiController.tfSalesmanName.setText(employeeInfo.queryById(saleBillVo.getSalesmanId()).getName());
        saleBillDetailUiController.tfOperator.setText(String.format("%s(id: %s)", currentEmployee.getValue().getName(), currentEmployee.getValue().getId()));
        saleBillDetailUiController.tfClientId.setText(saleBillVo.getClientId());
        saleBillDetailUiController.tfClientName.setText(clientBlService.queryById(saleBillVo.getClientId()).getName());
        saleBillDetailUiController.tfClientLevel.setText(clientBlService.queryById(saleBillVo.getClientId()).getClientLevel()+"");
        saleBillDetailUiController.cbRepository.setValue(saleBillVo.getRepository() + "");
        saleBillDetailUiController.tfOriginTotal.setText(saleBillVo.getOriginTotal() + "");
        saleBillDetailUiController.tfMinusProfits.setText(saleBillVo.getMinusProfits() + "");
        saleBillDetailUiController.tfToken.setText(saleBillVo.getToken() + "");
        saleBillDetailUiController.tfUltiTotal.setText(saleBillVo.getUltiTotal()+"");
        saleBillDetailUiController.tfPromotionId.setText(saleBillVo.getPromotionId());
        saleBillDetailUiController.tfGiftToken.setText(saleBillVo.getGiftToken()+"");
        saleBillDetailUiController.tfComment.setText(saleBillVo.getComment());
        saleBillDetailUiController.addCommodityListItems(saleBillVo.getCommodityList());
        saleBillDetailUiController.addGiftListItems(saleBillVo.getGiftList());
        return externalLoadedUiPackage;
    }

    public void addCommodityListItems(CommodityItem[] commodityItems) {
        for (CommodityItem commodityItem : commodityItems) {
            commodityItemModelObservableList.add(new CommodityItemModel(commodityItem));
        }
    }

    public void addGiftListItems(CommodityItem[] commodityItems) {
        for (CommodityItem commodityItem : commodityItems) {
            giftItemModelObservableList.add(new CommodityItemModel(commodityItem));
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

        tcGiftIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getCommodityId()));
        tcGiftNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getName()));
        tcGiftTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getType()));
        tcGiftNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getNumber() + ""));
        tcGiftPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getPrice() + ""));
        tcGiftTotalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getTotal() + ""));
        tcGiftCommentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getComment()));
        TreeItem<CommodityItemModel> rootGift = new RecursiveTreeItem<>(giftItemModelObservableList, RecursiveTreeObject::getChildren);
        tbGiftList.setRoot(rootGift);
        tbGiftList.setShowRoot(false);
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/saleui/SaleBillDetailUi.fxml").loadAndGetPackageWithoutException();
    }
}
