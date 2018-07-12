package trapx00.lightx00.shared.po.manager;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@DatabaseTable(tableName = "UsedCoupon")
public class CouponPo implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    double couponPrice;
    @DatabaseField
    Date date;

    public CouponPo(double couponPrice, Date date) {
        this.couponPrice = couponPrice;
        this.date = date;
    }

    public CouponPo(int id, double couponPrice, Date date) {
        this.id = id;
        this.couponPrice = couponPrice;
        this.date = date;
    }

    public CouponPo() {}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
