package trapx00.lightx00.client.presentation.financeui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.client.vo.financestaff.SaleRecordVo;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;

import java.util.Date;

public class SaleRecordModel extends RecursiveTreeObject<SaleRecordModel> {
    private ObjectProperty<Date> date;
    private ObjectProperty<CommodityVo> commodityVo;
    private DoubleProperty amount;
    private DoubleProperty unitPrice;
    private DoubleProperty total;



    public SaleRecordModel(SaleRecordVo saleRecord) {
        this.date = new SimpleObjectProperty<>(saleRecord.getDate());
        this.commodityVo = new SimpleObjectProperty<>(saleRecord.getCommodityName());
        this.amount = new SimpleDoubleProperty(saleRecord.getAmount());
        this.unitPrice = new SimpleDoubleProperty(saleRecord.getUnitPrice());
        this.total = new SimpleDoubleProperty(saleRecord.getTotal());
    }

    public SaleRecordVo toSaleRecord() {
        return new SaleRecordVo(date.get(), commodityVo.get(), amount.get(), unitPrice.get(), total.get());
    }

    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public CommodityVo getCommodityVo() {
        return commodityVo.get();
    }

    public ObjectProperty<CommodityVo> commodityVoProperty() {
        return commodityVo;
    }

    public void setCommodityVo(CommodityVo commodityVo) {
        this.commodityVo.set(commodityVo);
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public double getUnitPrice() {
        return unitPrice.get();
    }

    public DoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    public double getTotal() {
        return total.get();
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public void setTotal(double total) {
        this.total.set(total);
    }
}
