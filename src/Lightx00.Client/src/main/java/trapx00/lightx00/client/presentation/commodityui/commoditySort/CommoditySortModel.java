package trapx00.lightx00.client.presentation.commodityui.commoditySort;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.client.vo.inventorystaff.CommoditySortVo;

public class CommoditySortModel extends RecursiveTreeObject<CommoditySortModel> {

    public ObjectProperty<CommoditySortVo> commoditySortVoObjectProperty;

    public CommoditySortModel(CommoditySortVo commoditySortVoObjectProperty) {
        this.commoditySortVoObjectProperty = new SimpleObjectProperty<>(commoditySortVoObjectProperty);
    }

    public CommoditySortVo getCommoditySortVoObjectProperty() {
        return commoditySortVoObjectProperty.get();
    }

    public ObjectProperty<CommoditySortVo> commoditySortVoObjectPropertyProperty() {
        return commoditySortVoObjectProperty;
    }

    public void setCommoditySortVoObjectProperty(CommoditySortVo commoditySortVoObjectProperty) {
        this.commoditySortVoObjectProperty.set(commoditySortVoObjectProperty);
    }

}
