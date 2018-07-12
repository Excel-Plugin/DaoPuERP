package trapx00.lightx00.client.presentation.inventoryui.check;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryCheckBlService;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryCheckBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadableUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.PromptDialogHelper;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.inventorystaff.InventoryViewVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.time.LocalDate;
import java.util.Date;

public class InventoryCheckUiController implements ExternalLoadableUiController {
    @FXML private JFXDatePicker startDatePicker;
    @FXML private JFXDatePicker endDatePicker;
    @FXML private JFXButton btnFilter;
    @FXML private JFXTreeTableView<InventoryViewModel> tableView;
    @FXML private JFXTreeTableColumn<InventoryViewModel, Double> tcinventoryAmount;
    @FXML private JFXTreeTableColumn<InventoryViewModel, Double> tcinventoryMoney;
    @FXML private JFXTreeTableColumn<InventoryViewModel, Double> tcoutOfInventoryAmount;
    @FXML private JFXTreeTableColumn<InventoryViewModel, Double> tcoutOfInVentoryMoney;
    @FXML private JFXTreeTableColumn<InventoryViewModel, Double> tcsum;
    @FXML private JFXTreeTableColumn<InventoryViewModel, Double> tcinSold;
    @FXML private JFXTreeTableColumn<InventoryViewModel, Double> tcoutSold;
    @FXML private JFXTreeTableColumn<InventoryViewModel,Double> tcsoldNum;//销售数量
    @FXML private JFXTreeTableColumn<InventoryViewModel,Double> tcinvennum;//进货数量

    private ObservableList<InventoryViewModel> viewModels = FXCollections.observableArrayList();
    private InventoryCheckBlService blService= InventoryCheckBlServiceFactory.getInstance();

    public void onBtnFilterClicked(ActionEvent actionEvent) {
        updateItems();
    }

    public void initialize() {
        initTable();
    }
    public void updateItems() {

        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        if (start == null || end == null) {
            PromptDialogHelper.start("请选择日期","请选择日期！")
                    .addCloseButton("好","CHECK", null)
                    .createAndShow();
            return;
        }
        InventoryViewVo inventoryViewVo=null;
        Date beginTime=DateHelper.fromLocalDate(start);
        Date endTime=DateHelper.fromLocalDate(end);
            try {
                inventoryViewVo=blService.getInventoryView(
                        beginTime,
                        endTime);
                viewModels.clear();
                viewModels.add(new InventoryViewModel(inventoryViewVo.getItems()));
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }



    }

    @FXML
    private void initTable() {
        tcinventoryAmount.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getInventorViewItem().getInventoryAmount()));
        tcinventoryMoney.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getInventorViewItem().getInventoryMoney()));
        tcoutOfInventoryAmount.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getInventorViewItem().getOutOfInventoryAmount()));
        tcoutOfInVentoryMoney.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getInventorViewItem().getOutOfInVentoryMoney()));
        tcsum.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getInventorViewItem().getSum()));
        tcinSold.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getInventorViewItem().getInSoldPrice()));
        tcoutSold.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getInventorViewItem().getOutSoldPrice()));

        tcinvennum.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getInventorViewItem().getInsellnum()));
        tcsoldNum.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getInventorViewItem().getSolerNum()));
        TreeItem<InventoryViewModel> root = new RecursiveTreeItem<>(viewModels, RecursiveTreeObject::getChildren);
        tableView.setRoot(root);
        tableView.setShowRoot(false);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/check/InventoryCheckUi.fxml").loadAndGetPackageWithoutException();
    }
}
