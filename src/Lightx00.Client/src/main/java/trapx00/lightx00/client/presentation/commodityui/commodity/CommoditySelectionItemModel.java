package trapx00.lightx00.client.presentation.commodityui.commodity;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;

public class CommoditySelectionItemModel extends RecursiveTreeObject<CommoditySelectionItemModel> {
    public ObjectProperty<CommodityVo> commodityVoObjectProperty;

    public CommoditySelectionItemModel(CommodityVo commodityVoObjectProperty) {
        this.commodityVoObjectProperty = new SimpleObjectProperty<>(commodityVoObjectProperty);
    }

    public CommodityVo getCommodityVoObjectProperty() {
        return commodityVoObjectProperty.get();
    }

    public ObjectProperty<CommodityVo> commodityVoObjectPropertyProperty() {
        return commodityVoObjectProperty;
    }

    public void setCommodityVoObjectProperty(CommodityVo commodityVoObjectProperty) {
        this.commodityVoObjectProperty.set(commodityVoObjectProperty);
    }
}
