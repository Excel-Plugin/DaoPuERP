package trapx00.lightx00.shared.po.manager.promotion;

import java.io.Serializable;

public class PromotionCommodity implements Serializable {
    private String commodityId;
    private String name;
    private double price;
    private double amount;

    public PromotionCommodity(String commodityId, String name, double price, double amount) {
        this.commodityId = commodityId;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
    public PromotionCommodity() {}
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCommodityId() {
        return commodityId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }


}
