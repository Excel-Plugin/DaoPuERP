package trapx00.lightx00.client.blservice.promotionblservice;

import trapx00.lightx00.client.vo.manager.promotion.TotalPricePromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.promotion.TotalPricePromotionQueryVo;

public interface TotalPricePromotionBlService {
    /**
     * Submit a TotalPricePromotionVo.
     * @param promotion the TotalPricePromotionVo to be submitted
     * @return whether the operation is done successfully
     */
    ResultMessage submit(TotalPricePromotionVo promotion);

    /**
     * Save a half-completed TotalPricePromotionVo as a draft.
     * @param promotion the TotalPricePromotionVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    ResultMessage saveAsDraft(TotalPricePromotionVo promotion);

    /**
     * Filter TotalPricePromotionVo.
     * @param query the filter conditions
     * @return array of TotalPricePromotionVo which match the conditions
     */
    TotalPricePromotionVo[] queryPromotion(TotalPricePromotionQueryVo query);

    /**
     * Delete a overdue or needless TotalPricePromotionVo.
     * @param id id of the TotalPricePromotionVo to be deleted
     * @return whether the operation is done successfully
     */
    ResultMessage delete(String id);

    /**
     * Get id for the next promotion.
     * @return id for the next promotion
     */
    String getId();
}
