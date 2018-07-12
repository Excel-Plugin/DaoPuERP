package trapx00.lightx00.client.presentation.notificationui;

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
import trapx00.lightx00.client.bl.notificationbl.factory.NotificationBlFactory;
import trapx00.lightx00.client.blservice.notificationblservice.NotificationBlService;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class NotificationUiController implements ExternalLoadableUiController {

    public JFXButton selectAllButton;
    public JFXTreeTableView<NotificationModel> notificationTable;
    public JFXTreeTableColumn<NotificationModel, String> tableDateColumn;
    public JFXTreeTableColumn<NotificationModel, String> tableTypeColumn;
    public JFXTreeTableColumn<NotificationModel, String> tableIdColumn;
    public JFXButton readButton;
    public JFXTreeTableColumn<NotificationModel, String> tableSenderColumn;

    public ObservableList<NotificationModel> notificationModels = FXCollections.observableArrayList();

    private NotificationBlService blService = NotificationBlFactory.getController();

    public NotificationUiController() { }

    public void initialize(){
        initNotifyItem();
    }

    public int updateItems(){
        notificationModels.clear();
        notificationModels.addAll(Arrays.stream(blService.update()).map(NotificationModel::new).collect(Collectors.toList()));
        return notificationModels.size();
    }

    public void initNotifyItem() {
        tableDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getVoObjectProperty().getDate())));
        tableTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getVoObjectProperty().getType().toString()));
        tableIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getVoObjectProperty().getId())));
        tableSenderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getVoObjectProperty().getSender().getName()));
        TreeItem<NotificationModel> root = new RecursiveTreeItem<>(notificationModels, RecursiveTreeObject::getChildren);
        notificationTable.setRoot(root);
        notificationTable.setShowRoot(false);
        notificationTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                onReadButtonClicked(null);
            }});
    }


    public void onRefreshButtonClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getFrameworkUiController().refreshNotificationStatus();
    }

    public NotificationModel getSelected() {
        return notificationTable.getSelectionModel().getSelectedItem().getValue();
    }

    public void showNotSelectedDialog() {
        PromptDialogHelper.start("请选择一条通知！","请选择一条通知！")
            .addCloseButton("好","CHECK",null)
            .createAndShow();
    }

    public void deleteItem(int index){
        updateItems();
    }

    public void onReadButtonClicked(ActionEvent actionEvent) {
        NotificationModel model = getSelected();
        if (model != null) {
            ExternalLoadedUiPackage uiPackage = model.getVoObjectProperty().notificationDetailUi().showContent(model.getVoObjectProperty());
            NotificationDetailUi detailUi = uiPackage.getController();
            detailUi.setOnExit(this::updateItems);
            PromptDialogHelper.start("消息详细内容","你选择了通知")
                .setContent(uiPackage.getComponent())
                .createAndShow();
        } else {
            showNotSelectedDialog();
        }


    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/notificationui/NotificationUi.fxml").loadAndGetPackageWithoutException();
    }
}
