package trapx00.lightx00.shared.po.manager.promotion;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "ClientPromotion")
public class ClientPromotionPo extends PromotionPoBase {
    @DatabaseField
    private int clientLevel;
    @DatabaseField
    private double couponPrice;
    @DatabaseField
    private double salePrice;

    public ClientPromotionPo(String id, Date startDate, Date endDate, PromotionState state, int clientLevel, double couponPrice,PromotionCommodity[] promotionCommodities,double salePrice) {
        super(id, PromotionType.ClientPromotion, startDate, endDate, state,promotionCommodities);
        this.clientLevel = clientLevel;
        this.couponPrice = couponPrice;
        this.salePrice = salePrice;
    }

    public ClientPromotionPo() {}

    public int getClientLevel() {
        return clientLevel;
    }

    public void setClientLevel(int clientLevel) {
        this.clientLevel = clientLevel;
    }

    public double getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice() {
        this.salePrice = salePrice;
    }
}
