package trapx00.lightx00.client.presentation.inventoryui.gift;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.util.DateHelper;

/**
 * 详细信息UI。注意，单据UI详细信息实现了BIllDetailUi后，不需要再实现DraftDetailUi。
 */
public class InventoryGiftDetailUiController extends BillDetailUi {
    public JFXTextField tfBillState;
    public JFXTextField tfOperator;
    public JFXTextField tfDate;
    public JFXTextField tfId;
    public JFXTreeTableView<InventoryGiftItemModel> inventoryGiftItems;
    public JFXTreeTableColumn<InventoryGiftItemModel, String> tcName;
    public JFXTreeTableColumn<InventoryGiftItemModel, String> tcPrice;
    public JFXTreeTableColumn<InventoryGiftItemModel, String> tcAmount;

    private ObservableList<InventoryGiftItemModel> inventoryGiftItemModelObservableList = FXCollections.observableArrayList();

    @Override
    public ExternalLoadedUiPackage showContent(BillVo arg) {
        InventoryGiftVo inventoryGiftVo=(InventoryGiftVo)arg;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        InventoryGiftDetailUiController inventoryGiftDetailUiController = (InventoryGiftDetailUiController) externalLoadedUiPackage.getController();
        inventoryGiftDetailUiController.tfId.setText(inventoryGiftVo.getId());
        inventoryGiftDetailUiController.tfDate.setText(DateHelper.fromDate(inventoryGiftVo.getDate()));
        inventoryGiftDetailUiController.tfBillState.setText(inventoryGiftVo.getState().toString());
        inventoryGiftDetailUiController.tfOperator.setText(inventoryGiftVo.getOperatorId());
        inventoryGiftDetailUiController.addGiftItems(inventoryGiftVo.getGifts());
        return externalLoadedUiPackage;
    }

    public void addGiftItems(PromotionCommodity[] promotionCommodities) {
        for (PromotionCommodity promotionCommodity : promotionCommodities) {
            inventoryGiftItemModelObservableList.add(new InventoryGiftItemModel(promotionCommodity));
        }

    }

    public void initialize() {
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getPromotionCommodityObjectProperty().getCommodityId()));
        tcPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getPromotionCommodityObjectProperty().getPrice())));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getPromotionCommodityObjectProperty().getAmount())));
        TreeItem<InventoryGiftItemModel> root = new RecursiveTreeItem<>(inventoryGiftItemModelObservableList, RecursiveTreeObject::getChildren);
        inventoryGiftItems.setRoot(root);
        inventoryGiftItems.setShowRoot(false);

    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/gift/InventoryGiftDetailUi.fxml").loadAndGetPackageWithoutException();
    }


}
