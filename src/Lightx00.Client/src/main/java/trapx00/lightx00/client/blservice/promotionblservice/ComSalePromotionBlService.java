package trapx00.lightx00.client.blservice.promotionblservice;

import trapx00.lightx00.client.vo.manager.promotion.ComSalePromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.promotion.ComSalePromotionQueryVo;

public interface ComSalePromotionBlService {
    /**
     * Submit a ComSalePromotionVo.
     * @param promotion the ComSalePromotionVo to be submitted
     * @return whether the operation is done successfully
     */
    ResultMessage submit(ComSalePromotionVo promotion);

    /**
     * Save a half-completed ComSalePromotionVo as a draft.
     * @param promotion the ComSalePromotionVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    ResultMessage saveAsDraft(ComSalePromotionVo promotion);

    /**
     * Filter ComSalePromotionVo.
     * @param query the filter conditions
     * @return array of ComSalePromotionVo which match the conditions
     */
    ComSalePromotionVo[] queryPromotion(ComSalePromotionQueryVo query);

    /**
     * Delete a overdue or needless ComSalePromotionVo.
     * @param id id of the ComSalePromotionVo to be deleted
     * @return whether the operation is done successfully
     */
    ResultMessage delete(String id);

    /**
     * Get id for the next promotion.
     * @return id for the next promotion
     */
    String getId();
}
