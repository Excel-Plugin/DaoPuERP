package trapx00.lightx00.client.presentation.financeui;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import trapx00.lightx00.client.presentation.bankaccountui.BankAccountModel;
import trapx00.lightx00.client.presentation.clientui.ClientSelectionItemModel;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelectionItemModel;

public class TableInitialization {
     public static void initBankAccountTable(JFXTreeTableView<BankAccountModel> tableBankAccounts, JFXTreeTableColumn<BankAccountModel, Integer> tcBankId, JFXTreeTableColumn<BankAccountModel, String> tcBankName, JFXTreeTableColumn<BankAccountModel, Double> tcBankBalance, ObservableList<BankAccountModel> bankAccountModels) {
        tcBankId.setCellValueFactory(cellData -> cellData.getValue().getValue().idProperty().asObject());
        tcBankName.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        tcBankBalance.setCellValueFactory(cellData -> cellData.getValue().getValue().balanceProperty().asObject());
        TreeItem<BankAccountModel> root = new RecursiveTreeItem<>(bankAccountModels, RecursiveTreeObject::getChildren);
        tableBankAccounts.setRoot(root);
        tableBankAccounts.setShowRoot(false);
        tableBankAccounts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public static void initClientTable(TreeTableView<ClientSelectionItemModel> tableClient, TreeTableColumn<ClientSelectionItemModel, String> tcClientId, TreeTableColumn<ClientSelectionItemModel, String> tcClientName, TreeTableColumn<ClientSelectionItemModel, String> tcClientType, ObservableList<ClientSelectionItemModel> clientModels) {
        tcClientId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getId()));
        tcClientName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getName()));
        tcClientType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getClientVoObjectProperty().getClientType().toString()));
        TreeItem<ClientSelectionItemModel> root = new RecursiveTreeItem<>(clientModels, RecursiveTreeObject::getChildren);
        tableClient.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableClient.setRoot(root);
        tableClient.setShowRoot(false);
    }

    public static void initCommodityTable(JFXTreeTableView<CommoditySelectionItemModel> tableCommodity, JFXTreeTableColumn<CommoditySelectionItemModel, String> tcCommodityName, JFXTreeTableColumn<CommoditySelectionItemModel, String> tcCommodityId, ObservableList<CommoditySelectionItemModel> commodityModels) {
        tcCommodityName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityVoObjectProperty().getName()));
        tcCommodityId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getId())));
        TreeItem<CommoditySelectionItemModel> root = new RecursiveTreeItem<>(commodityModels, RecursiveTreeObject::getChildren);
        tableCommodity.setRoot(root);
        tableCommodity.setShowRoot(false);
        tableCommodity.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

}
