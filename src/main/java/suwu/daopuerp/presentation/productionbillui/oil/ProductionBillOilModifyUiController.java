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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.presentation.productionbillui.ProductionBillModifyUi;
import suwu.daopuerp.presentation.stockui.StockAddUiController;
import suwu.daopuerp.presentation.stockui.StockItemModel;
import suwu.daopuerp.presentation.stockui.factory.StackAddUiControllerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductionBillOilModifyUiController extends ProductionBillModifyUi implements ExternalLoadableUiController {
    @FXML
    private JFXTextField formulaId;
    @FXML
    private JFXTextField formulaName;
    @FXML
    private JFXTextField formulaType;
    @FXML
    private JFXTreeTableView<StockItemModel> stockTable;
    @FXML
    private JFXTreeTableColumn<StockItemModel, String> stockIdColumn;
    @FXML
    private JFXTreeTableColumn<StockItemModel, String> stockNameColumn;
    @FXML
    private JFXTreeTableColumn<StockItemModel, String> stockPercentColumn;
    @FXML
    private JFXTreeTableColumn<StockItemModel, String> stockPriceColumn;

    private ObservableList<StockItemModel> stockItemModelObservableList = FXCollections.observableArrayList();
    private StringProperty formulaIdProperty = new SimpleStringProperty("");
    private StringProperty formulaNameProperty = new SimpleStringProperty("");
    private StringProperty formulaTypeProperty = new SimpleStringProperty("");

    private StockAddUiController stockAddUiController = StackAddUiControllerFactory.getStackAddUiController();

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/productionbillui/oil/ProductionBillOilModifyUi.fxml").loadAndGetPackageWithoutException();
    }

    public void initialize() {
        stockIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getStockItemObjectProperty().getStockId()));
        stockNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getStockItemObjectProperty().getStockName()));
        stockPercentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getStockItemObjectProperty().getStockPercent() + ""));
        stockPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getStockItemObjectProperty().getStockPrice() + ""));
        TreeItem<StockItemModel> root = new RecursiveTreeItem<>(stockItemModelObservableList, RecursiveTreeObject::getChildren);
        stockTable.setRoot(root);
        stockTable.setShowRoot(false);

        formulaId.textProperty().bindBidirectional(formulaIdProperty);
        formulaName.textProperty().bindBidirectional(formulaNameProperty);
        formulaType.textProperty().bindBidirectional(formulaTypeProperty);

        NumberValidator numberValidator = new NumberValidator();
        numberValidator.setMessage("请输入数字类型");
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.setMessage("请输入信息");
    }


    @Override
    public ExternalLoadedUiPackage showContent(ProductionBillDto productionBillDto) {
        return null;
    }

    public void onBtnAddItemClicked(ActionEvent actionEvent) {
        stockAddUiController.showStockAdd(stockItem -> stockItemModelObservableList.add(new StockItemModel(stockItem)));
    }

    public void onBtnDeleteItemClicked(ActionEvent actionEvent) {
        int selectedIndex = stockTable.getSelectionModel().getSelectedIndex();
        stockItemModelObservableList.remove(selectedIndex);
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        submit();
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
//        FormulaDto formulaDto = getCurrentFormulaDto();
//
//        PromptDialogHelper.start("确认配方单", "").setContent(
//                purchaseBillVo.billDetailUi().showContent(purchaseBillVo).getComponent())
//                .addCloseButton("确定", "CHECK", e -> {
//                    submit();
//                })
//                .addCloseButton("取消", "CLOSE", null)
//                .createAndShow();
//        FrameworkUiManager.getWholePane().setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                initHotKey();
//                FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
//                submit();
//            }
//        });
    }

    private void submit() {

    }

    private FormulaDto getCurrentFormulaDto() {
        List<StockItem> stockItemList = stockItemModelObservableList.stream().collect(ArrayList::new, (list, stockItemModel) -> list.add(stockItemModel.getStockItemObjectProperty()), ArrayList::addAll);
        return new FormulaDto(formulaIdProperty.get(), formulaNameProperty.get(), formulaTypeProperty.get(), stockItemList);
    }

    public void onBtnResetClicked(ActionEvent actionEvent) {
        PromptDialogHelper.start("是否要重置", null)
                .addCloseButton("确定", "DONE", e -> reset())
                .addCloseButton("取消", "UNDO", null)
                .createAndShow();
    }

    private void reset() {
        formulaId.clear();
        formulaName.clear();
        formulaType.clear();
        stockItemModelObservableList.clear();
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}
