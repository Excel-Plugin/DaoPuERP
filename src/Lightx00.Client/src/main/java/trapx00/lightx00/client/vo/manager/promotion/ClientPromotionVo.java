package trapx00.lightx00.client.vo.manager.promotion;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.promotionbl.factory.ClientPromotionBlFactory;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;
import trapx00.lightx00.client.presentation.promotionui.detail.ClientPromotionDetailUi;
import trapx00.lightx00.client.presentation.promotionui.detail.ClientPromotionUiController;
import trapx00.lightx00.client.presentation.promotionui.detail.PromotionDetailUi;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.po.manager.promotion.PromotionType;

import java.util.Date;

public class ClientPromotionVo extends PromotionVoBase {
    private int clientLevel;
    private double couponPrice;
    private double salePrice;

    public ClientPromotionVo(String id, Date startDate, Date endDate, PromotionState state, int clientLevel, double couponPrice,PromotionCommodity[] promotionCommodities,double salePrice) {
        super(id, PromotionType.ClientPromotion, startDate, endDate, state,promotionCommodities);
        this.clientLevel = clientLevel;
        this.couponPrice = couponPrice;
        this.salePrice = salePrice;
    }

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

    /**
     * Gets DeleteService corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DeleteService
     */
    @Override
    public DraftDeleteService deleteService() {
        return ClientPromotionBlFactory.getDraftDeleteService();
    }

    /**
     * Gets the DraftContinueWritableUiController service corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DraftContinueWritableUiController
     */
    @Override
    public DraftContinueWritableUiController continueWritableUi() {
        return new ClientPromotionUiController();
    }

    @Override
    public PromotionDetailUi promotionDetailUi() {
        return new ClientPromotionDetailUi();
    }
}
