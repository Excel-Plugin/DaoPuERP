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
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.formula.FormulaLiquidDto;
import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.presentation.formulaui.FormulaDetailUi;
import suwu.daopuerp.presentation.helpui.ExternalLoadableUiController;
import suwu.daopuerp.presentation.helpui.ExternalLoadedUiPackage;
import suwu.daopuerp.presentation.helpui.UiLoader;
import suwu.daopuerp.presentation.stockui.formulastock.StockAddUiController;
import suwu.daopuerp.presentation.stockui.formulastock.StockItemModel;
import suwu.daopuerp.presentation.stockui.factory.StackAddUiControllerFactory;

public class FormulaLiquidDetailUiController extends FormulaDetailUi implements ExternalLoadableUiController {
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

    private StockAddUiController stockAddUiController = StackAddUiControllerFactory.getStackAddUiController();

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/formulaui/liquid/FormulaLiquidDetailUi.fxml").loadAndGetPackageWithoutException();
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
    }

    @Override
    public ExternalLoadedUiPackage showContent(FormulaDto formulaDto) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        FormulaLiquidDto formulaLiquidDto = (FormulaLiquidDto) formulaDto;
        FormulaLiquidDetailUiController formulaDetailUiController = externalLoadedUiPackage.getController();
        formulaDetailUiController.formulaId.setText(formulaLiquidDto.getFormulaId());
        formulaDetailUiController.formulaCode.setText(formulaLiquidDto.getFormulaCode());
        formulaDetailUiController.formulaName.setText(formulaLiquidDto.getFormulaName());
        formulaDetailUiController.formulaType.setText(formulaLiquidDto.getFormulaType());
        formulaDetailUiController.liquidLooking.setText(formulaLiquidDto.getLiquidLooking());
        formulaDetailUiController.lightValue.setText(formulaLiquidDto.getLightValue());
        formulaDetailUiController.phValue.setText(formulaLiquidDto.getPhValue());
        formulaDetailUiController.stableAttr1.setText(formulaLiquidDto.getStableAttr1());
        formulaDetailUiController.stableAttr2.setText(formulaLiquidDto.getStableAttr2());
        for (StockItem stockItem : formulaDto.getStockItems()) {
            formulaDetailUiController.stockItemModelObservableList.add(new StockItemModel(stockItem));
        }
        return externalLoadedUiPackage;
    }
}
