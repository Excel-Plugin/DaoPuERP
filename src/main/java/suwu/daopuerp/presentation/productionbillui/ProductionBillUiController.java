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
import suwu.daopuerp.bl.productionbill.factory.ProductionBillBlServiceFactory;
import suwu.daopuerp.blservice.productionbill.ProductionBillBlService;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;
import suwu.daopuerp.exception.IdDoesNotExistException;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.presentation.productionbillui.liquid.ProductionBillLiquidAddUiController;
import suwu.daopuerp.presentation.productionbillui.oil.ProductionBillOilAddUiController;
import suwu.daopuerp.publicdata.BillType;

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
            PromptDialogHelper.start("客户详细信息", "")
                    .setContent(productionBillDto.detailUi().showContent(productionBillDto).getComponent())
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        } else {
            PromptDialogHelper.start("错误", "请至少选一个条目。")
                    .addCloseButton("好的", "DONE", null)
                    .createAndShow();
        }
    }

    @FXML
    private void onBtnCloseClicked(ActionEvent actionEvent) {
        FrameworkUiManager.switchBackToHome();
    }

}
