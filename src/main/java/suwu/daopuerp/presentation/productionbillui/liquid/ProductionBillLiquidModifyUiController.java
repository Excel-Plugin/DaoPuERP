package suwu.daopuerp.presentation.productionbillui.liquid;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import suwu.daopuerp.bl.productionbill.factory.ProductionBillBlServiceFactory;
import suwu.daopuerp.blservice.productionbill.ProductionBillService;
import suwu.daopuerp.dto.formula.FormulaLiquidDto;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillLiquidDto;
import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.presentation.formulaui.FormulaSelectUi;
import suwu.daopuerp.presentation.formulaui.FormulaSelectUiController;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.presentation.productionbillui.ProductionBillModifyUi;
import suwu.daopuerp.presentation.productionbillui.ProductionBillStockItemModel;
import suwu.daopuerp.presentation.stockui.StockAddUiController;
import suwu.daopuerp.presentation.stockui.factory.StackAddUiControllerFactory;
import suwu.daopuerp.util.FormatDateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductionBillLiquidModifyUiController extends ProductionBillModifyUi implements ExternalLoadableUiController {
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
    private JFXTextField liquidLooking;
    @FXML
    private JFXTextField phValue;
    @FXML
    private JFXTextField lightValue;
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
    private StringProperty commentProperty = new SimpleStringProperty("");
    private StringProperty liquidLookingProperty = new SimpleStringProperty("");
    private StringProperty phValueProperty = new SimpleStringProperty("");
    private StringProperty lightValueProperty = new SimpleStringProperty("");
    private StringProperty stableAttr1Property = new SimpleStringProperty("");
    private StringProperty stableAttr2Property = new SimpleStringProperty("");

    private FormulaSelectUi formulaSelectUi = new FormulaSelectUiController();
    private StockAddUiController stockAddUiController = StackAddUiControllerFactory.getStackAddUiController();
    private ProductionBillService productionBillService = ProductionBillBlServiceFactory.getProductionBillService();

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/productionbillui/liquid/ProductionBillLiquidModifyUi.fxml").loadAndGetPackageWithoutException();
    }

    public void initialize() {
        stockIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillStockItemObjectProperty().getStockId()));
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
        liquidLooking.textProperty().bindBidirectional(liquidLookingProperty);
        phValue.textProperty().bindBidirectional(phValueProperty);
        lightValue.textProperty().bindBidirectional(lightValueProperty);
        stableAttr1.textProperty().bindBidirectional(stableAttr1Property);
        stableAttr2.textProperty().bindBidirectional(stableAttr2Property);

        NumberValidator numberValidator = new NumberValidator();
        numberValidator.setMessage("请输入数字类型");
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.setMessage("请输入信息");
    }


    @Override
    public ExternalLoadedUiPackage showContent(ProductionBillDto productionBillDto) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        ProductionBillLiquidDto productionBillLiquidDto = (ProductionBillLiquidDto) productionBillDto;
        ProductionBillLiquidModifyUiController productionBillLiquidModifyUiController = externalLoadedUiPackage.getController();
        productionBillLiquidModifyUiController.billId.setText(productionBillLiquidDto.getBillId());
        productionBillLiquidModifyUiController.productionDate.setText(productionBillLiquidDto.getBillDate());
        productionBillLiquidModifyUiController.productionName.setText(productionBillLiquidDto.getProductionName());
        productionBillLiquidModifyUiController.billDate.setText(productionBillLiquidDto.getBillDate());
        productionBillLiquidModifyUiController.client.setText(productionBillLiquidDto.getClient());
        productionBillLiquidModifyUiController.productionType.setText(productionBillLiquidDto.getMachineId());
        productionBillLiquidModifyUiController.machineId.setText(productionBillLiquidDto.getMachineId());
        productionBillLiquidModifyUiController.productionId.setText(productionBillLiquidDto.getProductionId());
        productionBillLiquidModifyUiController.totalQuantity.setText(productionBillLiquidDto.getTotalQuantity() + "");
        productionBillLiquidModifyUiController.modifyRecord.setText(productionBillLiquidDto.getModifyRecord());
        productionBillLiquidModifyUiController.comment.setText(productionBillLiquidDto.getComment());
        productionBillLiquidModifyUiController.liquidLooking.setText(productionBillLiquidDto.getLiquidLooking());
        productionBillLiquidModifyUiController.phValue.setText(productionBillLiquidDto.getPhValue());
        productionBillLiquidModifyUiController.lightValue.setText(productionBillLiquidDto.getLightValue());
        productionBillLiquidModifyUiController.stableAttr1.setText(productionBillLiquidDto.getStableAttr1());
        productionBillLiquidModifyUiController.stableAttr2.setText(productionBillLiquidDto.getStableAttr2());
        for (ProductionBillStockItem productionBillStockItem : productionBillDto.getProductionBillStockItems()) {
            productionBillLiquidModifyUiController.productionBillStockItemModelObservableList.add(new ProductionBillStockItemModel(productionBillStockItem));
        }
        return externalLoadedUiPackage;
    }

    public void onBtnAddItemClicked(ActionEvent actionEvent) {
        stockAddUiController.showStockAdd(stockItem -> productionBillStockItemModelObservableList.add(new ProductionBillStockItemModel(new ProductionBillStockItem(stockItem.getStockId(), stockItem.getStockPercent() * Double.parseDouble(totalQuantityProperty.get()), stockItem.getStockProcess()))));
    }

    public void onBtnDeleteItemClicked(ActionEvent actionEvent) {
        int selectedIndex = stockTable.getSelectionModel().getSelectedIndex();
        productionBillStockItemModelObservableList.remove(selectedIndex);
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        submit();
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }

    private void submit() {
        productionBillService.submit(getCurrentProductionBillLiquidDto());
    }

    private ProductionBillLiquidDto getCurrentProductionBillLiquidDto() {
        List<ProductionBillStockItem> productionBillStockItems = productionBillStockItemModelObservableList.stream().collect(ArrayList::new, (list, item) -> list.add(item.getProductionBillStockItemObjectProperty()), ArrayList::addAll);
        ProductionBillLiquidDto productionBillLiquidDto = new ProductionBillLiquidDto(billIdProperty.get(), productionDateProperty.get(), productionNameProperty.get(), billDateProperty.get(), clientProperty.get(), productionTypeProperty.get(), machineIdProperty.get(), productionIdProperty.get(), Double.parseDouble(totalQuantityProperty.get()), modifyRecordProperty.get(), commentProperty.get(), stableAttr1Property.get(), stableAttr2Property.get(), productionBillStockItems, liquidLookingProperty.get(), phValueProperty.get(), lightValueProperty.get());
        return productionBillLiquidDto;
    }

    public void onBtnResetClicked(ActionEvent actionEvent) {
        PromptDialogHelper.start("是否要重置", null)
                .addCloseButton("确定", "DONE", e -> reset())
                .addCloseButton("取消", "UNDO", null)
                .createAndShow();
    }

    private void reset() {
        billId.clear();
        productionDate.clear();
        productionName.clear();
        billDate.clear();
        client.clear();
        productionType.clear();
        machineId.clear();
        productionId.clear();
        totalQuantity.clear();
        modifyRecord.clear();
        comment.clear();
        liquidLooking.clear();
        phValue.clear();
        lightValue.clear();
        stableAttr1.clear();
        stableAttr2.clear();
        productionBillStockItemModelObservableList.clear();
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }

    public void onSelectProductionClicked(MouseEvent mouseEvent) {
        formulaSelectUi.showFormulaSelectDialog(formulaDto -> {
            FormulaLiquidDto formulaLiquidDto = (FormulaLiquidDto) formulaDto;
            billId.setText(formulaLiquidDto.getFormulaCode());
            productionName.setText(formulaLiquidDto.getFormulaName());
            billDate.setText(FormatDateTime.toLongTimeString(new Date()));
            productionType.setText(formulaLiquidDto.getFormulaType());
            productionId.setText(formulaLiquidDto.getFormulaCode());
            liquidLooking.setText(formulaLiquidDto.getLiquidLooking());
            phValue.setText(formulaLiquidDto.getPhValue());
            lightValue.setText(formulaLiquidDto.getLightValue());
            stableAttr1.setText(formulaLiquidDto.getStableAttr1());
            stableAttr2.setText(formulaLiquidDto.getStableAttr2());
            List<ProductionBillStockItemModel> productionBillStockItemModels = new ArrayList<>();
            for (StockItem stockItem : formulaDto.getStockItems()) {
                productionBillStockItemModels.add(new ProductionBillStockItemModel(new ProductionBillStockItem(stockItem.getStockId(), stockItem.getStockPercent(), stockItem.getStockProcess())));
            }
            productionBillStockItemModelObservableList.addAll(productionBillStockItemModels);
        });
    }
}
