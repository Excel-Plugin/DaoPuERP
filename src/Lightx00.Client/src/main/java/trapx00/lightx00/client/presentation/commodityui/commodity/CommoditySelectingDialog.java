package trapx00.lightx00.client.presentation.commodityui.commodity;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import trapx00.lightx00.client.bl.commoditybl.CommodityInfo;
import trapx00.lightx00.client.bl.commoditybl.factory.CommodityInfoFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CommoditySelectingDialog extends SelectingDialog implements CommoditySelection {

    @FXML
    public JFXTreeTableView<CommoditySelectionItemModel> commodityTable;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableNameColumn;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableSortColumn;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableAmountColumn;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableIdColumn;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableDateColumn;
    @FXML private JFXTextField tfSearch;

    private Consumer<CommodityVo> callback;
    private ObservableList<CommoditySelectionItemModel> commodityModels = FXCollections.observableArrayList();
    private CommodityInfo blService= CommodityInfoFactory.getCommodityInfo();
    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/commodity/CommoditySelectingDialog.fxml").loadAndGetPackageWithoutException();
    }

    @FXML
    private void initialize() {
        initTable();
        initSearch();
        update();

    }
    private void initSearch() {
        tfSearch.setOnKeyPressed(e -> {
           if (e.getCode().equals(KeyCode.ENTER)) {
                if (tfSearch.getText().length() > 0) {
                    update(new CommodityQueryVo().eq("name", tfSearch.getText()));
                } else {
                    update();
               }

            }
        });
    }

    private void update() {
        update(new CommodityQueryVo());
    }

    private void update(CommodityQueryVo queryVo) {
        CommodityVo[] queryResult = blService.queryCommodity(queryVo);
        commodityModels.clear();
        commodityModels.addAll(Arrays.stream(queryResult).map(CommoditySelectionItemModel::new).collect(Collectors.toList()));

    }


    @FXML
    private void initTable() {
        tableDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getCommodityVoObjectProperty().getProductionDate())));
        tableNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityVoObjectProperty().getName()));
        tableIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getId())));
        tableAmountColumn.setCellValueFactory(cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getAmount())));
        tableSortColumn.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getValue().getCommodityVoObjectProperty().getType()));
        TreeItem<CommoditySelectionItemModel> root = new RecursiveTreeItem<>(commodityModels, RecursiveTreeObject::getChildren);
        commodityTable.setRoot(root);
        commodityTable.setShowRoot(false);

        commodityTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                CommodityVo selected = getSelected();
                onClose();
                if (callback != null && selected != null) {
                    callback.accept(selected);
                }
            }});
       // commodityTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //支持多选，没有这句话就是不支持
    }

    /**
     * 获得当前已经选择的日志。如果没有选择的，那么这个是空List（不是null！！！）
     * @return 当前已经选择的项
     */
    private CommodityVo getSelected() {
        return commodityTable.getSelectionModel().getSelectedItem().getValue().commodityVoObjectProperty.getValue();
    }


    @FXML
    private void onBtnSelectClicked(ActionEvent actionEvent) {
        onClose();
        CommodityVo selected = getSelected();
        if (callback != null && selected != null) {
            callback.accept(selected);
        }
    }


    @Override
    public void showCommoditySelectDialog(Consumer<CommodityVo> callback) {
        ExternalLoadedUiPackage uiPackage = load();
        CommoditySelectingDialog controller = (CommoditySelectingDialog) uiPackage.getController();
        controller.callback = callback;
        JFXDialog dialog = PromptDialogHelper.start("","").create();
        dialog.setContent((Region) uiPackage.getComponent());
        FrameworkUiManager.getCurrentDialogStack().pushAndShow(dialog);
    }

    @Override
    public CommodityVo queryId(String id) {

        return blService.queryCommodity(new CommodityQueryVo().idEq(id))[0];
    }

    /**
     * 设置目前选择的。用于刚开始的时候初始化已经选择的项。
     */
    private CommodityVo setSelected() {
        try {
            return commodityTable.getSelectionModel().getSelectedItem().getValue().getCommodityVoObjectProperty();
        } catch (Exception e) {
            return null;
        }
    }

    @FXML
    private void onBtnCloseClicked(ActionEvent actionEvent) {
        onClose();
    }
}
