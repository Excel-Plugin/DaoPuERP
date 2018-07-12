package trapx00.lightx00.client.presentation.promotionui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import trapx00.lightx00.client.vo.manager.promotion.ClientPromotionVo;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.client.vo.manager.promotion.TotalPricePromotionVo;
import trapx00.lightx00.shared.po.manager.promotion.PromotionType;

public class PromotionSelectionItemModel extends RecursiveTreeObject<PromotionSelectionItemModel> {
    private StringProperty id;
    private ObjectProperty<PromotionType> type;
    private StringProperty gift;
    private StringProperty coupon;
    private StringProperty sale;
    private ObjectProperty<PromotionVoBase> promotion;

    public PromotionSelectionItemModel(PromotionVoBase promotionVo) {
        this.id = new SimpleStringProperty(promotionVo.getId());
        this.type = new SimpleObjectProperty<>(promotionVo.getType());
        double couponPrice = 0;
        double salePrice = 0;
        if(promotionVo.getPromotionCommodities()==null)
            this.gift = new SimpleStringProperty("无");
        else if(promotionVo.getType().equals(PromotionType.ComSalePromotion))
            this.gift = new SimpleStringProperty("");
        else
            this.gift = new SimpleStringProperty("有");

        switch (promotionVo.getType()){
            case ClientPromotion:
                couponPrice = ((ClientPromotionVo)promotionVo).getCouponPrice();
                salePrice = ((ClientPromotionVo)promotionVo).getSalePrice();
                break;
            case TotalPricePromotion:
                couponPrice = ((TotalPricePromotionVo)promotionVo).getCouponPrice();
                break;
            default:
                break;
        }
        this.coupon = new SimpleStringProperty(String.valueOf(couponPrice));
        this.sale = new SimpleStringProperty(String.valueOf(salePrice));
        this.promotion = new SimpleObjectProperty<>(promotionVo) ;
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public PromotionType getType() {
        return type.get();
    }

    public ObjectProperty<PromotionType> typeProperty() {
        return type;
    }

    public void setType(PromotionType type) {
        this.type.set(type);
    }


    public String getGift() {
        return gift.get();
    }

    public StringProperty giftProperty() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift.set(gift);
    }

    public String getCoupon() {
        return coupon.get();
    }

    public StringProperty couponProperty() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon.set(coupon);
    }

    public String getSale() {
        return sale.get();
    }

    public StringProperty saleProperty() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale.set(sale);
    }

    public PromotionVoBase getPromotion() {
        return promotion.get();
    }

    public ObjectProperty<PromotionVoBase> promotionProperty() {
        return promotion;
    }

    public void setPromotion(PromotionVoBase promotion) {
        this.promotion.set(promotion);
    }
}
