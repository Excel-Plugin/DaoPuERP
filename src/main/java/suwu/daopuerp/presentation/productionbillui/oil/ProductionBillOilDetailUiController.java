package suwu.daopuerp.presentation.productionbillui.oil;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import suwu.daopuerp.bl.productionbill.factory.ProductionBillBlServiceFactory;
import suwu.daopuerp.blservice.productionbill.ProductionBillBlService;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillOilDto;
import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.presentation.helpui.ExternalLoadableUiController;
import suwu.daopuerp.presentation.helpui.ExternalLoadedUiPackage;
import suwu.daopuerp.presentation.helpui.UiLoader;
import suwu.daopuerp.presentation.productionbillui.ProductionBillDetailUi;
import suwu.daopuerp.presentation.productionbillui.ProductionBillStockItemModel;
import suwu.daopuerp.presentation.stockui.factory.ProductionStockAddUiControllerFactory;
import suwu.daopuerp.presentation.stockui.productionstock.ProductionBillStockItemAddUiController;
import suwu.daopuerp.util.FormatDateTime;

public class ProductionBillOilDetailUiController extends ProductionBillDetailUi implements ExternalLoadableUiController {
    @FXML
    private JFXTextField billId;
    @FXML
    private JFXTextField productionDate;
    @FXML
    private JFXTextField productionName;
    @FXML
    private JFXTextField billDate;
    @FXML
    private JFXTextField client;
    @FXML
    private JFXTextField productionType;
    @FXML
    private JFXTextField machineId;
    @FXML
    private JFXTextField productionId;
    @FXML
    private JFXTextField totalQuantity;
    @FXML
    private JFXTextField modifyRecord;
    @FXML
    private JFXTextField comment;
    @FXML
    private JFXTextField outLooking;
    @FXML
    private JFXTextField flashPoint;
    @FXML
    private JFXTextField viscosity;
    @FXML
    private JFXTextField stableAttr1;
    @FXML
    private JFXTextField stableAttr2;
    @FXML
    private JFXTreeTableView<ProductionBillStockItemModel> stockTable;
    @FXML
    private JFXTreeTableColumn<ProductionBillStockItemModel, String> stockIdColumn;
    @FXML
    private JFXTreeTableColumn<ProductionBillStockItemModel, String> stockPredictAmountColumn;
    @FXML
    private JFXTreeTableColumn<ProductionBillStockItemModel, String> stockProcessColumn;

    private ObservableList<ProductionBillStockItemModel> productionBillStockItemModelObservableList = FXCollections.observableArrayList();
    private StringProperty billIdProperty = new SimpleStringProperty("");
    private StringProperty productionDateProperty = new SimpleStringProperty("");
    private StringProperty productionNameProperty = new SimpleStringProperty("");
    private StringProperty billDateProperty = new SimpleStringProperty("");
    private StringProperty clientProperty = new SimpleStringProperty("");
    private StringProperty productionTypeProperty = new SimpleStringProperty("");
    private StringProperty machineIdProperty = new SimpleStringProperty("");
    private StringProperty productionIdProperty = new SimpleStringProperty("");
    private StringProperty totalQuantityProperty = new SimpleStringProperty("");
    private StringProperty modifyRecordProperty = new SimpleStringProperty("");
    private StringProperty outLookingProperty = new SimpleStringProperty("");
    private StringProperty flashPointProperty = new SimpleStringProperty("");
    private StringProperty commentProperty = new SimpleStringProperty("");
    private StringProperty viscosityProperty = new SimpleStringProperty("");
    private StringProperty stableAttr1Property = new SimpleStringProperty("");
    private StringProperty stableAttr2Property = new SimpleStringProperty("");

    private ProductionBillStockItemAddUiController productionBillStockItemAddUiController = ProductionStockAddUiControllerFactory.getProductionStockAddUiController();
    private ProductionBillBlService productionBillBlService = ProductionBillBlServiceFactory.getProductionBillBlService();

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/productionbillui/oil/ProductionBillOilDetailUi.fxml").loadAndGetPackageWithoutException();
    }

    public void initialize() {
        stockIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillStockItemObjectProperty().getStockCode()));
        stockPredictAmountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillStockItemObjectProperty().getStockAmount() + ""));
        stockProcessColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillStockItemObjectProperty().getStockProcess()));
        TreeItem<ProductionBillStockItemModel> root = new RecursiveTreeItem<>(productionBillStockItemModelObservableList, RecursiveTreeObject::getChildren);
        stockTable.setRoot(root);
        stockTable.setShowRoot(false);

        billId.textProperty().bindBidirectional(billIdProperty);
        productionDate.textProperty().bindBidirectional(productionDateProperty);
        productionName.textProperty().bindBidirectional(productionNameProperty);
        billDate.textProperty().bindBidirectional(billDateProperty);
        client.textProperty().bindBidirectional(clientProperty);
        productionType.textProperty().bindBidirectional(productionTypeProperty);
        machineId.textProperty().bindBidirectional(machineIdProperty);
        productionId.textProperty().bindBidirectional(productionIdProperty);
        totalQuantity.textProperty().bindBidirectional(totalQuantityProperty);
        modifyRecord.textProperty().bindBidirectional(modifyRecordProperty);
        comment.textProperty().bindBidirectional(commentProperty);
        outLooking.textProperty().bindBidirectional(outLookingProperty);
        flashPoint.textProperty().bindBidirectional(flashPointProperty);
        viscosity.textProperty().bindBidirectional(viscosityProperty);
        stableAttr1.textProperty().bindBidirectional(stableAttr1Property);
        stableAttr2.textProperty().bindBidirectional(stableAttr2Property);
    }


    @Override
    public ExternalLoadedUiPackage showContent(ProductionBillDto productionBillDto) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        ProductionBillOilDto productionBillOilDto = (ProductionBillOilDto) productionBillDto;
        ProductionBillOilDetailUiController productionBillOilDetailUiController = externalLoadedUiPackage.getController();
        productionBillOilDetailUiController.billId.setText(productionBillOilDto.getBillId());
        productionBillOilDetailUiController.productionDate.setText(FormatDateTime.toShortDateString(productionBillOilDto.getProductionDate()));
        productionBillOilDetailUiController.productionName.setText(productionBillOilDto.getProductionName());
        productionBillOilDetailUiController.billDate.setText(productionBillOilDto.getBillDate());
        productionBillOilDetailUiController.client.setText(productionBillOilDto.getClient());
        productionBillOilDetailUiController.productionType.setText(productionBillOilDto.getMachineId());
        productionBillOilDetailUiController.machineId.setText(productionBillOilDto.getMachineId());
        productionBillOilDetailUiController.productionId.setText(productionBillOilDto.getProductionId());
        productionBillOilDetailUiController.totalQuantity.setText(productionBillOilDto.getTotalQuantity() + "");
        productionBillOilDetailUiController.modifyRecord.setText(productionBillOilDto.getModifyRecord());
        productionBillOilDetailUiController.comment.setText(productionBillOilDto.getComment());
        productionBillOilDetailUiController.outLooking.setText(productionBillOilDto.getOutLooking());
        productionBillOilDetailUiController.flashPoint.setText(productionBillOilDto.getFlashPoint());
        productionBillOilDetailUiController.viscosity.setText(productionBillOilDto.getViscosity());
        productionBillOilDetailUiController.stableAttr1.setText(productionBillOilDto.getStableAttr1());
        productionBillOilDetailUiController.stableAttr2.setText(productionBillOilDto.getStableAttr2());
        for (ProductionBillStockItem productionBillStockItem : productionBillDto.getProductionBillStockItems()) {
            productionBillOilDetailUiController.productionBillStockItemModelObservableList.add(new ProductionBillStockItemModel(productionBillStockItem));
        }
        return externalLoadedUiPackage;
    }
}
