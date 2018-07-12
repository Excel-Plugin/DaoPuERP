package trapx00.lightx00.shared.po.manager.promotion;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "TotalPricePromotion")
public class TotalPricePromotionPo extends PromotionPoBase {
    @DatabaseField
    private double couponPrice;
    @DatabaseField
    private double totalPrice;

    public TotalPricePromotionPo(String id, Date startDate, Date endDate, PromotionState state, double couponPrice, double totalPrice,PromotionCommodity[] promotionCommodities) {
        super(id, PromotionType.TotalPricePromotion, startDate, endDate, state, promotionCommodities);
        this.couponPrice = couponPrice;
        this.totalPrice = totalPrice;
    }

    public TotalPricePromotionPo() {}

    public void setCouponPrice(double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getCouponPrice() {
        return couponPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}

