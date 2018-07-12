package trapx00.lightx00.client.presentation.clientui;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlService;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.salestaff.ClientVo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ClientDialogUiController extends SelectingDialog implements ClientInfoUi, ExternalLoadableUiController {

    public Consumer<ClientVo> callback;
    @FXML
    private JFXTextField tfSearch;
    @FXML
    private TreeTableView<ClientSelectionItemModel> clientTable;
    @FXML
    private TreeTableColumn<ClientSelectionItemModel, String> clientIdColumn;
    @FXML
    private TreeTableColumn<ClientSelectionItemModel, String> clientNameColumn;
    @FXML
    private TreeTableColumn<ClientSelectionItemModel, String> clientTypeColumn;
    @FXML
    private TreeTableColumn<ClientSelectionItemModel, String> clientLevelColumn;
    @FXML
    private TreeTableColumn<ClientSelectionItemModel, String> clientPhoneColumn;
    private ObservableList<ClientSelectionItemModel> clientSelectionItemModels = FXCollections.observableArrayList();

    private ClientBlService blService = ClientBlServiceFactory.getInstance();

    /**
     * show the select client dialog
     *
     * @param callback call back function
     */
    public void showClientSelectDialog(Consumer<ClientVo> callback) {
        ExternalLoadedUiPackage uiPackage = load();
        ClientDialogUiController controller = (ClientDialogUiController) uiPackage.getController();
        controller.callback = callback;
        PromptDialogHelper.start("", "").setContent((Region) uiPackage.getComponent()).createAndShow();
    }

    @FXML
    private void initialize() {
        initLogItem();
        initClients();
        initSearch();

        clientTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                onBtnSelectClicked(null);
            }
        });
    }

    private void initClients() {
        ClientVo[] clientVos = blService.query("");
        for (ClientVo clientVo : clientVos) {
            clientSelectionItemModels.add(new ClientSelectionItemModel(clientVo));
        }
    }

    private void initSearch() {
        tfSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                ClientVo[] clientVos = blService.query(tfSearch.getText());
                clientSelectionItemModels.clear();
                clientSelectionItemModels.addAll(Arrays.stream(clientVos).map(ClientSelectionItemModel::new).collect(Collectors.toList()));
            }
        });
    }

    private void initLogItem() {
        clientIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getId()));
        clientNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getName()));
        clientTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getClientType().toString()));
        clientLevelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getClientLevel() + ""));
        clientPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getPhone()));
        TreeItem<ClientSelectionItemModel> root = new RecursiveTreeItem<>(clientSelectionItemModels, RecursiveTreeObject::getChildren);
        clientTable.setRoot(root);
        clientTable.setShowRoot(false);
    }

    /**
     * 获得当前已经选择的日志。如果没有选择的，那么这个是空List（不是null！！！）
     *
     * @return 当前已经选择的项
     */
    private List<ClientVo> getSelected() {
        return clientTable.getSelectionModel().getSelectedItems().stream()
                .map(x -> x.getValue().getClientVoObjectProperty())
                .collect(Collectors.toList());
    }

    /**
     * 设置目前选择的。用于刚开始的时候初始化已经选择的项。
     *
     * @param selected 已经选择的项
     */
    private void setSelected(List<ClientVo> selected) {
        List<String> ids = selected.stream().map(ClientVo::getId).collect(Collectors.toList());
        clientTable.getSelectionModel().select(Integer.parseInt(ids.get(0)));
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/clientui/ClientDialogUi.fxml").loadAndGetPackageWithoutException();
    }

    @FXML
    private void onBtnSelectClicked(ActionEvent actionEvent) {
        onClose(); //一定要调用这个来把弹出框关了。
        if (callback != null) {
            callback.accept(getSelected().get(0)); //选择结束，调用回调方法。
        }
    }

    @FXML
    private void onBtnCloseClicked(ActionEvent actionEvent) {
        onClose();
    }

    /**
     * query the whole clientvo by id
     *
     * @param id
     * @return the whole clientvo
     */
    @Override
    public ClientVo queryById(String id) {
        return blService.queryById(id);
    }
}
