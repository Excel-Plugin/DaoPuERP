package trapx00.lightx00.client.presentation.adminui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import trapx00.lightx00.client.bl.adminbl.factory.UserManagementBlFactory;
import trapx00.lightx00.client.blservice.adminblservice.UserManagementBlService;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.PromptDialogHelper;
import trapx00.lightx00.client.presentation.helpui.SelectingDialog;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.queryvo.SpecificUserAccountQueryVo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EmployeeSelectionUiController extends SelectingDialog implements EmployeeSelection {
    public JFXTreeTableView<EmployeeSelectionItemModel> tbEmployee;
    public JFXTreeTableColumn<EmployeeSelectionItemModel, String> tcId;
    public JFXTreeTableColumn<EmployeeSelectionItemModel, String> tcPosition;
    public JFXTreeTableColumn<EmployeeSelectionItemModel, String> tcName;
    public JFXTreeTableColumn<EmployeeSelectionItemModel, String> tcWorkSince;
    public JFXTextField tfSearch;
    public JFXButton btnSelect;
    public JFXButton btnClose;

    private Consumer<List<EmployeeVo>> callback;
    private ObservableList<EmployeeSelectionItemModel> employeeSelectionItemModelObservableList = FXCollections.observableArrayList();
    private UserManagementBlService blService= UserManagementBlFactory.getController();
    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/adminui/EmployeeSelectionUi.fxml").loadAndGetPackageWithoutException();
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
                    update(new UserAccountQueryVo().eq("name", tfSearch.getText()));
                } else {
                    update();
                }

            }
        });
    }

    private void update() {
        update(new UserAccountQueryVo().addQueryVoForAllEmployeePosition(new SpecificUserAccountQueryVo()));
    }

    private void update(UserAccountQueryVo queryVo) {
        EmployeeVo[] queryResult = blService.query(queryVo);
        employeeSelectionItemModelObservableList.clear();
        employeeSelectionItemModelObservableList.addAll(Arrays.stream(queryResult).map(EmployeeSelectionItemModel::new).collect(Collectors.toList()));

    }


    @FXML
    private void initTable() {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getEmployeeVoObjectProperty().getId()));
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getEmployeeVoObjectProperty().getName()));
        tcPosition.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getEmployeeVoObjectProperty().getPosition().toString()));
        tcWorkSince.setCellValueFactory(cellData->new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getEmployeeVoObjectProperty().getWorkSince())));
        TreeItem<EmployeeSelectionItemModel> root = new RecursiveTreeItem<>(employeeSelectionItemModelObservableList, RecursiveTreeObject::getChildren);
        tbEmployee.setRoot(root);
        tbEmployee.setShowRoot(false);
        tbEmployee.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tbEmployee.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                onBtnSelectClicked();
            }
        });
    }

    /**
     * 获得当前已经选择的日志。如果没有选择的，那么这个是空List（不是null！！！）
     * @return 当前已经选择的项
     */
    private List<EmployeeVo> getSelected() {
        List<EmployeeVo> list = new ArrayList<>();
        list.add(tbEmployee.getSelectionModel().getSelectedItem().getValue().employeeVoObjectProperty.getValue());
        return list;
    }

    @Override
    public void showEmployeeSelectDialog(Consumer<List<EmployeeVo>> callback) {
        ExternalLoadedUiPackage uiPackage = load();
        EmployeeSelectionUiController controller = uiPackage.getController();
        controller.callback = callback;
        PromptDialogHelper.start("","").setContent(uiPackage.getComponent()).createAndShow();
    }

    @Override
    public EmployeeVo queryId(String id) {
        return blService.query(new UserAccountQueryVo().idEq(id))[0];
    }

    /**
     * 设置目前选择的。用于刚开始的时候初始化已经选择的项。
     */
    private EmployeeVo setSelected() {
        try {
            return tbEmployee.getSelectionModel().getSelectedItem().getValue().getEmployeeVoObjectProperty();
        } catch (Exception e) {
            return null;
        }
    }

    public void onBtnSelectClicked() {
        List<EmployeeVo> selected = getSelected();
        if (callback != null && selected != null) {
            callback.accept(selected);
        }
        onClose();
    }

    public void onBtnCloseClicked() {
        onClose();
    }
}
