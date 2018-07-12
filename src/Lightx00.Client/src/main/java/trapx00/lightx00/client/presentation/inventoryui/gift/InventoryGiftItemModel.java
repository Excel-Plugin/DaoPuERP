package trapx00.lightx00.client.presentation.inventoryui.gift;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;

public class InventoryGiftItemModel extends RecursiveTreeObject<InventoryGiftItemModel> {
    private ObjectProperty<PromotionCommodity> promotionCommodityObjectProperty;

    public InventoryGiftItemModel(PromotionCommodity promotionCommodity) {
        this.promotionCommodityObjectProperty = new SimpleObjectProperty<>(promotionCommodity);
    }

    public PromotionCommodity getPromotionCommodityObjectProperty() {
        return promotionCommodityObjectProperty.get();
    }

    public ObjectProperty<PromotionCommodity> promotionCommodityObjectPropertyProperty() {
        return promotionCommodityObjectProperty;
    }

    public void setPromotionCommodityObjectProperty(PromotionCommodity promotionCommodityObjectProperty) {
        this.promotionCommodityObjectProperty.set(promotionCommodityObjectProperty);
    }
}
