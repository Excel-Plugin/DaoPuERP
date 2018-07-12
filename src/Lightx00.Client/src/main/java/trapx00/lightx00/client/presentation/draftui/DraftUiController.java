package trapx00.lightx00.client.presentation.draftui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.blservice.draftblservice.DraftBlService;
import trapx00.lightx00.client.blservice.draftblservice.DraftBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.draft.DraftVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.util.DateHelper;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DraftUiController implements ExternalLoadableUiController {
    public JFXButton selectAllButton;
    public JFXButton deleteButton;
    public JFXTreeTableView<DraftTableItemModel> draftTable;
    public JFXTreeTableColumn<DraftTableItemModel, String> tableDateColumn;
    public JFXTreeTableColumn<DraftTableItemModel, String> tableTypeColumn;
    public JFXTreeTableColumn<DraftTableItemModel, String> tableIdColumn;
    public JFXButton continueWriteButton;
    public JFXTreeTableColumn<DraftTableItemModel, String> tcDraftableId;


    public ObservableList<DraftTableItemModel> draftModels = FXCollections.observableArrayList();

    private DraftBlService blService = DraftBlServiceFactory.getInstance();


    public DraftUiController() { }

    public void initialize() {
        initDraftItem();
    }

    public int updateItems() {
        draftModels.clear();
        draftModels.addAll(Arrays.stream(blService.update()).map(DraftTableItemModel::new).collect(Collectors.toList()));
        return draftModels.size();

    }

    public void initDraftItem() {
        tableDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getDate())));
        tableTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getType().toString()));
        tableIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getId())));
        tcDraftableId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getDraft().getId()));
        TreeItem<DraftTableItemModel> root = new RecursiveTreeItem<>(draftModels, RecursiveTreeObject::getChildren);
        draftTable.setRoot(root);
        draftTable.setShowRoot(false);
    }


    public void onRefreshButtonClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getFrameworkUiController().refreshDraftStatus();
    }

    public void onDeleteButtonClicked(ActionEvent actionEvent) {
        int index = draftTable.getSelectionModel().getFocusedIndex();
        DraftTableItemModel model = draftTable.getRoot().getChildren().get(index).getValue();
        PromptDialogHelper.start("确定要删除这个草稿吗？","你选择了草稿" + model.getId())
                .addTable(ReadOnlyPairTableHelper.start()
                        .addPair("草稿ID", String.valueOf(model.getId()))
                        .addPair("草稿类型", model.getType().toString())
                        .addPair("草稿时间", DateHelper.fromDate(model.getDate()))
                        .addPair("草稿内容ID", model.getDraft().getId())
                        .create())
                .addCloseButton("确定", "CHECK",e -> {
                    deleteItem(index);
                })
                .addCloseButton("取消", "CLOSE", null)
                .createAndShow();
    }

    public void deleteItem(int index) {
        DraftVo draftVo = draftModels.get(index).toDraftVo();
        try {
            blService.delete(draftVo);
            PromptDialogHelper.start("删除成功！","草稿和草稿内容已经删除！").addCloseButton("好", "CHECK", e -> updateItems()).createAndShow();
            FrameworkUiManager.getFrameworkUiController().refreshDraftStatus();
        } catch (UncheckedRemoteException e) {
            PromptDialogHelper.start("网络错误！","网络错误，信息是：" + e.getMessage()).addCloseButton("好","CHECK",null).createAndShow();
        }
    }

    @SuppressWarnings("unchecked")
    public void onContinueWriteButtonClicked(ActionEvent actionEvent) {
        try {
            DraftTableItemModel model = draftTable.getSelectionModel().getSelectedItem().getValue();
            PromptDialogHelper.start("确认继续填写这个吗","")
                    .addTable(
                            ReadOnlyPairTableHelper.start()
                                .addPair("草稿ID", String.valueOf(model.getId()))
                                .addPair("草稿时间", DateHelper.fromDate(model.getDate()))
                                .addPair("草稿类型", model.getType().toString())
                                .addPair("草稿内容ID", model.getDraft().getId())
                                .create())
                    .addCloseButton("确定","CHECK",e -> {
                        try {
                            ExternalLoadedUiPackage ui = model.getDraft().continueWritableUi().continueWriting(model.getDraft());
                            FrameworkUiManager.switchFunction(ui, "继续填写草稿",true);
                            blService.delete(model.toDraftVo());
                            FrameworkUiManager.getFrameworkUiController().refreshDraftStatus();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    })
                    .addCloseButton("取消","CLOSE",null)
                    .createAndShow();
        } catch (Exception ex) {
            PromptDialogHelper.start("错误","请至少选一个条目。")
                    .addCloseButton("好的","DONE",null)
                    .createAndShow();
        }

    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/draftui/DraftUi.fxml").loadAndGetPackageWithoutException();
    }
}
