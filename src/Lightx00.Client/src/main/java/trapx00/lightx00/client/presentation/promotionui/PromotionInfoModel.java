package trapx00.lightx00.client.presentation.promotionui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.po.manager.promotion.PromotionType;

import java.util.Date;

public class PromotionInfoModel extends RecursiveTreeObject<PromotionInfoModel> {
    private StringProperty id;
    private ObjectProperty<Date> startDate;
    private ObjectProperty<Date> endDate;
    private ObjectProperty<PromotionType> type;
    private ObjectProperty<PromotionState> state;
    private ObjectProperty<PromotionVoBase> promotion;

    public PromotionInfoModel(PromotionVoBase promotionVo) {
        this.id = new SimpleStringProperty(promotionVo.getId());
        this.startDate = new SimpleObjectProperty<>(promotionVo.getStartDate());
        this.endDate = new SimpleObjectProperty<>(promotionVo.getEndDate());
        this.type = new SimpleObjectProperty<>(promotionVo.getType());
        this.state = new SimpleObjectProperty<>(promotionVo.getState());
        this.promotion = new SimpleObjectProperty<>(promotionVo);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public Date getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<Date> startDateProperty() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate.set(startDate);
    }

    public PromotionType getType() {
        return type.get();
    }

    public ObjectProperty<PromotionType> typeProperty() {
        return type;
    }

    public void setType(PromotionType type) {
        this.type.set(type);
    }

    public Date getEndDate() {
        return endDate.get();
    }

    public ObjectProperty<Date> endDateProperty() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate.set(endDate);
    }

    public void setPromotion(PromotionVoBase promotion) {
        this.promotion.set(promotion);
    }

    public PromotionVoBase getPromotion() {
        return promotion.get();
    }

    public ObjectProperty<PromotionVoBase> promotionProperty() {
        return promotion;
    }

    public void setState(PromotionState state) {
        this.state.set(state);
    }

    public PromotionState getState() {
        return state.get();
    }

    public ObjectProperty<PromotionState> stateProperty() {
        return state;
    }


}
