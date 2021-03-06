package suwu.daopuerp.presentation.formulaui.liquid;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import suwu.daopuerp.bl.formula.factory.FormulaBlServiceFactory;
import suwu.daopuerp.blservice.formula.FormulaBlService;
import suwu.daopuerp.dto.formula.FormulaLiquidDto;
import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.presentation.formulaui.FormulaUiController;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.presentation.stockui.factory.StackAddUiControllerFactory;
import suwu.daopuerp.presentation.stockui.formulastock.StockAddUiController;
import suwu.daopuerp.presentation.stockui.formulastock.StockItemModel;
import suwu.daopuerp.util.ContentUtil;

import java.util.ArrayList;
import java.util.List;

public class FormulaLiquidAddUiController implements ExternalLoadableUiController {
    @FXML
    private JFXTextField formulaId;
    @FXML
    private JFXTextField formulaCode;
    @FXML
    private JFXTextField formulaName;
    @FXML
    private JFXTextField formulaType;
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
    private JFXTreeTableView<StockItemModel> stockTable;
    @FXML
    private JFXTreeTableColumn<StockItemModel, String> stockIdColumn;
    @FXML
    private JFXTreeTableColumn<StockItemModel, String> stockNameColumn;
    @FXML
    private JFXTreeTableColumn<StockItemModel, String> stockPercentColumn;
    @FXML
    private JFXTreeTableColumn<StockItemModel, String> stockPriceColumn;
    @FXML
    private JFXTreeTableColumn<StockItemModel, String> stockProcessColumn;

    private ObservableList<StockItemModel> stockItemModelObservableList = FXCollections.observableArrayList();
    private StringProperty formulaIdProperty = new SimpleStringProperty("");
    private StringProperty formulaCodeProperty = new SimpleStringProperty("");
    private StringProperty formulaNameProperty = new SimpleStringProperty("");
    private StringProperty formulaTypeProperty = new SimpleStringProperty("");
    private StringProperty liquidLookingProperty = new SimpleStringProperty("");
    private StringProperty phValueProperty = new SimpleStringProperty("");
    private StringProperty lightValueProperty = new SimpleStringProperty("");
    private StringProperty stableAttr1Property = new SimpleStringProperty("");
    private StringProperty stableAttr2Property = new SimpleStringProperty("");

    private FormulaBlService formulaBlService = FormulaBlServiceFactory.getFormulaBlService();
    private StockAddUiController stockAddUiController = StackAddUiControllerFactory.getStackAddUiController();

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/formulaui/liquid/FormulaLiquidAddUi.fxml").loadAndGetPackageWithoutException();
    }

    public void initialize() {
        stockIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getStockItemObjectProperty().getStockId()));
        stockNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getStockItemObjectProperty().getStockName()));
        stockPercentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getStockItemObjectProperty().getStockPercent() + ""));
        stockPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getStockItemObjectProperty().getStockPrice() + ""));
        stockProcessColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getStockItemObjectProperty().getStockProcess()));
        TreeItem<StockItemModel> root = new RecursiveTreeItem<>(stockItemModelObservableList, RecursiveTreeObject::getChildren);
        stockTable.setRoot(root);
        stockTable.setShowRoot(false);

        formulaId.textProperty().bindBidirectional(formulaIdProperty);
        formulaCode.textProperty().bindBidirectional(formulaCodeProperty);
        formulaName.textProperty().bindBidirectional(formulaNameProperty);
        formulaType.textProperty().bindBidirectional(formulaTypeProperty);
        liquidLooking.textProperty().bindBidirectional(liquidLookingProperty);
        phValue.textProperty().bindBidirectional(phValueProperty);
        lightValue.textProperty().bindBidirectional(lightValueProperty);
        stableAttr1.textProperty().bindBidirectional(stableAttr1Property);
        stableAttr2.textProperty().bindBidirectional(stableAttr2Property);
        stableAttr1.setText("-5℃");
        stableAttr2.setText("50℃");

        stockTable.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.V) {
                String[] tuples = ContentUtil.getSysClipboardText().split("\n");
                for (String tuple : tuples) {
                    String[] attrs = tuple.split("\t");
                    StockItem stockItem;
                    if (attrs.length >= 6) {
                        stockItem = new StockItem(attrs[0], attrs[1], Double.parseDouble(attrs[2]), 0.0, attrs[5]);
                    } else {
                        stockItem = new StockItem(attrs[0], attrs[1], Double.parseDouble(attrs[2]), 0.0, "");
                    }
                    stockItemModelObservableList.add(new StockItemModel(stockItem));
                }
            }
        });

        autoFill();
    }

    private void autoFill() {
        try {
            formulaId.setText(formulaBlService.getNextId());
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
        formulaId.clear();
        formulaCode.clear();
        formulaName.clear();
        formulaType.clear();
        liquidLooking.clear();
        phValue.clear();
        lightValue.clear();
        stableAttr1.setText("-5℃");
        stableAttr2.setText("50℃");
        stockItemModelObservableList.clear();

        autoFill();
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        try {
            submit();
            reset();
        } catch (Exception e) {
            PromptDialogHelper.start("提交失败！", "请将单据填写完整！")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        }
    }

    private void submit() {
        formulaBlService.submit(getCurrentFormulaLiquidDto());
    }

    private FormulaLiquidDto getCurrentFormulaLiquidDto() {
        List<StockItem> stockItemList = stockItemModelObservableList.stream().collect(ArrayList::new, (list, stockItemModel) -> list.add(stockItemModel.getStockItemObjectProperty()), ArrayList::addAll);
        return new FormulaLiquidDto(formulaIdProperty.get(), formulaCodeProperty.get(), formulaNameProperty.get(), formulaTypeProperty.get(), stockItemList, stableAttr1Property.get(), stableAttr2Property.get(), liquidLookingProperty.get(), phValueProperty.get(), lightValueProperty.get());
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
        FrameworkUiManager.switchFunction(FormulaUiController.class, "管理配方单", true);
    }

    public void onBtnAddItemClicked(ActionEvent actionEvent) {
        stockAddUiController.showStockAdd(stockItem -> stockItemModelObservableList.add(new StockItemModel(stockItem)));
    }

    public void onBtnDeleteItemClicked(ActionEvent actionEvent) {
        int selectedIndex = stockTable.getSelectionModel().getSelectedIndex();
        stockItemModelObservableList.remove(selectedIndex);
    }
}
