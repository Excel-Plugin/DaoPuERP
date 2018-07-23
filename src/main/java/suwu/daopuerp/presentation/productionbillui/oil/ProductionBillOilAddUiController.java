package suwu.daopuerp.presentation.productionbillui.oil;

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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import suwu.daopuerp.bl.productionbill.factory.ProductionBillBlServiceFactory;
import suwu.daopuerp.blservice.productionbill.ProductionBillService;
import suwu.daopuerp.dto.formula.FormulaOilDto;
import suwu.daopuerp.dto.productionbill.ProductionBillOilDto;
import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.presentation.formulaui.FormulaSelectUi;
import suwu.daopuerp.presentation.formulaui.FormulaSelectUiController;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.presentation.productionbillui.ProductionBillStockItemModel;
import suwu.daopuerp.presentation.productionbillui.ProductionBillUiController;
import suwu.daopuerp.presentation.stockui.StockAddUiController;
import suwu.daopuerp.presentation.stockui.factory.StackAddUiControllerFactory;
import suwu.daopuerp.publicdata.BillType;
import suwu.daopuerp.util.FormatDateTime;

import java.util.ArrayList;
import java.util.List;

public class ProductionBillOilAddUiController implements ExternalLoadableUiController {
    @FXML
    private JFXTextField billId;
    @FXML
    private JFXTextField selectedProductionId;
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
        return new UiLoader("/fxml/productionbillui/oil/ProductionBillOilAddUi.fxml").loadAndGetPackageWithoutException();
    }

    public void initialize() {
        stockIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillStockItemObjectProperty().getStockId()));
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

        NumberValidator numberValidator = new NumberValidator();
        numberValidator.setMessage("请输入数字类型");
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.setMessage("请输入信息");

        autoFill();
    }

    private void autoFill() {
        try {
            billId.setText(productionBillService.getNextId(BillType.LIQUID));
            billDate.setText(FormatDateTime.toShortDateString());
        } catch (Exception e) {
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
        outLooking.clear();
        flashPoint.clear();
        viscosity.clear();
        stableAttr1.clear();
        stableAttr2.clear();
        productionBillStockItemModelObservableList.clear();
        autoFill();
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        submit();
        reset();
    }

    private void submit() {
        productionBillService.submit(getCurrentProductionBillOilDto());
    }

    private ProductionBillOilDto getCurrentProductionBillOilDto() {
        List<ProductionBillStockItem> productionBillStockItems = productionBillStockItemModelObservableList.stream().collect(ArrayList::new, (list, item) -> list.add(item.getProductionBillStockItemObjectProperty()), ArrayList::addAll);
        ProductionBillOilDto productionBillOilDto = new ProductionBillOilDto(billIdProperty.get(), productionDateProperty.get(), productionNameProperty.get(), billDateProperty.get(), clientProperty.get(), productionTypeProperty.get(), machineIdProperty.get(), productionIdProperty.get(), Double.parseDouble(totalQuantityProperty.get()), modifyRecordProperty.get(), commentProperty.get(), stableAttr1Property.get(), stableAttr2Property.get(), productionBillStockItems, outLookingProperty.get(), flashPointProperty.get(), viscosityProperty.get());
        return productionBillOilDto;
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
        FrameworkUiManager.switchFunction(ProductionBillUiController.class, "管理生产原始单", true);
    }

    public void onBtnAddItemClicked(ActionEvent actionEvent) {
        stockAddUiController.showStockAdd(stockItem -> productionBillStockItemModelObservableList.add(new ProductionBillStockItemModel(new ProductionBillStockItem(stockItem.getStockId(), stockItem.getStockPercent() * Double.parseDouble(totalQuantityProperty.get()), stockItem.getStockProcess()))));
    }

    public void onBtnDeleteItemClicked(ActionEvent actionEvent) {
        int selectedIndex = stockTable.getSelectionModel().getSelectedIndex();
        productionBillStockItemModelObservableList.remove(selectedIndex);
    }

    public void onSelectProductionClicked(MouseEvent mouseEvent) {
        formulaSelectUi.showFormulaSelectDialog(formulaAndAmountDto -> {
            FormulaOilDto formulaOilDto = (FormulaOilDto) formulaAndAmountDto.getFormulaDto();
            selectedProductionId.setText(formulaOilDto.getFormulaCode());
            productionName.setText(formulaOilDto.getFormulaName());
            billDate.setText(FormatDateTime.toLongTimeString());
            productionType.setText(formulaOilDto.getFormulaType());
            productionId.setText(formulaOilDto.getFormulaCode());
            totalQuantity.setText(formulaAndAmountDto.getAmount() + "");
            outLooking.setText(formulaOilDto.getOutLooking());
            flashPoint.setText(formulaOilDto.getFlashPoint());
            viscosity.setText(formulaOilDto.getViscosity());
            stableAttr1.setText(formulaOilDto.getStableAttr1());
            stableAttr2.setText(formulaOilDto.getStableAttr2());
            List<ProductionBillStockItemModel> productionBillStockItemModels = new ArrayList<>();
            for (StockItem stockItem : formulaOilDto.getStockItems()) {
                productionBillStockItemModels.add(new ProductionBillStockItemModel(new ProductionBillStockItem(stockItem.getStockId(), formulaAndAmountDto.getAmount() * stockItem.getStockPercent(), stockItem.getStockProcess())));
            }
            productionBillStockItemModelObservableList.addAll(productionBillStockItemModels);
        });
    }
}
