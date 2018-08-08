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
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jxl.Workbook;
import jxl.write.*;
import suwu.daopuerp.bl.productionbill.factory.ProductionBillBlServiceFactory;
import suwu.daopuerp.blservice.productionbill.ProductionBillBlService;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;
import suwu.daopuerp.dto.productionbill.ProductionBillLiquidDto;
import suwu.daopuerp.dto.productionbill.ProductionBillOilDto;
import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.exception.ExcelCreateFailException;
import suwu.daopuerp.exception.IdDoesNotExistException;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.presentation.productionbillui.liquid.ProductionBillLiquidAddUiController;
import suwu.daopuerp.presentation.productionbillui.oil.ProductionBillOilAddUiController;
import suwu.daopuerp.publicdata.BillType;
import suwu.daopuerp.util.FormatDateTime;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private final static int MIN_ROW = 20;

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
        initSearch();

        billTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                onBtnSelectClicked(null);
            }
        });
    }

    private void initSearch() {
        tfSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                ProductionBillItem[] productionBillItems = productionBillBlService.query(tfSearch.getText());
                productionBillItemModelObservableList.clear();
                productionBillItemModelObservableList.addAll(Arrays.stream(productionBillItems).map(ProductionBillItemModel::new).collect(Collectors.toList()));
            }
        });
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
            PromptDialogHelper.start("修改单据信息", "")
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
            createExcel(billType, productionBillDto, file.getName(), file.getPath());
            PromptDialogHelper.start("导出成功！", String.format("生产原始单表已经导出到%s。", file.getAbsolutePath()))
                    .addCloseButton("好", "CHECK", null)
                    .createAndShow();
        }
    }

    private void createExcel(BillType billType, ProductionBillDto productionBillDto, String fileName, String path) {
        try {
            path = path + ".xls";
            WritableWorkbook book = Workbook.createWorkbook(new File(path));
            //页码
            WritableSheet sheet = book.createSheet(fileName, 0);


            WritableFont normalFont = new WritableFont(WritableFont.createFont("宋体"), 11, WritableFont.NO_BOLD);
            // 设置字体为宋体,11号字,不加粗,颜色为红色
            WritableCellFormat normalFormat = new WritableCellFormat(normalFont);
            normalFormat.setAlignment(jxl.format.Alignment.CENTRE);
            normalFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            normalFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            sheet.addCell(new Label(0, 0, "文件编号", normalFormat));
            sheet.addCell(new Label(1, 0, productionBillDto.getBillId(), normalFormat));
            sheet.addCell(new Label(2, 0, "生产日期", normalFormat));
            sheet.addCell(new Label(3, 0, FormatDateTime.toShortDateString(productionBillDto.getProductionDate()), normalFormat));
            sheet.addCell(new Label(4, 0, "品名", normalFormat));
            sheet.addCell(new Label(5, 0, productionBillDto.getProductionName(), normalFormat));

            sheet.addCell(new Label(0, 1, "开单日期", normalFormat));
            sheet.addCell(new Label(1, 1, productionBillDto.getBillDate(), normalFormat));
            sheet.addCell(new Label(2, 1, "客户", normalFormat));
            sheet.addCell(new Label(3, 1, productionBillDto.getClient(), normalFormat));
            sheet.addCell(new Label(4, 1, "型号", normalFormat));
            sheet.addCell(new Label(5, 1, productionBillDto.getProductionType(), normalFormat));

            sheet.addCell(new Label(0, 2, "设备编号", normalFormat));
            sheet.addCell(new Label(1, 2, productionBillDto.getMachineId(), normalFormat));
            sheet.addCell(new Label(2, 2, "", normalFormat));
            sheet.mergeCells(1, 2, 2, 2);
            sheet.addCell(new Label(3, 2, "编号", normalFormat));
            sheet.addCell(new Label(4, 2, productionBillDto.getProductionId(), normalFormat));
            sheet.addCell(new Label(5, 2, "", normalFormat));
            sheet.mergeCells(4, 2, 5, 2);

            sheet.addCell(new Label(0, 3, "序号", normalFormat));
            sheet.addCell(new Label(1, 3, "原料代码", normalFormat));
            sheet.addCell(new Label(2, 3, "加入量KG", normalFormat));
            sheet.addCell(new Label(3, 3, "实际加入量", normalFormat));
            sheet.addCell(new Label(4, 3, "生产工艺", normalFormat));
            sheet.addCell(new Label(5, 3, "调整记录", normalFormat));

            int i = 0;
            for (i = 0; i < productionBillDto.getProductionBillStockItems().size(); i++) {
                ProductionBillStockItem productionBillStockItem = productionBillDto.getProductionBillStockItems().get(i);
                sheet.addCell(new Label(0, 4 + i, i + "", normalFormat));
                sheet.addCell(new Label(1, 4 + i, productionBillStockItem.getStockCode(), normalFormat));
                sheet.addCell(new Label(2, 4 + i, productionBillStockItem.getStockAmount() + "", normalFormat));
                sheet.addCell(new Label(3, 4 + i, "", normalFormat));
                sheet.addCell(new Label(4, 4 + i, productionBillStockItem.getStockProcess(), normalFormat));
                sheet.addCell(new Label(5, 4 + i, "", normalFormat));
            }
            for (int j = i; j < MIN_ROW + 1; j++) {
                sheet.addCell(new Label(0, 4 + j, j + "", normalFormat));
                sheet.addCell(new Label(1, 4 + j, "", normalFormat));
                sheet.addCell(new Label(2, 4 + j, "", normalFormat));
                sheet.addCell(new Label(3, 4 + j, "", normalFormat));
                sheet.addCell(new Label(4, 4 + j, "", normalFormat));
                sheet.addCell(new Label(5, 4 + j, "", normalFormat));
            }
            sheet.addCell(new Label(5, 5, productionBillDto.getModifyRecord(), normalFormat));
            sheet.mergeCells(5, 4, 5, (4 + MIN_ROW) / 2);
            sheet.addCell(new Label(5, (4 + MIN_ROW) / 2 + 1, "备注", normalFormat));
            sheet.addCell(new Label(5, (4 + MIN_ROW) / 2 + 2, productionBillDto.getComment(), normalFormat));
            sheet.mergeCells(5, (4 + MIN_ROW) / 2 + 2, 5, 4 + MIN_ROW);
            sheet.addCell(new Label(0, 4 + MIN_ROW + 1, "合计", normalFormat));
            sheet.addCell(new Label(1, 4 + MIN_ROW + 1, productionBillDto.getTotalQuantity() + "", normalFormat));
            sheet.addCell(new Label(2, 4 + MIN_ROW + 1, "实际入库数量", normalFormat));
            sheet.addCell(new Label(3, 4 + MIN_ROW + 1, " ", normalFormat));
            sheet.addCell(new Label(4, 4 + MIN_ROW + 1, "损耗率", normalFormat));
            sheet.addCell(new Label(5, 4 + MIN_ROW + 1, " ", normalFormat));

            int handWriteStartRow = 4 + MIN_ROW + 2;
            switch (billType) {
                case OIL:
                    ProductionBillOilDto productionBillOilDto = (ProductionBillOilDto) productionBillDto;
                    sheet.addCell(new Label(0, handWriteStartRow, "质量指标", normalFormat));
                    sheet.mergeCells(0, handWriteStartRow, 0, handWriteStartRow + 2);
                    sheet.addCell(new Label(1, handWriteStartRow, "外观", normalFormat));
                    sheet.addCell(new Label(1, handWriteStartRow + 1, "开口闪点", normalFormat));
                    sheet.addCell(new Label(1, handWriteStartRow + 2, "粘度40℃", normalFormat));
                    sheet.addCell(new Label(2, handWriteStartRow, productionBillOilDto.getOutLooking(), normalFormat));
                    sheet.addCell(new Label(2, handWriteStartRow + 1, productionBillOilDto.getFlashPoint(), normalFormat));
                    sheet.addCell(new Label(2, handWriteStartRow + 2, productionBillOilDto.getViscosity(), normalFormat));
                    break;
                case LIQUID:
                    ProductionBillLiquidDto productionBillLiquidDto = (ProductionBillLiquidDto) productionBillDto;
                    sheet.addCell(new Label(0, handWriteStartRow, "质量指标", normalFormat));
                    sheet.mergeCells(0, handWriteStartRow, 0, handWriteStartRow + 2);
                    sheet.addCell(new Label(1, handWriteStartRow, "原液外观", normalFormat));
                    sheet.addCell(new Label(1, handWriteStartRow + 1, "PH值", normalFormat));
                    sheet.addCell(new Label(1, handWriteStartRow + 2, "折光系数", normalFormat));
                    sheet.addCell(new Label(2, handWriteStartRow, productionBillLiquidDto.getLiquidLooking(), normalFormat));
                    sheet.addCell(new Label(2, handWriteStartRow + 1, productionBillLiquidDto.getPhValue(), normalFormat));
                    sheet.addCell(new Label(2, handWriteStartRow + 2, productionBillLiquidDto.getLightValue(), normalFormat));
                    break;
            }
            sheet.addCell(new Label(3, handWriteStartRow, "结论", normalFormat));
            sheet.addCell(new Label(3, handWriteStartRow + 1, "", normalFormat));
            sheet.addCell(new Label(3, handWriteStartRow + 2, "", normalFormat));
            sheet.addCell(new Label(4, handWriteStartRow, "主管批示", normalFormat));
            sheet.addCell(new Label(4, handWriteStartRow + 1, "生产责任人", normalFormat));
            sheet.addCell(new Label(4, handWriteStartRow + 2, "", normalFormat));
            sheet.mergeCells(4, handWriteStartRow + 1, 4, handWriteStartRow + 2);
            sheet.addCell(new Label(5, handWriteStartRow, "", normalFormat));
            sheet.addCell(new Label(5, handWriteStartRow + 1, "", normalFormat));
            sheet.addCell(new Label(5, handWriteStartRow + 2, "", normalFormat));
            sheet.addCell(new Label(0, handWriteStartRow + 3, "中控项目", normalFormat));
            sheet.mergeCells(0, handWriteStartRow + 3, 0, handWriteStartRow + 6);
            sheet.addCell(new Label(1, handWriteStartRow + 3, "外观", normalFormat));
            sheet.mergeCells(1, handWriteStartRow + 3, 2, handWriteStartRow + 3);
            sheet.addCell(new Label(1, handWriteStartRow + 4, "原液安定性", normalFormat));
            sheet.mergeCells(1, handWriteStartRow + 4, 1, handWriteStartRow + 5);
            sheet.addCell(new Label(2, handWriteStartRow + 4, productionBillDto.getStableAttr1(), normalFormat));
            sheet.addCell(new Label(2, handWriteStartRow + 5, productionBillDto.getStableAttr2(), normalFormat));
            sheet.addCell(new Label(1, handWriteStartRow + 6, "防锈性(IP287)", normalFormat));
            sheet.mergeCells(1, handWriteStartRow + 6, 2, handWriteStartRow + 6);
            sheet.addCell(new Label(3, handWriteStartRow + 3, "", normalFormat));
            sheet.addCell(new Label(3, handWriteStartRow + 4, "", normalFormat));
            sheet.addCell(new Label(3, handWriteStartRow + 5, "", normalFormat));
            sheet.addCell(new Label(3, handWriteStartRow + 6, "", normalFormat));
            sheet.addCell(new Label(4, handWriteStartRow + 3, "运动粘度", normalFormat));
            sheet.addCell(new Label(4, handWriteStartRow + 4, "开口闪点", normalFormat));
            sheet.addCell(new Label(4, handWriteStartRow + 5, "钢片腐蚀", normalFormat));
            sheet.addCell(new Label(4, handWriteStartRow + 6, "密度", normalFormat));
            sheet.addCell(new Label(5, handWriteStartRow + 3, "检测人", normalFormat));
            sheet.addCell(new Label(5, handWriteStartRow + 4, "", normalFormat));
            sheet.addCell(new Label(5, handWriteStartRow + 5, "填写", normalFormat));
            sheet.addCell(new Label(5, handWriteStartRow + 6, "admin", normalFormat));
            sheet.addCell(new Label(0, handWriteStartRow + 7, "核对", normalFormat));
            sheet.addCell(new Label(1, handWriteStartRow + 7, "", normalFormat));
            sheet.addCell(new Label(2, handWriteStartRow + 7, "审核", normalFormat));
            sheet.addCell(new Label(3, handWriteStartRow + 7, "", normalFormat));
            sheet.addCell(new Label(4, handWriteStartRow + 7, "归档", normalFormat));
            sheet.addCell(new Label(5, handWriteStartRow + 7, "", normalFormat));
            book.write();
            book.close();
        } catch (WriteException | IOException e) {
            e.printStackTrace();
            PromptDialogHelper.start("导出失败！", "生产原始单导出失败。")
                    .addCloseButton("好", "CHECK", null)
                    .createAndShow();
        }
    }

    @FXML
    private void onBtnCloseClicked(ActionEvent actionEvent) {
        FrameworkUiManager.switchBackToHome();
    }

}
