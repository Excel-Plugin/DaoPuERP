package trapx00.lightx00.client.presentation.clientui;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlService;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ClientUiController implements ClientInfoUi, ExternalLoadableUiController {

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
        ClientUiController controller = uiPackage.getController();
        JFXDialog dialog = PromptDialogHelper.start("", "").create();
        dialog.setContent((Region) uiPackage.getComponent());
        dialog.show();
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

    private void initLogItem() {
        clientIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getId()));
        clientNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getName()));
        clientTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getClientType().toString()));
        clientLevelColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getClientLevel() + ""));
        clientPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getPhone()));
        TreeItem<ClientSelectionItemModel> root = new RecursiveTreeItem<>(clientSelectionItemModels, RecursiveTreeObject::getChildren);
        clientTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        clientTable.setRoot(root);
        clientTable.setShowRoot(false);
    }

    private void initClients() {
        ClientVo[] clientVos = blService.query("");
        for (ClientVo clientVo : clientVos) {
            clientSelectionItemModels.add(new ClientSelectionItemModel(clientVo));
        }
    }

    private void initSearch() {
        FrameworkUiManager.getWholePane().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                ClientVo[] clientVos = blService.query(tfSearch.getText());
                clientSelectionItemModels.clear();
                clientSelectionItemModels.addAll(Arrays.stream(clientVos).map(ClientSelectionItemModel::new).collect(Collectors.toList()));
            }
        });
        tfSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                ClientVo[] clientVos = blService.query(tfSearch.getText());
                clientSelectionItemModels.clear();
                clientSelectionItemModels.addAll(Arrays.stream(clientVos).map(ClientSelectionItemModel::new).collect(Collectors.toList()));
            }
        });
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/clientui/ClientUi.fxml").loadAndGetPackageWithoutException();
    }

    @FXML
    private void onBtnAddClicked(ActionEvent actionEvent) {
        FrameworkUiManager.switchFunction(ClientAddUiController.class, "管理客户", true);
    }

    @FXML
    private void onBtnModifyClicked(ActionEvent actionEvent) {
        ClientSelectionItemModel model = clientTable.getSelectionModel().getSelectedItem().getValue();
        if (model != null) {
            ClientVo selected = model.getClientVoObjectProperty();
            PromptDialogHelper.start("修改客户信息", "")
                    .setContent(selected.modifyUi().showContent(selected).getComponent())
                    .createAndShow();
        } else {
            PromptDialogHelper.start("错误", "请至少选一个条目。")
                    .addCloseButton("好的", "DONE", null)
                    .createAndShow();
        }
        clientSelectionItemModels.clear();
        initClients();
    }

    @FXML
    private void onBtnDeleteClicked(ActionEvent actionEvent) {
        PromptDialogHelper.start("是否要删除", null)
                .addCloseButton("确定", "DONE", e -> delete())
                .addCloseButton("取消", "UNDO", null)
                .createAndShow();
    }

    private void delete() {
        List<String> cannotDeleteIdList=new ArrayList<>();
        List<String> idList = new ArrayList<>();
        ObservableList<TreeItem<ClientSelectionItemModel>> clientSelectionTreeItemModels = clientTable.getSelectionModel().getSelectedItems();
        for (TreeItem<ClientSelectionItemModel> treeItem : clientSelectionTreeItemModels) {
            idList.add(treeItem.getValue().getClientVoObjectProperty().getId());
            if(treeItem.getValue().getClientVoObjectProperty().getReceivable()!=0.0||treeItem.getValue().getClientVoObjectProperty().getPayable()!=0.0){
                cannotDeleteIdList.add(treeItem.getValue().getClientVoObjectProperty().getId());
            }
        }
        if(cannotDeleteIdList.size()>0){
            StringBuilder content=new StringBuilder();
            content.append("|");
            for(String id:cannotDeleteIdList){
                content.append(id);
                content.append("|");
            }
            PromptDialogHelper.start("以下客户应收应付不为0，再次确定是否要删除", new String(content))
                    .addCloseButton("确定", "DONE", e -> confirmDelete())
                    .addCloseButton("取消", "UNDO", null)
                    .createAndShow();
        }
    }

    private void confirmDelete(){
        List<String> idList = new ArrayList<>();
        ObservableList<TreeItem<ClientSelectionItemModel>> clientSelectionTreeItemModels = clientTable.getSelectionModel().getSelectedItems();
        for (TreeItem<ClientSelectionItemModel> treeItem : clientSelectionTreeItemModels) {
            idList.add(treeItem.getValue().getClientVoObjectProperty().getId());
        }
        ResultMessage resultMessage = blService.delete(idList.toArray(new String[idList.size()]));
        if (resultMessage == ResultMessage.Success) {
            ObservableList<Integer> commodityIndexList = clientTable.getSelectionModel().getSelectedIndices();
            for (int index : commodityIndexList) {
                clientSelectionItemModels.remove(index);
            }
            PromptDialogHelper.start("删除成功", null)
                    .addCloseButton("确定", "DONE", null)
                    .createAndShow();
        } else {
            PromptDialogHelper.start("删除失败，请重试", null)
                    .addCloseButton("确定", "DONE", null)
                    .createAndShow();
        }
    }

    @FXML
    private void onBtnSelectClicked(ActionEvent actionEvent) {
        ClientSelectionItemModel model = clientTable.getSelectionModel().getSelectedItem().getValue();
        if (model != null) {
            ClientVo selected = model.getClientVoObjectProperty();
            PromptDialogHelper.start("客户详细信息", "")
                    .setContent(selected.detailUi().showContent(selected).getComponent())
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        } else {
            PromptDialogHelper.start("错误", "请至少选一个条目。")
                    .addCloseButton("好的", "DONE", null)
                    .createAndShow();
        }
    }

    @FXML
    private void onBtnCloseClicked(ActionEvent actionEvent) {
        FrameworkUiManager.switchBackToHome();
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
