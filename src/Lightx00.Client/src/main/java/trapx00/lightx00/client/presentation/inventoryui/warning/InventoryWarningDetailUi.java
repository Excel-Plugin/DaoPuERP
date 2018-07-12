package trapx00.lightx00.client.presentation.inventoryui.warning;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.presentation.commodityui.factory.CommodityUiFactory;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryDetailBillVo;
import trapx00.lightx00.shared.po.inventorystaff.InventoryWarningItem;
import trapx00.lightx00.shared.util.DateHelper;

public class InventoryWarningDetailUi extends BillDetailUi {
    public JFXTextField tfBillState;
    public JFXTextField tfBillType;
    public JFXTextField tfOperator;
    public JFXTextField tfDate;
    public JFXTextField tfId;
    public JFXTreeTableView<InventoryWarningItemModel> inventoryGiftItems;
    public JFXTreeTableColumn<InventoryWarningItemModel, String> tcName;
    public JFXTreeTableColumn<InventoryWarningItemModel, String> tcDeltaAmount;
    public JFXTreeTableColumn<InventoryWarningItemModel, String> tcId;

    private ObservableList<InventoryWarningItemModel> inventoryGiftItemModelObservableList = FXCollections.observableArrayList();


    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/warning/InventoryWarningDetailUi.fxml").loadAndGetPackageWithoutException();
    }

    @Override
    public ExternalLoadedUiPackage showContent(BillVo arg) {
        InventoryDetailBillVo inventoryDetailBillVo=(InventoryDetailBillVo)arg;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        InventoryWarningDetailUi inventoryWarningDetailUi = (InventoryWarningDetailUi) externalLoadedUiPackage.getController();
        inventoryWarningDetailUi.tfId.setText(inventoryDetailBillVo.getId());
        inventoryWarningDetailUi.tfDate.setText(DateHelper.fromDate(inventoryDetailBillVo.getDate()));
        inventoryWarningDetailUi.tfBillState.setText(inventoryDetailBillVo.getState().toString());
        inventoryWarningDetailUi.tfOperator.setText(inventoryDetailBillVo.getOperatorId());
        inventoryWarningDetailUi.tfBillType.setText(inventoryDetailBillVo.getType().toString());
        inventoryWarningDetailUi.addWarningItems(inventoryDetailBillVo.getCommodities());
        return externalLoadedUiPackage;
    }

    public void addWarningItems(InventoryWarningItem[] promotionCommodities) {
        for ( InventoryWarningItem inventoryWarningItem : promotionCommodities) {
            inventoryGiftItemModelObservableList.add(new InventoryWarningItemModel(inventoryWarningItem));
        }

    }

    public void initialize() {
        tcId.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getValue().getInventoryWarningItemObjectProperty().getId()));
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(CommodityUiFactory.getCommoditySelectionUi().queryId(cellData.getValue().getValue().getInventoryWarningItemObjectProperty().getId()).getName()));
        tcDeltaAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getInventoryWarningItemObjectProperty().getDelta())));
        TreeItem<InventoryWarningItemModel> root = new RecursiveTreeItem<>(inventoryGiftItemModelObservableList, RecursiveTreeObject::getChildren);
        inventoryGiftItems.setRoot(root);
        inventoryGiftItems.setShowRoot(false);

    }

}
