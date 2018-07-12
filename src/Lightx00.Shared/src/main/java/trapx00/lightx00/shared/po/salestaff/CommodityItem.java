package trapx00.lightx00.shared.po.salestaff;

import java.io.Serializable;

public class CommodityItem implements Serializable {

    private String commodityId;
    private String name;
    private String type;
    private double number;
    private double price;
    private double total;
    private String comment;

    public CommodityItem(String commodityId, String name, String type, double number, double price, double total, String comment) {
        this.commodityId = commodityId;
        this.name = name;
        this.type = type;
        this.number = number;
        this.price = price;
        this.total = total;
        this.comment = comment;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
