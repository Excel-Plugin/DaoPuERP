package trapx00.lightx00.client.presentation.inventoryui.check;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryCheckBlService;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryCheckBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadableUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.PromptDialogHelper;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.inventorystaff.InventoryPictureItem;
import trapx00.lightx00.shared.util.DateHelper;

import java.io.File;

public class InventoryPictureUiController implements ExternalLoadableUiController {

    @FXML private JFXButton btnFilter;
    public JFXButton exportButton;
    @FXML private JFXTreeTableView<InventoryPictureModel> tableView;
    @FXML private JFXTreeTableColumn<InventoryPictureModel, String> tcNum;
    @FXML private JFXTreeTableColumn<InventoryPictureModel, String> tcId;
    @FXML private JFXTreeTableColumn<InventoryPictureModel, String> tcName;
    @FXML private JFXTreeTableColumn<InventoryPictureModel, String> tcType;
    @FXML private JFXTreeTableColumn<InventoryPictureModel, Double> tcAmount;
    @FXML private JFXTreeTableColumn<InventoryPictureModel, Double> tcPrice;
    @FXML private JFXTreeTableColumn<InventoryPictureModel, String> tcBatch;
    @FXML private JFXTreeTableColumn<InventoryPictureModel, String> tcBatchNo;
    @FXML private JFXTreeTableColumn<InventoryPictureModel, String> tcProductionTime;

    private ObservableList<InventoryPictureModel> viewModels = FXCollections.observableArrayList();
     private InventoryCheckBlService blService= InventoryCheckBlServiceFactory.getInstance();

    public void onBtnFilterClicked(ActionEvent actionEvent) {
        updateItems();
    }


    public void initialize() {
        initTable();
    }
    public void updateItems() {
        viewModels.clear();
        InventoryPictureItem[]inventoryPictureItems =blService.getInventoryPicture().getItems();
        for(int i=0;i<inventoryPictureItems.length;i++){
            viewModels.add(new InventoryPictureModel(i,inventoryPictureItems[i]));
        }
    }

    @FXML
    private void initTable() {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getInventoryPictureModel().getId()));
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getInventoryPictureModel().getName()));
        tcType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getInventoryPictureModel().getType()));
        tcAmount.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getInventoryPictureModel().getAmount()));
        tcPrice.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getValue().getInventoryPictureModel().getPrice()));
        tcBatch.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getValue().getInventoryPictureModel().getBatch()));
        tcBatchNo.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getValue().getInventoryPictureModel().getBatchNo()));
        tcProductionTime.setCellValueFactory(cellData -> new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getInventoryPictureModel().getDate())));
        tcNum.setCellValueFactory(cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getNo())));
        TreeItem<InventoryPictureModel> root = new RecursiveTreeItem<>(viewModels, RecursiveTreeObject::getChildren);
        tableView.setRoot(root);
        tableView.setShowRoot(false);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/check/InventoryPictureUi.fxml").loadAndGetPackageWithoutException();
    }


    public void onExportClicked(ActionEvent actionEvent) {
        if (viewModels.get(0)!= null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择路径");
            fileChooser.setInitialFileName(String.format("库存快照%s.xls", DateHelper.currentDateString("yyyy_MM_dd-HH_mm_ss")));
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                blService.export(file.getParent(),file.getName());

                PromptDialogHelper.start("导出成功！",String.format("经营情况表已经导出到%s。", file.getAbsolutePath()))
                        .addCloseButton("好","CHECK",null)
                        .createAndShow();
            }

        } else {
            PromptDialogHelper.start("导出失败！","请先查询报表！")
                    .addCloseButton("好","CHECK",null)
                    .createAndShow();
        }
    }
}
