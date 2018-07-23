package suwu.daopuerp.presentation.formulaui;

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
import suwu.daopuerp.exception.IdDoesNotExistException;
import suwu.daopuerp.presentation.formulaui.liquid.FormulaLiquidAddUiController;
import suwu.daopuerp.presentation.formulaui.oil.FormulaOilAddUiController;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.publicdata.BillType;

import java.util.List;

public class FormulaUiController implements ExternalLoadableUiController {
    @FXML
    private JFXTextField tfSearch;
    @FXML
    private JFXTreeTableView<FormulaItemModel> formulaTable;
    @FXML
    private JFXTreeTableColumn<FormulaItemModel, String> formulaIdColumn;
    @FXML
    private JFXTreeTableColumn<FormulaItemModel, String> formulaCodeColumn;
    @FXML
    private JFXTreeTableColumn<FormulaItemModel, String> formulaNameColumn;
    @FXML
    private JFXTreeTableColumn<FormulaItemModel, String> formulaTypeColumn;

    private ObservableList<FormulaItemModel> formulaItemModelObservableList = FXCollections.observableArrayList();
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
        formulaCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getFormulaItemObjectProperty().getFormulaCode()));
        formulaNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getFormulaItemObjectProperty().getFormulaName()));
        formulaTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getFormulaItemObjectProperty().getFormulaType()));
        TreeItem<FormulaItemModel> root = new RecursiveTreeItem<>(formulaItemModelObservableList, RecursiveTreeObject::getChildren);
        formulaTable.setRoot(root);
        formulaTable.setShowRoot(false);

        tfSearch.textProperty().bindBidirectional(tfSearchProperty);

        initFormulas();
    }

    private void initFormulas() {
        List<FormulaItem> formulaItems = formulaBlService.getAllFormulas();
        for (FormulaItem formulaItem : formulaItems) {
            formulaItemModelObservableList.add(new FormulaItemModel(formulaItem));
        }
    }

    @FXML
    private void onBtnAddClicked(ActionEvent actionEvent) {
        PromptDialogHelper.start("选择生产原始单类型", null)
                .addCloseButton(BillType.LIQUID.getName(), "ATTACHMENT", e -> FrameworkUiManager.switchFunction(FormulaLiquidAddUiController.class, "增加配方单（液）", true))
                .addCloseButton(BillType.OIL.getName(), "ASSESSMENT", e -> FrameworkUiManager.switchFunction(FormulaOilAddUiController.class, "增加配方单（油）", true))
                .addCloseButton("取消", "UNDO", null)
                .createAndShow();
    }

    @FXML
    private void onBtnModifyClicked(ActionEvent actionEvent) {
        FormulaItemModel model = formulaTable.getSelectionModel().getSelectedItem().getValue();
        if (model != null) {
            FormulaItem selected = model.getFormulaItemObjectProperty();
            FormulaDto formulaDto = null;
            try {
                formulaDto = formulaBlService.getFormulaById(selected.getFormulaId());
            } catch (IdDoesNotExistException e) {
                e.printStackTrace();
                PromptDialogHelper.start("错误", "找不到该配方ID。")
                        .addCloseButton("好的", "DONE", null)
                        .createAndShow();
            }
            assert formulaDto != null;
            PromptDialogHelper.start("修改配方信息", "")
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
        int selectedIndex = formulaTable.getSelectionModel().getSelectedIndex();
        String formulaId = formulaItemModelObservableList.get(selectedIndex).getFormulaItemObjectProperty().getFormulaId();
        try {
            formulaBlService.deleteFormula(formulaId);
            formulaItemModelObservableList.remove(selectedIndex);
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
        FormulaItemModel model = formulaTable.getSelectionModel().getSelectedItem().getValue();
        if (model != null) {
            FormulaItem selected = model.getFormulaItemObjectProperty();
            FormulaDto formulaDto = null;
            try {
                formulaDto = formulaBlService.getFormulaById(selected.getFormulaId());
            } catch (IdDoesNotExistException e) {
                e.printStackTrace();
                PromptDialogHelper.start("错误", "找不到该配方ID。")
                        .addCloseButton("好的", "DONE", null)
                        .createAndShow();
            }
            PromptDialogHelper.start("配方单详细信息", "")
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
