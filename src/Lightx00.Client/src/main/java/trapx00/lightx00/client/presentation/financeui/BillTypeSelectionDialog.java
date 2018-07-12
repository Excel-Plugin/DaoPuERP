package trapx00.lightx00.client.presentation.financeui;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.PromptDialogHelper;
import trapx00.lightx00.client.presentation.helpui.SelectingDialog;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.shared.po.bill.BillType;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class TypeModel extends RecursiveTreeObject<TypeModel> {
    private ObjectProperty<BillType> type;

    public TypeModel(BillType type) {
        this.type = new SimpleObjectProperty<>(type);
    }

    public BillType getType() {
        return type.get();
    }

    public ObjectProperty<BillType> typeProperty() {
        return type;
    }

    public void setType(BillType type) {
        this.type.set(type);
    }
}

public class BillTypeSelectionDialog extends SelectingDialog {

    public JFXTreeTableView<TypeModel> tbTypes;
    public JFXTreeTableColumn<TypeModel, String> tcName;

    private ObservableList<TypeModel> list = FXCollections.observableArrayList();
    private Consumer<List<BillType>> callback;



    public void initialize() {
        initTable();
        initItem();
    }

    public void initItem() {
        list.addAll(Arrays.stream(BillType.values()).map(TypeModel::new).collect(Collectors.toList()));
    }

    public void initTable() {
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getType().toString()));
        TreeItem<TypeModel> root = new RecursiveTreeItem<>(list, RecursiveTreeObject::getChildren);
        tbTypes.setRoot(root);
        tbTypes.setShowRoot(false);
        tbTypes.setEditable(true);
        tbTypes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void show(List<BillType> selected, Consumer<List<BillType>> callback) {
        ExternalLoadedUiPackage uiPackage = load();
        BillTypeSelectionDialog controller = uiPackage.getController();
        controller.callback = callback;
        controller.setSelected(selected);
        PromptDialogHelper.start("","").setContent(uiPackage.getComponent()).createAndShow();

    }

    public void setSelected(List<BillType> selected) {
        if (selected != null) {
            for (int i = 0; i < list.size(); i++) {
                BillType existing = tbTypes.getSelectionModel().getModelItem(i).getValue().getType();
                if (selected.contains(existing)) {
                    tbTypes.getSelectionModel().select(i);
                }
            }
        }

    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/BillTypeSelectionDialog.fxml").loadAndGetPackageWithoutException();
    }

    public void onBtnBackClicked(ActionEvent actionEvent) {
        onClose();
    }

    public void onBtnResetClicked(ActionEvent actionEvent) {
        tbTypes.getSelectionModel().clearSelection();
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        if (callback != null) {
            callback.accept(tbTypes.getSelectionModel().getSelectedItems().stream().map(x -> x.getValue().getType()).collect(Collectors.toList()));
        }
        onClose();
    }
}
