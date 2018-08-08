package suwu.daopuerp.presentation.bomui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import suwu.daopuerp.bl.productionbill.factory.ProductionBillBlServiceFactory;
import suwu.daopuerp.blservice.productionbill.ProductionBillBlService;
import suwu.daopuerp.dto.bom.IdAmountTuple;
import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.entity.productionbill.ProductionBill;
import suwu.daopuerp.exception.ExcelCreateFailException;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.util.ExcelOutput;
import suwu.daopuerp.util.FormatDateTime;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BomUiController implements ExternalLoadableUiController {
    @FXML
    private JFXDatePicker dpStart;
    @FXML
    private JFXDatePicker dpEnd;
    @FXML
    private JFXTextField totalOutput;
    @FXML
    private JFXTreeTableView<IdAmountTupleModel> stockTable;
    @FXML
    private JFXTreeTableColumn<IdAmountTupleModel, String> stockIdColumn;
    @FXML
    private JFXTreeTableColumn<IdAmountTupleModel, String> stockAmountColumn;
    @FXML
    private JFXTreeTableView<IdAmountTupleModel> productionTable;
    @FXML
    private JFXTreeTableColumn<IdAmountTupleModel, String> productionIdColumn;
    @FXML
    private JFXTreeTableColumn<IdAmountTupleModel, String> productionAmountColumn;
    @FXML
    private JFXTreeTableView<IdAmountTupleModel> halfProductionTable;
    @FXML
    private JFXTreeTableColumn<IdAmountTupleModel, String> halfProductionIdColumn;
    @FXML
    private JFXTreeTableColumn<IdAmountTupleModel, String> halfProductionAmountColumn;

    private ObservableList<IdAmountTupleModel> stockTableObservableList = FXCollections.observableArrayList();
    private ObservableList<IdAmountTupleModel> productionTableObservableList = FXCollections.observableArrayList();
    private ObservableList<IdAmountTupleModel> halfProductionTableObservableList = FXCollections.observableArrayList();

    private ProductionBillBlService productionBillBlService = ProductionBillBlServiceFactory.getProductionBillBlService();

    public void initialize() {
        stockIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getIdAmountTupleObjectProperty().getId()));
        stockAmountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getIdAmountTupleObjectProperty().getAmount() + ""));
        TreeItem<IdAmountTupleModel> stockRoot = new RecursiveTreeItem<>(stockTableObservableList, RecursiveTreeObject::getChildren);
        stockTable.setRoot(stockRoot);
        stockTable.setShowRoot(false);

        productionIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getIdAmountTupleObjectProperty().getId()));
        productionAmountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getIdAmountTupleObjectProperty().getAmount() + ""));
        TreeItem<IdAmountTupleModel> productionRoot = new RecursiveTreeItem<>(productionTableObservableList, RecursiveTreeObject::getChildren);
        productionTable.setRoot(productionRoot);
        productionTable.setShowRoot(false);

        halfProductionIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getIdAmountTupleObjectProperty().getId()));
        halfProductionAmountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getIdAmountTupleObjectProperty().getAmount() + ""));
        TreeItem<IdAmountTupleModel> halfProductionRoot = new RecursiveTreeItem<>(halfProductionTableObservableList, RecursiveTreeObject::getChildren);
        halfProductionTable.setRoot(halfProductionRoot);
        halfProductionTable.setShowRoot(false);
    }

    private void updateItems() {
        LocalDate startDate = dpStart.getValue();
        LocalDate endDate = dpEnd.getValue();
        if (startDate != null && endDate != null) {
            stockTableObservableList.clear();
            productionTableObservableList.clear();
            halfProductionTableObservableList.clear();
            List<ProductionBill> productionBills = productionBillBlService.getBillsBetween(FormatDateTime.fromLocalDate(startDate), FormatDateTime.fromLocalDate(endDate));
            Map<String, Double> stockMap = new HashMap<>();
            Map<String, Double> productionMap = new HashMap<>();
            Map<String, Double> halfProductionMap = new HashMap<>();
            for (ProductionBill productionBill : productionBills) {
                String billId = productionBill.getProductionId();
                if (isHalfProduction(billId)) {
                    if (halfProductionMap.containsKey(billId)) {
                        Double oldValue = halfProductionMap.get(billId);
                        halfProductionMap.replace(billId, oldValue + productionBill.getTotalQuantity());
                    } else {
                        halfProductionMap.put(billId, productionBill.getTotalQuantity());
                    }
                } else {
                    if (productionMap.containsKey(billId)) {
                        Double oldValue = productionMap.get(billId);
                        productionMap.replace(billId, oldValue + productionBill.getTotalQuantity());
                    } else {
                        productionMap.put(billId, productionBill.getTotalQuantity());
                    }
                }
                List<ProductionBillStockItem> productionBillStockItems = productionBill.getProductionBillStockItems();
                for (ProductionBillStockItem productionBillStockItem : productionBillStockItems) {
                    String stockId = productionBillStockItem.getStockCode();
                    if (stockMap.containsKey(stockId)) {
                        Double oldValue = stockMap.get(stockId);
                        stockMap.replace(stockId, oldValue + productionBillStockItem.getStockAmount());
                    } else {
                        stockMap.put(stockId, productionBillStockItem.getStockAmount());
                    }
                }
            }

            double total = 0;
            for (String key : stockMap.keySet()) {
                double amount = stockMap.get(key);
                IdAmountTupleModel idAmountTupleModel = new IdAmountTupleModel(new IdAmountTuple(key, amount));
                stockTableObservableList.add(idAmountTupleModel);
                total += amount;
            }
            for (String key : productionMap.keySet()) {
                double amount = productionMap.get(key);
                IdAmountTupleModel idAmountTupleModel = new IdAmountTupleModel(new IdAmountTuple(key, amount));
                productionTableObservableList.add(idAmountTupleModel);
                total += amount;
            }
            for (String key : halfProductionMap.keySet()) {
                double amount = halfProductionMap.get(key);
                IdAmountTupleModel idAmountTupleModel = new IdAmountTupleModel(new IdAmountTuple(key, amount));
                halfProductionTableObservableList.add(idAmountTupleModel);
                total += amount;
            }
            totalOutput.setText(total + "");
        }
    }

    private boolean isHalfProduction(String id) {
        return id.charAt(1) >= '0' && id.charAt(1) <= '9';
    }

    public void onExportClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择路径");
        fileChooser.setInitialFileName(String.format("昆山道普润滑科技有限公司%s生产领料明细表", FormatDateTime.currentDateString()));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                ExcelOutput.createExcel(file.getParent(), toExcel(), file.getName(), "//");
            } catch (ExcelCreateFailException e) {
                e.printStackTrace();
                PromptDialogHelper.start("导出失败！", "生产原始单导出失败。")
                        .addCloseButton("好", "CHECK", null)
                        .createAndShow();
            }
            PromptDialogHelper.start("导出成功！", String.format("生产领料明细表已经导出到%s。", file.getAbsolutePath()))
                    .addCloseButton("好", "CHECK", null)
                    .createAndShow();
        }
    }

    private String[] toExcel() {
        List<String> content = new ArrayList<>();
        content.add("原料编号//用量(单位:kg)");
        for (IdAmountTupleModel idAmountTupleModel : stockTableObservableList) {
            content.add(String.format("%s//%s\n",
                    idAmountTupleModel.getIdAmountTupleObjectProperty().getId(),
                    idAmountTupleModel.getIdAmountTupleObjectProperty().getAmount()
            ));
        }
        content.add("成品编号//用量(单位:kg)");
        for (IdAmountTupleModel idAmountTupleModel : productionTableObservableList) {
            content.add(String.format("%s//%s\n",
                    idAmountTupleModel.getIdAmountTupleObjectProperty().getId(),
                    idAmountTupleModel.getIdAmountTupleObjectProperty().getAmount()
            ));
        }
        content.add("半成品编号//用量(单位:kg)");
        for (IdAmountTupleModel idAmountTupleModel : halfProductionTableObservableList) {
            content.add(String.format("%s//%s\n",
                    idAmountTupleModel.getIdAmountTupleObjectProperty().getId(),
                    idAmountTupleModel.getIdAmountTupleObjectProperty().getAmount()
            ));
        }
        content.add("总量（原料+成品）//" + totalOutput.getText());
        return content.toArray(new String[content.size()]);
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/bomui/BomUi.fxml").loadAndGetPackageWithoutException();
    }

    @FXML
    private void onBtnClearFilterClicked() {
        dpStart.setValue(null);
        dpEnd.setValue(null);
        totalOutput.clear();
        stockTableObservableList.clear();
        productionTableObservableList.clear();
        halfProductionTableObservableList.clear();
    }

    @FXML
    private void onRefreshClicked(ActionEvent actionEvent) {
        updateItems();
    }

    @FXML
    private void onBtnCloseClicked(ActionEvent actionEvent) {
        FrameworkUiManager.switchBackToHome();
    }

    public void onBtnDeleteItemClicked(ActionEvent actionEvent) {
        int selectedIndex = stockTable.getSelectionModel().getSelectedIndex();
        stockTableObservableList.remove(selectedIndex);
        double total = 0;
        for (IdAmountTupleModel idAmountTupleModel : stockTableObservableList) {
            total += idAmountTupleModel.getIdAmountTupleObjectProperty().getAmount();
        }
        for (IdAmountTupleModel idAmountTupleModel : productionTableObservableList) {
            total += idAmountTupleModel.getIdAmountTupleObjectProperty().getAmount();
        }
        for (IdAmountTupleModel idAmountTupleModel : halfProductionTableObservableList) {
            total += idAmountTupleModel.getIdAmountTupleObjectProperty().getAmount();
        }
        totalOutput.setText(total + "");
    }
}
