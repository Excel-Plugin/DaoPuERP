package trapx00.lightx00.client.presentation.logui;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.blservice.logblservice.LogBackupBlService;
import trapx00.lightx00.client.blservice.logblservice.LogBackupBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadableUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.shared.queryvo.LogBackupVo;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.net.URI;

public class LogBackupUiController implements ExternalLoadableUiController {

    public JFXTreeTableView<LogBackupTableItemModel> logBackupTable;
    public JFXTreeTableColumn<LogBackupTableItemModel, String> logDateColumn;
    public JFXTreeTableColumn<LogBackupTableItemModel, String> logContentColumn;
    private ObservableList<LogBackupTableItemModel> logBackupTableItemModels = FXCollections.observableArrayList();
    private LogBackupBlService logBackupBlService = LogBackupBlServiceFactory.getInstance();

    public void initLogItem() {
        logDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getLogBackupVo().getDate()));
        logContentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getLogBackupVo().getUrl()));
        TreeItem<LogBackupTableItemModel> root = new RecursiveTreeItem<>(logBackupTableItemModels, RecursiveTreeObject::getChildren);
        logBackupTable.setRoot(root);
        logBackupTable.setShowRoot(false);

        logBackupTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                try {
                    LogBackupVo selected = getSelected();
                    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable text = new StringSelection(selected.getUrl());
                    clip.setContents(text, null);
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new URI(selected.getUrl()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }});
    }

    public LogBackupVo getSelected() {
        return logBackupTable.getSelectionModel().getSelectedItem().getValue().getLogBackupVo();
    }

    public void updateItems() {
        LogBackupVo[] logBackupVos = logBackupBlService.fetchCloudLog();
        if (logBackupVos != null) {
            for (LogBackupVo logBackupVo : logBackupVos) {
                logBackupTableItemModels.add(new LogBackupTableItemModel(logBackupVo));
            }
        }
    }

    public void initialize() {
        initLogItem();
        updateItems();
    }


    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/logui/LogBackupUi.fxml").loadAndGetPackageWithoutException();
    }
}
