package suwu.daopuerp.presentation.formulaui.oil;

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
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.formula.FormulaOilDto;
import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.presentation.formulaui.FormulaModifyUi;
import suwu.daopuerp.presentation.formulaui.FormulaUiController;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.presentation.stockui.factory.StackAddUiControllerFactory;
import suwu.daopuerp.presentation.stockui.formulastock.StockAddUiController;
import suwu.daopuerp.presentation.stockui.formulastock.StockItemModel;
import suwu.daopuerp.util.ContentUtil;

import java.util.ArrayList;
import java.util.List;

public class FormulaOilModifyUiController extends FormulaModifyUi implements ExternalLoadableUiController {
    @FXML
    private JFXTextField formulaId;
    @FXML
    private JFXTextField formulaCode;
    @FXML
    private JFXTextField formulaName;
    @FXML
    private JFXTextField formulaType;
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
    private StringProperty outLookingProperty = new SimpleStringProperty("");
    private StringProperty flashPointProperty = new SimpleStringProperty("");
    private StringProperty viscosityProperty = new SimpleStringProperty("");
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
        return new UiLoader("/fxml/formulaui/oil/FormulaOilModifyUi.fxml").loadAndGetPackageWithoutException();
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
        outLooking.textProperty().bindBidirectional(outLookingProperty);
        flashPoint.textProperty().bindBidirectional(flashPointProperty);
        viscosity.textProperty().bindBidirectional(viscosityProperty);
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
        outLooking.clear();
        flashPoint.clear();
        viscosity.clear();
        stableAttr1.setText("-5℃");
        stableAttr2.setText("50℃");
        stockItemModelObservableList.clear();
    }


    @Override
    public ExternalLoadedUiPackage showContent(FormulaDto formulaDto) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        FormulaOilDto formulaOilDto = (FormulaOilDto) formulaDto;
        FormulaOilModifyUiController formulaOilModifyUiController = externalLoadedUiPackage.getController();
        formulaOilModifyUiController.formulaId.setText(formulaOilDto.getFormulaId());
        formulaOilModifyUiController.formulaCode.setText(formulaOilDto.getFormulaCode());
        formulaOilModifyUiController.formulaName.setText(formulaOilDto.getFormulaName());
        formulaOilModifyUiController.formulaType.setText(formulaOilDto.getFormulaType());
        formulaOilModifyUiController.outLooking.setText(formulaOilDto.getOutLooking());
        formulaOilModifyUiController.flashPoint.setText(formulaOilDto.getFlashPoint());
        formulaOilModifyUiController.viscosity.setText(formulaOilDto.getViscosity());
        formulaOilModifyUiController.stableAttr1.setText(formulaOilDto.getStableAttr1());
        formulaOilModifyUiController.stableAttr2.setText(formulaOilDto.getStableAttr2());
        for (StockItem stockItem : formulaDto.getStockItems()) {
            formulaOilModifyUiController.stockItemModelObservableList.add(new StockItemModel(stockItem));
        }
        return externalLoadedUiPackage;
    }

    public void onBtnAddItemClicked(ActionEvent actionEvent) {
        stockAddUiController.showStockAdd(stockItem -> stockItemModelObservableList.add(new StockItemModel(stockItem)));
    }

    public void onBtnDeleteItemClicked(ActionEvent actionEvent) {
        int selectedIndex = stockTable.getSelectionModel().getSelectedIndex();
        stockItemModelObservableList.remove(selectedIndex);
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        try {
            submit();
            FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
            FrameworkUiManager.switchFunction(FormulaUiController.class, "管理配方单", true);
        } catch (Exception e) {
            PromptDialogHelper.start("提交失败！", "请将单据填写完整！")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        }
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
        formulaBlService.submit(getCurrentFormulaOilDto());
    }

    private FormulaOilDto getCurrentFormulaOilDto() {
        List<StockItem> stockItemList = stockItemModelObservableList.stream().collect(ArrayList::new, (list, stockItemModel) -> list.add(stockItemModel.getStockItemObjectProperty()), ArrayList::addAll);
        return new FormulaOilDto(formulaIdProperty.get(), formulaCodeProperty.get(), formulaNameProperty.get(), formulaTypeProperty.get(), stockItemList, stableAttr1Property.get(), stableAttr2Property.get(), outLookingProperty.get(), flashPointProperty.get(), viscosityProperty.get());
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}
