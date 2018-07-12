package trapx00.lightx00.client.bl.promotionbl;

import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;

public interface PromotionInfo {
    /**
     * query for PromotionVo which current sale can use
     * @param saleBill sale behaviour
     * @return array of PromotionVo which current sale can use
     */
    PromotionVoBase[] queryPromotion(SaleBillVo saleBill);

}
