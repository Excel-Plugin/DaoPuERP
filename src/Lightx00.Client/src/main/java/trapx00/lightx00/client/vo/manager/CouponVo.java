package trapx00.lightx00.client.vo.manager;

import trapx00.lightx00.shared.po.manager.CouponPo;

import java.util.Date;

public class CouponVo {
    private int id;
    private double couponPrice;
    private Date date;

    public CouponVo(double couponPrice, Date date) {
        this.couponPrice = couponPrice;
        this.date = date;
    }

    public CouponVo(int id, double couponPrice, Date date) {
        this.id = id;
        this.couponPrice = couponPrice;
        this.date = date;
    }

    public CouponPo toPo() {
        CouponPo couponPo = new CouponPo(couponPrice, date);
        couponPo.setId(id);
        return couponPo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
