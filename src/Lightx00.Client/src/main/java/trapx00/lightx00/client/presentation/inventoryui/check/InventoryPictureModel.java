package trapx00.lightx00.client.presentation.inventoryui.check;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.client.vo.inventorystaff.InventoryPictureItem;

public class InventoryPictureModel extends RecursiveTreeObject<InventoryPictureModel> {
    private int no;
    private ObjectProperty<InventoryPictureItem> inventoryPictureModelObjectProperty;

    public InventoryPictureModel(int no, InventoryPictureItem inventoryPictureItem) {
        this.no= no;
        this.inventoryPictureModelObjectProperty = new SimpleObjectProperty<>(inventoryPictureItem);
    }

    public InventoryPictureItem getInventoryPictureModel() {
        return inventoryPictureModelObjectProperty.get();
    }
    public int getNo(){
        return this.no;
    }

    public ObjectProperty<InventoryPictureItem> inventoryPictureModelObjectProperty() {
        return inventoryPictureModelObjectProperty;
    }

    public void setInventoryViewItemObjectProperty(InventoryPictureItem inventoryPictureModel) {
        this.inventoryPictureModelObjectProperty.set(inventoryPictureModel);
    }
}
