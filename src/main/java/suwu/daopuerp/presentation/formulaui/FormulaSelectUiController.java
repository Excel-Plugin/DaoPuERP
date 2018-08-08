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
import javafx.scene.input.KeyCode;
import suwu.daopuerp.bl.formula.factory.FormulaBlServiceFactory;
import suwu.daopuerp.bl.productionbill.factory.ProductionBillBlServiceFactory;
import suwu.daopuerp.blservice.formula.FormulaBlService;
import suwu.daopuerp.blservice.productionbill.ProductionBillBlService;
import suwu.daopuerp.dto.formula.FormulaAndAmountDto;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.formula.FormulaItem;
import suwu.daopuerp.exception.IdDoesNotExistException;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.publicdata.BillType;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FormulaSelectUiController extends SelectingDialog implements FormulaSelectUi, ExternalLoadableUiController {
    @FXML
    private JFXTextField tfQuantity;
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
    private StringProperty tfQuantityProperty = new SimpleStringProperty("");

    private FormulaBlService formulaBlService = FormulaBlServiceFactory.getFormulaBlService();
    private ProductionBillBlService productionBillBlService = ProductionBillBlServiceFactory.getProductionBillBlService();

    public Consumer<FormulaAndAmountDto> callback;
    public BillType billType;

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/formulaui/FormulaSelectUi.fxml").loadAndGetPackageWithoutException();
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
        tfQuantity.textProperty().bindBidirectional(tfQuantityProperty);

        initFormulas();
        initSearch();
    }

    private void initFormulas() {
        List<FormulaItem> formulaItems = formulaBlService.getAllFormulas();
        for (FormulaItem formulaItem : formulaItems) {
            formulaItemModelObservableList.add(new FormulaItemModel(formulaItem));
        }
    }

    private void initSearch() {
        tfSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                FormulaItem[] formulaItems = formulaBlService.query(tfSearch.getText());
                formulaItemModelObservableList.clear();
                formulaItemModelObservableList.addAll(Arrays.stream(formulaItems).map(FormulaItemModel::new).collect(Collectors.toList()));
            }
        });
    }

    /**
     * 获得当前已经选择的日志。如果没有选择的，那么这个是空List（不是null！！！）
     *
     * @return 当前已经选择的项
     */
    private FormulaAndAmountDto getSelected() {
        FormulaItemModel model = formulaTable.getSelectionModel().getSelectedItem().getValue();
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
        PromptDialogHelper.start("配方单列表", "")
                .setContent(formulaDto.detailUi().showContent(formulaDto).getComponent())
                .addCloseButton("好的", "CHECK", null)
                .createAndShow();
        return new FormulaAndAmountDto(Double.parseDouble(tfQuantityProperty.get()), formulaDto);
    }


    private FormulaAndAmountDto getTest() {
        FormulaDto formulaDto = productionBillBlService.getNextTestBill(billType);
        return new FormulaAndAmountDto(Double.parseDouble(tfQuantityProperty.get()), formulaDto);
    }

    @FXML
    private void onBtnSelectClicked(ActionEvent actionEvent) {
        onClose(); //一定要调用这个来把弹出框关了。
        if (callback != null) {
            callback.accept(getSelected()); //选择结束，调用回调方法。
        }
    }

    @FXML
    private void onBtnCloseClicked(ActionEvent actionEvent) {
        onClose();
    }

    /**
     * show the select formula dialog
     *
     * @param callback call back function
     * @param billType
     */
    @Override
    public void showFormulaSelectDialog(Consumer<FormulaAndAmountDto> callback, BillType billType) {
        ExternalLoadedUiPackage uiPackage = load();
        FormulaSelectUiController controller = uiPackage.getController();
        controller.callback = callback;
        controller.billType = billType;
        PromptDialogHelper.start("", "").setContent(uiPackage.getComponent()).createAndShow();
    }

    /**
     * query the whole formulaDto by id
     *
     * @param id
     * @return the whole formulaDto
     */
    @Override
    public FormulaDto queryById(String id) throws IdDoesNotExistException {
        return formulaBlService.getFormulaById(id);
    }

    public void onBtnTestClicked(ActionEvent actionEvent) {
        onClose(); //一定要调用这个来把弹出框关了。
        if (callback != null) {
            callback.accept(getTest()); //选择结束，调用回调方法。
        }
    }
}
