package trapx00.lightx00.client.presentation.inventoryui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;

public class CommodityItemModel extends RecursiveTreeObject<CommodityItemModel> {
    ObjectProperty<CommodityItem> commodityItemObjectProperty;

    public CommodityItemModel(CommodityItem commodityItem) {
        this.commodityItemObjectProperty = new SimpleObjectProperty<>(commodityItem);
    }

    public CommodityItem getCommodityItemObjectProperty() {
        return commodityItemObjectProperty.get();
    }

    public CommodityItem toCommodityItem(){
        return new CommodityItem(commodityItemObjectProperty.getValue().getCommodityId(),commodityItemObjectProperty.getValue().getName(),commodityItemObjectProperty.getValue().getType(),commodityItemObjectProperty.getValue().getNumber(),commodityItemObjectProperty.getValue().getPrice(),commodityItemObjectProperty.getValue().getTotal(),commodityItemObjectProperty.getValue().getComment());
    }

    public void setCommodityItemObjectProperty(CommodityItem commodityItemObjectProperty) {
        this.commodityItemObjectProperty.set(commodityItemObjectProperty);
    }

    public ObjectProperty<CommodityItem> commodityItemObjectPropertyProperty() {
        return commodityItemObjectProperty;
    }
}
