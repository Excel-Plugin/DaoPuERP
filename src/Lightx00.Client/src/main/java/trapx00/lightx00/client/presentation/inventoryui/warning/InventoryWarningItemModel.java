package trapx00.lightx00.client.presentation.inventoryui.warning;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.shared.po.inventorystaff.InventoryWarningItem;

public class InventoryWarningItemModel extends RecursiveTreeObject<InventoryWarningItemModel> {

    public ObjectProperty<InventoryWarningItem> inventoryWarningItemObjectProperty;

    public InventoryWarningItemModel(InventoryWarningItem inventoryWarningItemObjectProperty) {
        this.inventoryWarningItemObjectProperty = new SimpleObjectProperty<>(inventoryWarningItemObjectProperty);
    }

    public InventoryWarningItem getInventoryWarningItemObjectProperty() {
        return inventoryWarningItemObjectProperty.get();
    }

    public ObjectProperty<InventoryWarningItem> inventoryWarningItemObjectPropertyProperty() {
        return inventoryWarningItemObjectProperty;
    }

    public void setInventoryWarningItemObjectProperty(InventoryWarningItem inventoryWarningItemObjectProperty) {
        this.inventoryWarningItemObjectProperty.set(inventoryWarningItemObjectProperty);
    }
}
