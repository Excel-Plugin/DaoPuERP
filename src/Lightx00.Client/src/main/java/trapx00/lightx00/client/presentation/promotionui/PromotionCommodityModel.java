package trapx00.lightx00.client.presentation.promotionui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;

public class PromotionCommodityModel extends RecursiveTreeObject<PromotionCommodityModel> {
    private StringProperty id;
    private StringProperty name;
    private DoubleProperty price;
    private DoubleProperty amount;

    public PromotionCommodityModel(String id, String name, double price, double amount) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.amount = new SimpleDoubleProperty(amount);
    }

    //根据数据库存的PromotionCommodity显示
    public PromotionCommodityModel(PromotionCommodity promotionCommodity) {
        this(promotionCommodity.getCommodityId(), promotionCommodity.getName(),promotionCommodity.getPrice(), promotionCommodity.getAmount());
    }


    public PromotionCommodity toPromotionCommodity() {
        return new PromotionCommodity(id.getValue(),name.getValue(), price.getValue(), amount.getValue());
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double price) {
        this.amount.set(price);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }


}
