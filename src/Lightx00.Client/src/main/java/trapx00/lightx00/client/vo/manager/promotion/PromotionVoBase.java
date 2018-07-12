package trapx00.lightx00.client.vo.manager.promotion;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.presentation.helpui.ContentDisplayUi;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;
import trapx00.lightx00.client.presentation.promotionui.detail.PromotionDetailUi;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.po.manager.promotion.PromotionType;

import java.io.Serializable;
import java.util.Date;

public abstract class PromotionVoBase implements Draftable, Serializable {
    private String id;
    private PromotionType type;
    private Date startDate;
    private Date endDate;
    private PromotionState state;
    private PromotionCommodity[] promotionCommodities;

    public PromotionVoBase(String id, PromotionType type, Date startDate, Date endDate,
                           PromotionState state,PromotionCommodity[] promotionCommodities) {
        this.id = id;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.promotionCommodities = promotionCommodities;
        if(state.equals(PromotionState.Draft) || state.equals(PromotionState.Abandoned)) {
        }
        else {
            Date currentDate = new Date();
            if (startDate.getTime() <= currentDate.getTime() && endDate.getTime() > currentDate.getTime()) {
                this.state = PromotionState.Active;
            } else if (startDate.getTime() > currentDate.getTime()) {
                this.state = PromotionState.Waiting;
            } else if (endDate.getTime() <= currentDate.getTime()) {
                this.state = PromotionState.Overdue;
            }
        }
    }

    public PromotionState getState() {
        if(state.equals(PromotionState.Draft) || state.equals(PromotionState.Abandoned)) {
        }
        else {
            Date currentDate = new Date();
            if (startDate.getTime() <= currentDate.getTime() && endDate.getTime() > currentDate.getTime()) {
                this.state = PromotionState.Active;
            } else if (startDate.getTime() > currentDate.getTime()) {
                this.state = PromotionState.Waiting;
            } else if (endDate.getTime() <= currentDate.getTime()) {
                this.state = PromotionState.Overdue;
            }
        }
        return state;
    }

    public PromotionType getType() {
        return type;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public PromotionType getPromotionType() {return type;}

    public PromotionState getPromotionState() {return state;}

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

    /**
     * Gets DeleteService corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DeleteService
     */
    @Override
    public abstract DraftDeleteService deleteService();

    /**
     * Gets the DraftContinueWritableUiController service corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DraftContinueWritableUiController
     */
    @Override
    public abstract DraftContinueWritableUiController continueWritableUi();

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

    public abstract PromotionDetailUi promotionDetailUi();

    /**
     * 显示详细信息UI
     *
     * @return 显示详细信息UI
     */
    @Override
    public ContentDisplayUi contentDisplayUi() {
        return promotionDetailUi();
    }

}
