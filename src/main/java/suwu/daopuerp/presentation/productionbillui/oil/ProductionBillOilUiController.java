package suwu.daopuerp.presentation.productionbillui.oil;

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
import suwu.daopuerp.bl.formula.factory.FormulaBlServiceFactory;
import suwu.daopuerp.blservice.formula.FormulaBlService;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.formula.FormulaItem;
import suwu.daopuerp.presentation.helpui.*;

import java.util.List;

public class ProductionBillOilUiController implements ExternalLoadableUiController {
    @FXML
    private JFXTextField tfSearch;
    @FXML
    private JFXTreeTableView<ProductionBillOilItemModel> formulaTable;
    @FXML
    private JFXTreeTableColumn<ProductionBillOilItemModel, String> formulaIdColumn;
    @FXML
    private JFXTreeTableColumn<ProductionBillOilItemModel, String> formulaNameColumn;
    @FXML
    private JFXTreeTableColumn<ProductionBillOilItemModel, String> formulaTypeColumn;

    private ObservableList<ProductionBillOilItemModel> productionBillOilItemModelObservableList = FXCollections.observableArrayList();
    private StringProperty tfSearchProperty = new SimpleStringProperty("");

    private FormulaBlService formulaBlService = FormulaBlServiceFactory.getFormulaBlService();

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/formulaui/FormulaUi.fxml").loadAndGetPackageWithoutException();
    }

    public void initialize() {
        formulaIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getFormulaItemObjectProperty().getFormulaId()));
        formulaNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getFormulaItemObjectProperty().getFormulaName()));
        formulaTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getFormulaItemObjectProperty().getFormulaType()));
        TreeItem<ProductionBillOilItemModel> root = new RecursiveTreeItem<>(productionBillOilItemModelObservableList, RecursiveTreeObject::getChildren);
        formulaTable.setRoot(root);
        formulaTable.setShowRoot(false);

        tfSearch.textProperty().bindBidirectional(tfSearchProperty);

        initFormulas();
    }

    private void initFormulas() {
        List<FormulaItem> formulaItems = formulaBlService.getAllFormulas();
        for (FormulaItem formulaItem : formulaItems) {
            productionBillOilItemModelObservableList.add(new ProductionBillOilItemModel(formulaItem));
        }
    }

    @FXML
    private void onBtnAddClicked(ActionEvent actionEvent) {
        FrameworkUiManager.switchFunction(ProductionBillOilAddUiController.class, "增加配方单", true);
    }

    @FXML
    private void onBtnModifyClicked(ActionEvent actionEvent) {
        ProductionBillOilItemModel model = formulaTable.getSelectionModel().getSelectedItem().getValue();
        if (model != null) {
            FormulaItem selected = model.getFormulaItemObjectProperty();
            FormulaDto formulaDto = formulaBlService.getFormulaById(selected.getFormulaId());
            PromptDialogHelper.start("修改客户信息", "")
                    .setContent(formulaDto.modifyUi().showContent(formulaDto).getComponent())
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
//        List<String> idList = new ArrayList<>();
//        ObservableList<TreeItem<ClientSelectionItemModel>> clientSelectionTreeItemModels = clientTable.getSelectionModel().getSelectedItems();
//        for (TreeItem<ClientSelectionItemModel> treeItem : clientSelectionTreeItemModels) {
//            idList.add(treeItem.getValue().getClientVoObjectProperty().getId());
//        }
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
        ProductionBillOilItemModel model = formulaTable.getSelectionModel().getSelectedItem().getValue();
        if (model != null) {
            FormulaItem selected = model.getFormulaItemObjectProperty();
            FormulaDto formulaDto = formulaBlService.getFormulaById(selected.getFormulaId());
            PromptDialogHelper.start("客户详细信息", "")
                    .setContent(formulaDto.detailUi().showContent(formulaDto).getComponent())
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
