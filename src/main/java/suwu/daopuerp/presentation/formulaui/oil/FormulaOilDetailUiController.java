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
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.formula.FormulaOilDto;
import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.presentation.formulaui.FormulaDetailUi;
import suwu.daopuerp.presentation.helpui.ExternalLoadableUiController;
import suwu.daopuerp.presentation.helpui.ExternalLoadedUiPackage;
import suwu.daopuerp.presentation.helpui.UiLoader;
import suwu.daopuerp.presentation.stockui.formulastock.StockAddUiController;
import suwu.daopuerp.presentation.stockui.formulastock.StockItemModel;
import suwu.daopuerp.presentation.stockui.factory.StackAddUiControllerFactory;

public class FormulaOilDetailUiController extends FormulaDetailUi implements ExternalLoadableUiController {
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

    private StockAddUiController stockAddUiController = StackAddUiControllerFactory.getStackAddUiController();

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/formulaui/oil/FormulaOilDetailUi.fxml").loadAndGetPackageWithoutException();
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
    }

    @Override
    public ExternalLoadedUiPackage showContent(FormulaDto formulaDto) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        FormulaOilDto formulaOilDto = (FormulaOilDto) formulaDto;
        FormulaOilDetailUiController formulaOilDetailUiController = externalLoadedUiPackage.getController();
        formulaOilDetailUiController.formulaId.setText(formulaOilDto.getFormulaId());
        formulaOilDetailUiController.formulaCode.setText(formulaOilDto.getFormulaCode());
        formulaOilDetailUiController.formulaName.setText(formulaOilDto.getFormulaName());
        formulaOilDetailUiController.formulaType.setText(formulaOilDto.getFormulaType());
        formulaOilDetailUiController.flashPoint.setText(formulaOilDto.getFlashPoint());
        formulaOilDetailUiController.outLooking.setText(formulaOilDto.getOutLooking());
        formulaOilDetailUiController.viscosity.setText(formulaOilDto.getViscosity());
        formulaOilDetailUiController.stableAttr1.setText(formulaOilDto.getStableAttr1());
        formulaOilDetailUiController.stableAttr2.setText(formulaOilDto.getStableAttr2());
        for (StockItem stockItem : formulaDto.getStockItems()) {
            formulaOilDetailUiController.stockItemModelObservableList.add(new StockItemModel(stockItem));
        }
        return externalLoadedUiPackage;
    }
}
