package trapx00.lightx00.shared.po.manager.promotion;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@DatabaseTable(tableName = "promotion")
public class PromotionPoBase  implements Serializable {
    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private PromotionType type;
    @DatabaseField
    private Date startDate;
    @DatabaseField
    private Date endDate;
    @DatabaseField
    private PromotionState state;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private PromotionCommodity[] promotionCommodities;

    public PromotionPoBase(String id, PromotionType type, Date startDate, Date endDate, PromotionState state,PromotionCommodity[] promotionCommodities) {
        this.id = id;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.promotionCommodities = promotionCommodities;
    }

    public PromotionPoBase() {}

    public Date getEndDate() {
        return endDate;
    }

    public String getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public PromotionType getPromotionType() {
        return type;
    }

    public PromotionState getPromotionState() {
        return state;
    }

    public PromotionCommodity[] getPromotionCommodities() {
        return promotionCommodities;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setState(PromotionState state) {
        this.state = state;
    }

    public void setPromotionCommodities(PromotionCommodity[] promotionCommodities) {
        this.promotionCommodities = promotionCommodities;
    }

    public PromotionState getState() {
        return state;
    }

    @Override
    public String toString() {
        return "PromotionPo{" +
                "promotionType =" + type +
                ", id='" + id + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", state=" + state +
                '}';
    }
}
