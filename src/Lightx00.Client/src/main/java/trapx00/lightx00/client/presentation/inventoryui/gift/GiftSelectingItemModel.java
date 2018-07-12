package trapx00.lightx00.client.presentation.inventoryui.gift;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;

public class GiftSelectingItemModel  extends RecursiveTreeObject<GiftSelectingItemModel> {
    public ObjectProperty<InventoryGiftVo> inventoryGiftVoObjectProperty;

    public GiftSelectingItemModel(InventoryGiftVo inventoryGiftVoObjectProperty) {
        this.inventoryGiftVoObjectProperty = new SimpleObjectProperty<>(inventoryGiftVoObjectProperty);
    }

    public InventoryGiftVo getInventoryGiftVoObjectProperty() {
        return inventoryGiftVoObjectProperty.get();
    }

    public ObjectProperty<InventoryGiftVo> inventoryGiftVoObjectPropertyProperty() {
        return inventoryGiftVoObjectProperty;
    }

    public void setInventoryGiftVoObjectProperty(InventoryGiftVo inventoryGiftVoObjectProperty) {
        this.inventoryGiftVoObjectProperty.set(inventoryGiftVoObjectProperty);
    }
}
