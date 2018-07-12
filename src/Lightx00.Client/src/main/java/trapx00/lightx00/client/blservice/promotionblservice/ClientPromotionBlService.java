package trapx00.lightx00.client.blservice.promotionblservice;

import trapx00.lightx00.client.vo.manager.promotion.ClientPromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.promotion.ClientPromotionQueryVo;

public interface ClientPromotionBlService {
    /**
     * Submit a ClientPromotionVo.
     * @param promotion the ClientPromotionVo to be submitted
     * @return whether the operation is done successfully
     */
    ResultMessage submit(ClientPromotionVo promotion);

    /**
     * Save a half-completed ClientPromotionVo as a draft.
     * @param promotion the ClientPromotionVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    ResultMessage saveAsDraft(ClientPromotionVo promotion);

    /**
     * Filter ClientPromotionVo.
     * @param query the filter conditions
     * @return array of ClientPromotionVo which match the conditions
     */
    ClientPromotionVo[] queryPromotion(ClientPromotionQueryVo query);

    /**
     * Delete a overdue or needless ClientPromotionVo.
     * @param id id of the ClientPromotionVo to be deleted
     * @return whether the operation is done successfully
     */
    ResultMessage delete(String id);

    /**
     * Get id for the next promotion.
     * @return id for the next promotion
     */
    String getId();
}
