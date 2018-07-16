package suwu.daopuerp.presentation.productionbillui.liquid;

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
import suwu.daopuerp.dto.formula.StockItem;
import suwu.daopuerp.presentation.helpui.ExternalLoadableUiController;
import suwu.daopuerp.presentation.helpui.ExternalLoadedUiPackage;
import suwu.daopuerp.presentation.helpui.UiLoader;
import suwu.daopuerp.presentation.stockui.StockItemModel;

public class ProductionBillLiquidDetailUiController extends ProductionBillLiquidDetailUi implements ExternalLoadableUiController {
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

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/formulaui/FormulaDetailUi.fxml").loadAndGetPackageWithoutException();
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
    }

    @Override
    public ExternalLoadedUiPackage showContent(FormulaDto formulaDto) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        ProductionBillLiquidDetailUiController formulaDetailUiController = externalLoadedUiPackage.getController();
        formulaDetailUiController.formulaId.setText(formulaDto.getFormulaId());
        formulaDetailUiController.formulaName.setText(formulaDto.getFormulaName());
        formulaDetailUiController.formulaType.setText(formulaDto.getFormulaType());
        for (StockItem stockItem : formulaDto.getStockItems()) {
            formulaDetailUiController.stockItemModelObservableList.add(new StockItemModel(stockItem));
        }
        return externalLoadedUiPackage;
    }
}
