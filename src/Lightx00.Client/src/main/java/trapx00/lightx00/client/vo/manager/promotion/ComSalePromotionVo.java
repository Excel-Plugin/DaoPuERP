package trapx00.lightx00.client.vo.manager.promotion;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.promotionbl.factory.ComSalePromotionBlFactory;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;
import trapx00.lightx00.client.presentation.promotionui.detail.ComSalePromotionDetailUi;
import trapx00.lightx00.client.presentation.promotionui.detail.ComSalePromotionUiController;
import trapx00.lightx00.client.presentation.promotionui.detail.PromotionDetailUi;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.po.manager.promotion.PromotionType;

import java.util.Date;

public class ComSalePromotionVo extends PromotionVoBase {
    private double onSalePrice;

    public ComSalePromotionVo(String id, Date startDate, Date endDate, PromotionState state,
                              PromotionCommodity[] promotionCommodities, double onSalePrice) {
        super(id, PromotionType.ComSalePromotion, startDate, endDate, state, promotionCommodities);
        this.onSalePrice = onSalePrice;
    }

    public double getOnSalePrice() {
        return onSalePrice;
    }

    public void setOnSalePrice(double onSalePrice) {
        this.onSalePrice = onSalePrice;
    }

    /**
     * Gets DeleteService corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DeleteService
     */
    @Override
    public DraftDeleteService deleteService() {
        return ComSalePromotionBlFactory.getDraftDeleteService();
    }

    /**
     * Gets the DraftContinueWritableUiController service corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DraftContinueWritableUiController
     */
    @Override
    public DraftContinueWritableUiController continueWritableUi() {
        return new ComSalePromotionUiController();
    }

    @Override
    public PromotionDetailUi promotionDetailUi() {
        return new ComSalePromotionDetailUi();
    }
}
