package trapx00.lightx00.client.presentation.inventoryui.check;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.client.vo.inventorystaff.InventoryViewItem;

public class InventoryViewModel extends RecursiveTreeObject<InventoryViewModel> {
    private ObjectProperty<InventoryViewItem> inventoryViewItemObjectProperty;

    public InventoryViewModel(InventoryViewItem inventoryViewItem) {
        this.inventoryViewItemObjectProperty = new SimpleObjectProperty<>(inventoryViewItem);
    }

    public InventoryViewItem getInventorViewItem() {
        return inventoryViewItemObjectProperty.get();
    }

    public ObjectProperty<InventoryViewItem> inventoryViewItemObjectPropertyProperty() {
        return inventoryViewItemObjectProperty;
    }

    public void setInventoryViewItemObjectProperty(InventoryViewItem inventoryViewItemObjectProperty) {
        this.inventoryViewItemObjectProperty.set(inventoryViewItemObjectProperty);
    }
}
