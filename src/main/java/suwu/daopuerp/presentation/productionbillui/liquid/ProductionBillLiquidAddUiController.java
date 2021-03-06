package suwu.daopuerp.presentation.productionbillui.liquid;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import suwu.daopuerp.bl.productionbill.factory.ProductionBillBlServiceFactory;
import suwu.daopuerp.blservice.productionbill.ProductionBillBlService;
import suwu.daopuerp.dto.formula.FormulaLiquidDto;
import suwu.daopuerp.dto.productionbill.ProductionBillLiquidDto;
import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.presentation.formulaui.FormulaSelectUi;
import suwu.daopuerp.presentation.formulaui.FormulaSelectUiController;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.presentation.productionbillui.ProductionBillStockItemModel;
import suwu.daopuerp.presentation.productionbillui.ProductionBillUiController;
import suwu.daopuerp.presentation.stockui.factory.ProductionStockAddUiControllerFactory;
import suwu.daopuerp.presentation.stockui.productionstock.ProductionBillStockItemAddUiController;
import suwu.daopuerp.publicdata.BillType;
import suwu.daopuerp.util.FormatDateTime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductionBillLiquidAddUiController implements ExternalLoadableUiController {
    @FXML
    private JFXTextField billId;
    @FXML
    private JFXTextField selectedProductionId;
    @FXML
    private JFXDatePicker productionDate;
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
    private ProductionBillStockItemAddUiController productionBillStockItemAddUiController = ProductionStockAddUiControllerFactory.getProductionStockAddUiController();
    private ProductionBillBlService productionBillBlService = ProductionBillBlServiceFactory.getProductionBillBlService();

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/productionbillui/liquid/ProductionBillLiquidAddUi.fxml").loadAndGetPackageWithoutException();
    }

    public void initialize() {
        stockIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillStockItemObjectProperty().getStockCode()));
        stockPredictAmountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillStockItemObjectProperty().getStockAmount() + ""));
        stockProcessColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillStockItemObjectProperty().getStockProcess()));
        TreeItem<ProductionBillStockItemModel> root = new RecursiveTreeItem<>(productionBillStockItemModelObservableList, RecursiveTreeObject::getChildren);
        stockTable.setRoot(root);
        stockTable.setShowRoot(false);

        productionBillStockItemModelObservableList.addListener((ListChangeListener<? super ProductionBillStockItemModel>) (productionBillStockItemModel) -> {
            double total = 0;
            for (ProductionBillStockItemModel stockItemModel : productionBillStockItemModelObservableList) {
                total += stockItemModel.getProductionBillStockItemObjectProperty().getStockAmount();
            }
            totalQuantityProperty.setValue(total + "");
        });

        billId.textProperty().bindBidirectional(billIdProperty);
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
        stableAttr1.setText("-5℃");
        stableAttr2.setText("50℃");
        autoFill();
    }

    private void autoFill() {
        try {
            billId.setText(productionBillBlService.getNextId(BillType.LIQUID));
            productionDate.setValue(LocalDate.now());
            billDate.setText(FormatDateTime.toShortDateString());
        } catch (Exception e) {
            e.printStackTrace();
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
        billId.clear();
        selectedProductionId.clear();
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
        stableAttr1.setText("-5℃");
        stableAttr2.setText("50℃");
        productionBillStockItemModelObservableList.clear();
        autoFill();
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        try {
            submit();
            onBtnResetClicked();
            autoFill();
        } catch (Exception e) {
            PromptDialogHelper.start("提交失败！", "请将单据填写完整！")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        }
    }

    private void submit() {
        productionBillBlService.submit(getCurrentProductionBillLiquidDto());
    }

    private ProductionBillLiquidDto getCurrentProductionBillLiquidDto() {
        List<ProductionBillStockItem> productionBillStockItems = productionBillStockItemModelObservableList.stream().collect(ArrayList::new, (list, item) -> list.add(item.getProductionBillStockItemObjectProperty()), ArrayList::addAll);
        ProductionBillLiquidDto productionBillLiquidDto = new ProductionBillLiquidDto(billIdProperty.get(), FormatDateTime.fromLocalDate(productionDate.getValue()), productionNameProperty.get(), billDateProperty.get(), clientProperty.get(), productionTypeProperty.get(), machineIdProperty.get(), productionIdProperty.get(), Double.parseDouble(totalQuantityProperty.get()), modifyRecordProperty.get(), commentProperty.get(), stableAttr1Property.get(), stableAttr2Property.get(), productionBillStockItems, liquidLookingProperty.get(), phValueProperty.get(), lightValueProperty.get());
        return productionBillLiquidDto;
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
        FrameworkUiManager.switchFunction(ProductionBillUiController.class, "管理生产原始单", true);
    }

    public void onBtnAddItemClicked(ActionEvent actionEvent) {
        productionBillStockItemAddUiController.showStockAdd(stockItem -> productionBillStockItemModelObservableList.add(new ProductionBillStockItemModel(new ProductionBillStockItem(stockItem.getStockCode(), stockItem.getStockAmount(), stockItem.getStockProcess()))));
    }

    public void onBtnDeleteItemClicked(ActionEvent actionEvent) {
        int selectedIndex = stockTable.getSelectionModel().getSelectedIndex();
        productionBillStockItemModelObservableList.remove(selectedIndex);
    }

    public void onSelectProductionClicked(MouseEvent mouseEvent) {
        formulaSelectUi.showFormulaSelectDialog(formulaAndAmountDto -> {
            if (formulaAndAmountDto.getFormulaDto().getBillType() != BillType.LIQUID) {
                PromptDialogHelper.start("选择失败！", "请选择液的配方单！")
                        .addCloseButton("好的", "CHECK", null)
                        .createAndShow();
            } else {
                double amount = formulaAndAmountDto.getAmount();
                FormulaLiquidDto formulaLiquidDto = (FormulaLiquidDto) formulaAndAmountDto.getFormulaDto();
                selectedProductionId.setText(formulaLiquidDto.getFormulaCode());
                productionName.setText(formulaLiquidDto.getFormulaName());
                billDate.setText(FormatDateTime.toShortDateString());
                productionType.setText(formulaLiquidDto.getFormulaType());
                productionId.setText(formulaLiquidDto.getFormulaCode());
                totalQuantity.setText(formulaAndAmountDto.getAmount() + "");
                liquidLooking.setText(formulaLiquidDto.getLiquidLooking());
                phValue.setText(formulaLiquidDto.getPhValue());
                lightValue.setText(formulaLiquidDto.getLightValue());
                stableAttr1.setText(formulaLiquidDto.getStableAttr1());
                stableAttr2.setText(formulaLiquidDto.getStableAttr2());
                List<ProductionBillStockItemModel> productionBillStockItemModels = new ArrayList<>();
                for (StockItem stockItem : formulaLiquidDto.getStockItems()) {
                    productionBillStockItemModels.add(new ProductionBillStockItemModel(new ProductionBillStockItem(stockItem.getStockId(), amount * stockItem.getStockPercent(), stockItem.getStockProcess())));
                }
                productionBillStockItemModelObservableList.addAll(productionBillStockItemModels);
            }
        }, BillType.LIQUID);
    }
}
