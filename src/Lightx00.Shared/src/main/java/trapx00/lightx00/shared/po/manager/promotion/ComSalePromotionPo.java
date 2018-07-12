package trapx00.lightx00.shared.po.manager.promotion;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "ComSalePromotion")
public class ComSalePromotionPo extends PromotionPoBase {
    @DatabaseField
    private double onSalePrice;

    public ComSalePromotionPo(String id, Date startDate, Date endDate, PromotionState state,
                              PromotionCommodity[] promotionCommodities, double onSalePrice) {
        super(id, PromotionType.ComSalePromotion, startDate, endDate, state,promotionCommodities);
        this.onSalePrice = onSalePrice;
    }

    public ComSalePromotionPo() {}

    public double getOnSalePrice() {
        return onSalePrice;
    }

    public void setOnSalePrice(double onSalePrice) {
        this.onSalePrice = onSalePrice;
    }

}
