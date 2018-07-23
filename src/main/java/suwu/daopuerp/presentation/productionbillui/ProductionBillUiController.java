package suwu.daopuerp.presentation.productionbillui;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import suwu.daopuerp.bl.productionbill.factory.ProductionBillBlServiceFactory;
import suwu.daopuerp.blservice.productionbill.ProductionBillBlService;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;
import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.exception.ExcelCreateFailException;
import suwu.daopuerp.exception.IdDoesNotExistException;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.presentation.productionbillui.liquid.ProductionBillLiquidAddUiController;
import suwu.daopuerp.presentation.productionbillui.oil.ProductionBillOilAddUiController;
import suwu.daopuerp.publicdata.BillType;
import suwu.daopuerp.util.ExcelOutput;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductionBillUiController implements ExternalLoadableUiController {
    @FXML
    private JFXTextField tfSearch;
    @FXML
    private JFXTreeTableView<ProductionBillItemModel> billTable;
    @FXML
    private JFXTreeTableColumn<ProductionBillItemModel, String> billTypeColumn;
    @FXML
    private JFXTreeTableColumn<ProductionBillItemModel, String> billIdColumn;
    @FXML
    private JFXTreeTableColumn<ProductionBillItemModel, String> productionNameColumn;
    @FXML
    private JFXTreeTableColumn<ProductionBillItemModel, String> billDateColumn;
    @FXML
    private JFXTreeTableColumn<ProductionBillItemModel, String> productionTypeColumn;
    @FXML
    private JFXTreeTableColumn<ProductionBillItemModel, String> productionIdColumn;

    private ObservableList<ProductionBillItemModel> productionBillItemModelObservableList = FXCollections.observableArrayList();
    private StringProperty tfSearchProperty = new SimpleStringProperty("");

    private ProductionBillBlService productionBillBlService = ProductionBillBlServiceFactory.getProductionBillBlService();

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/productionbillui/ProductionBillUi.fxml").loadAndGetPackageWithoutException();
    }

    public void initialize() {
        billTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillItemObjectProperty().getBillType().getName()));
        billIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillItemObjectProperty().getBillId()));
        productionNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillItemObjectProperty().getProductionName()));
        billDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillItemObjectProperty().getBillDate()));
        productionTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillItemObjectProperty().getProductionType()));
        productionIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getProductionBillItemObjectProperty().getProductionId()));
        TreeItem<ProductionBillItemModel> root = new RecursiveTreeItem<>(productionBillItemModelObservableList, RecursiveTreeObject::getChildren);
        billTable.setRoot(root);
        billTable.setShowRoot(false);

        tfSearch.textProperty().bindBidirectional(tfSearchProperty);

        initProductionBills();
    }

    private void initProductionBills() {
        List<ProductionBillItem> productionBillItems = productionBillBlService.getAllProductionBillItems();
        for (ProductionBillItem productionBillItem : productionBillItems) {
            productionBillItemModelObservableList.add(new ProductionBillItemModel(productionBillItem));
        }
    }

    @FXML
    private void onBtnAddClicked(ActionEvent actionEvent) {
        PromptDialogHelper.start("选择生产原始单类型", null)
                .addCloseButton(BillType.LIQUID.getName(), "ATTACHMENT", e -> FrameworkUiManager.switchFunction(ProductionBillLiquidAddUiController.class, "增加生产原始单（液）", true))
                .addCloseButton(BillType.OIL.getName(), "ASSESSMENT", e -> FrameworkUiManager.switchFunction(ProductionBillOilAddUiController.class, "增加生产原始单（油）", true))
                .addCloseButton("取消", "UNDO", null)
                .createAndShow();
    }

    @FXML
    private void onBtnModifyClicked(ActionEvent actionEvent) {
        ProductionBillItemModel model = billTable.getSelectionModel().getSelectedItem().getValue();
        if (model != null) {
            ProductionBillItem selected = model.getProductionBillItemObjectProperty();
            ProductionBillDto productionBillDto = null;
            try {
                productionBillDto = productionBillBlService.getProductionBillDtoById(selected.getBillId());
            } catch (IdDoesNotExistException e) {
                e.printStackTrace();
                PromptDialogHelper.start("错误", "找不到该单据ID。")
                        .addCloseButton("好的", "DONE", null)
                        .createAndShow();
            }
            assert productionBillDto != null;
            PromptDialogHelper.start("修改客户信息", "")
                    .setContent(productionBillDto.modifyUi().showContent(productionBillDto).getComponent())
                    .createAndShow();
        } else {
            PromptDialogHelper.start("错误", "请至少选一个条目。")
                    .addCloseButton("好的", "DONE", null)
                    .createAndShow();
        }
    }

    @FXML
    private void onBtnDeleteClicked(ActionEvent actionEvent) {
        PromptDialogHelper.start("是否要删除", null)
                .addCloseButton("确定", "DONE", e -> delete())
                .addCloseButton("取消", "UNDO", null)
                .createAndShow();
    }

    private void delete() {
        int selectedIndex = billTable.getSelectionModel().getSelectedIndex();
        String formulaId = productionBillItemModelObservableList.get(selectedIndex).getProductionBillItemObjectProperty().getBillId();
        try {
            productionBillBlService.delete(formulaId);
            productionBillItemModelObservableList.remove(selectedIndex);
        } catch (IdDoesNotExistException e) {
            e.printStackTrace();
            PromptDialogHelper.start("删除失败，请重试", null)
                    .addCloseButton("确定", "DONE", null)
                    .createAndShow();
        }
    }

    private void confirmDelete() {
//        List<String> idList = new ArrayList<>();
//        ObservableList<TreeItem<ClientSelectionItemModel>> clientSelectionTreeItemModels = clientTable.getSelectionModel().getSelectedItems();
//        for (TreeItem<ClientSelectionItemModel> treeItem : clientSelectionTreeItemModels) {
//            idList.add(treeItem.getValue().getClientVoObjectProperty().getId());
//        }
//        ResultMessage resultMessage = blService.delete(idList.toArray(new String[idList.size()]));
//        if (resultMessage == ResultMessage.Success) {
//            ObservableList<Integer> commodityIndexList = clientTable.getSelectionModel().getSelectedIndices();
//            for (int index : commodityIndexList) {
//                clientSelectionItemModels.remove(index);
//            }
//            PromptDialogHelper.start("删除成功", null)
//                    .addCloseButton("确定", "DONE", null)
//                    .createAndShow();
//        } else {
//            PromptDialogHelper.start("删除失败，请重试", null)
//                    .addCloseButton("确定", "DONE", null)
//                    .createAndShow();
//        }
    }

    @FXML
    private void onBtnSelectClicked(ActionEvent actionEvent) {
        ProductionBillItemModel model = billTable.getSelectionModel().getSelectedItem().getValue();
        if (model != null) {
            ProductionBillItem selected = model.getProductionBillItemObjectProperty();
            ProductionBillDto productionBillDto = null;
            try {
                productionBillDto = productionBillBlService.getProductionBillDtoById(selected.getBillId());
            } catch (IdDoesNotExistException e) {
                e.printStackTrace();
                PromptDialogHelper.start("错误", "找不到该单据ID。")
                        .addCloseButton("好的", "DONE", null)
                        .createAndShow();
            }
            ProductionBillDto finalProductionBillDto = productionBillDto;
            PromptDialogHelper.start("单据详细信息", "")
                    .setContent(productionBillDto.detailUi().showContent(productionBillDto).getComponent())
                    .addCloseButton("导出", "SHARE", e -> {
                        try {
                            assert finalProductionBillDto != null;
                            exportExcel(finalProductionBillDto.getBillType(), finalProductionBillDto);
                        } catch (ExcelCreateFailException e1) {
                            e1.printStackTrace();
                            PromptDialogHelper.start("导出失败！", "生产原始单导出失败。")
                                    .addCloseButton("好", "CHECK", null)
                                    .createAndShow();
                        }
                    })
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        } else {
            PromptDialogHelper.start("错误", "请至少选一个条目。")
                    .addCloseButton("好的", "DONE", null)
                    .createAndShow();
        }
    }

    private void exportExcel(BillType billType, ProductionBillDto productionBillDto) throws ExcelCreateFailException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择路径");
        fileChooser.setInitialFileName(String.format("生产原始单表%s", productionBillDto.getBillId()));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            ExcelOutput.createExcel(file.getParent(), toExcel(billType, productionBillDto), file.getName(), "//");
            PromptDialogHelper.start("导出成功！", String.format("生产原始单表已经导出到%s。", file.getAbsolutePath()))
                    .addCloseButton("好", "CHECK", null)
                    .createAndShow();
        }
    }

    private String[] toExcel(BillType billType, ProductionBillDto productionBillDto) {
        List<String> content = new ArrayList<>();
        content.add("文件编号//" + productionBillDto.getBillId() + "//生产日期//" + productionBillDto.getProductionDate() + "//品名//" + productionBillDto.getProductionName() + "\n");
        content.add("开单日期//" + productionBillDto.getBillDate() + "//客户//" + productionBillDto.getClient() + "//型号//" + productionBillDto.getBillType() + "\n");
        content.add("设备编号//" + productionBillDto.getMachineId() + "//编号//" + productionBillDto.getProductionId() + "\n");
        content.add("序号//原料代码//加入量KG//实际加入量//生产工艺\n");
        for (int i = 0; i < productionBillDto.getProductionBillStockItems().size(); i++) {
            ProductionBillStockItem productionBillStockItem = productionBillDto.getProductionBillStockItems().get(i);
            content.add(String.format("%s//%s//%s//%s//%s\n",
                    i, productionBillStockItem.getStockId(), productionBillStockItem.getStockAmount(), "", productionBillStockItem.getStockProcess()
            ));
        }
        content.add("合计//" + productionBillDto.getTotalQuantity() + "//实际入库数量//" + " //损耗率// \n");
        content.add("调整记录//" + productionBillDto.getModifyRecord() + "//备注//" + productionBillDto.getComment() + "\n");
        switch (billType) {
            case OIL:
                break;
            case LIQUID:
                break;
        }
    }

    @FXML
    private void onBtnCloseClicked(ActionEvent actionEvent) {
        FrameworkUiManager.switchBackToHome();
    }

}
