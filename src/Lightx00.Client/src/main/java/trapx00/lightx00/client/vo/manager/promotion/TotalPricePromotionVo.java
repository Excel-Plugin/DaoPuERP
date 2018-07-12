package trapx00.lightx00.client.vo.manager.promotion;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.promotionbl.factory.TotalPricePromotionBlFactory;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;
import trapx00.lightx00.client.presentation.promotionui.detail.PromotionDetailUi;
import trapx00.lightx00.client.presentation.promotionui.detail.TotalPricePromotionDetailUi;
import trapx00.lightx00.client.presentation.promotionui.detail.TotalPricePromotionUiController;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.po.manager.promotion.PromotionType;

import java.util.Date;

public class TotalPricePromotionVo extends PromotionVoBase {
    private double couponPrice;
    private double totalPrice;

    public TotalPricePromotionVo(String id, Date startDate, Date endDate,
                                 PromotionState state, double couponPrice, double totalPrice, PromotionCommodity[] promotionCommodities) {
        super(id, PromotionType.TotalPricePromotion, startDate, endDate, state, promotionCommodities);
        this.couponPrice = couponPrice;
        this.totalPrice = totalPrice;
    }

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

    /**
     * Gets DeleteService corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DeleteService
     */
    @Override
    public DraftDeleteService deleteService() {
        return TotalPricePromotionBlFactory.getDraftDeleteService();
    }

    /**
     * Gets the DraftContinueWritableUiController service corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DraftContinueWritableUiController
     */
    @Override
    public DraftContinueWritableUiController continueWritableUi() {
        return new TotalPricePromotionUiController();
    }

    @Override
    public PromotionDetailUi promotionDetailUi() {
        return new TotalPricePromotionDetailUi();
    }
}
