package trapx00.lightx00.client.presentation.inventoryui.gift;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryGiftBlService;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryGiftBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.queryvo.InventoryGiftQueryVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class InventoryGiftSelectingDialog extends SelectingDialog implements InventoryGiftSelection {
    @FXML
    private JFXTreeTableView<GiftSelectingItemModel> gitfTable;
    @FXML private JFXTreeTableColumn<GiftSelectingItemModel, String> giftDateColumn;
    @FXML private JFXTreeTableColumn<GiftSelectingItemModel, String> giftIdColumn;
    @FXML private JFXTextField tfSearch;

    private Consumer<List<InventoryGiftVo>> callback;

    private ObservableList<GiftSelectingItemModel> giftSelectingItemModels = FXCollections.observableArrayList();
    private InventoryGiftBlService blService= InventoryGiftBlServiceFactory.getInstance();

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/gift/InventoryGiftSelectingDialog.fxml").loadAndGetPackageWithoutException();
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
                    update(new InventoryGiftQueryVo().eq("id", tfSearch.getText()));
                } else {
                    update();
                }

            }
        });
    }

    private void update() {
        update(new InventoryGiftQueryVo());
    }

    private void update(InventoryGiftQueryVo queryVo) {
        InventoryGiftVo[] queryResult={new InventoryGiftVo("GifT",new Date(),BillState.Approved,null,"X0001")};
        giftSelectingItemModels.clear();
        giftSelectingItemModels.addAll(Arrays.stream(queryResult).map(GiftSelectingItemModel::new).collect(Collectors.toList()));
    }


    @FXML
    private void initTable() {
        giftDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getInventoryGiftVoObjectProperty().getDate())));
        giftIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getInventoryGiftVoObjectProperty().getId()));

        TreeItem<GiftSelectingItemModel> root = new RecursiveTreeItem<>(giftSelectingItemModels, RecursiveTreeObject::getChildren);
        gitfTable.setRoot(root);
        gitfTable.setShowRoot(false);
        gitfTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //支持多选，没有这句话就是不支持
    }

    /**
     * 获得当前已经选择的日志。如果没有选择的，那么这个是空List（不是null！！！）
     * @return 当前已经选择的项
     */
    private List<InventoryGiftVo> getSelected() {
        return gitfTable.getSelectionModel().getSelectedItems().stream()
                .map(x -> x.getValue().getInventoryGiftVoObjectProperty())
                .collect(Collectors.toList());
    }

    /**
     * 设置目前选择的。用于刚开始的时候初始化已经选择的项。
     * @param
     */
    @FXML
    private void onBtnSelectClicked(ActionEvent actionEvent) {
        List<InventoryGiftVo> selected = getSelected();
        if (callback != null && selected != null) {
            callback.accept(selected);
        }
        onClose();
    }


    /**
     * 重点方法。也是外界调用的方法。
     * @param callback 选择结束后的回调函数。如果用户点了取消按钮，那么直接退出，不会触发回调函数。
     *                 用法参见……
     *
     */
    public void showInventoryGiftSelectDialog(Consumer<List<InventoryGiftVo>> callback) {
        ExternalLoadedUiPackage uiPackage = load();
        InventoryGiftSelectingDialog controller = (InventoryGiftSelectingDialog) uiPackage.getController();
        controller.callback = callback;
        JFXDialog dialog = PromptDialogHelper.start("","").create();
        dialog.setContent((Region) uiPackage.getComponent());
        FrameworkUiManager.getCurrentDialogStack().pushAndShow(dialog);
    }

    @FXML
    private void onBtnCloseClicked(ActionEvent actionEvent) {
        onClose();
    }



    @Override
    public InventoryGiftVo queryId(String id) {
        return blService.query(new InventoryGiftQueryVo().idEq(id))[0];
    }
}
