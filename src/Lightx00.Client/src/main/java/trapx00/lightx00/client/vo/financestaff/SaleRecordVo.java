package trapx00.lightx00.client.vo.financestaff;

import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;

import java.util.Date;

public class SaleRecordVo { //商品销售记录
    private Date date;
    private CommodityVo commodityVo;
    private double amount;
    private double unitPrice;
    private double total;

    public SaleRecordVo(Date date, CommodityVo commodityVo,  double amount, double unitPrice, double total) {
        this.date = date;
        this.commodityVo = commodityVo;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CommodityVo getCommodityName() {
        return commodityVo;
    }

    public void setCommodityName(CommodityVo commodityName) {
        this.commodityVo = commodityName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
