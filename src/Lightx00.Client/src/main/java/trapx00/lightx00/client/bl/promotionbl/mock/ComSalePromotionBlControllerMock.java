package trapx00.lightx00.client.bl.promotionbl.mock;

import trapx00.lightx00.client.bl.promotionbl.ComSalePromotionBlController;
import trapx00.lightx00.client.vo.manager.promotion.ComSalePromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.queryvo.promotion.ComSalePromotionQueryVo;

import java.util.Date;

public class ComSalePromotionBlControllerMock extends ComSalePromotionBlController {
    /**
     * Submit a ComSalePromotionVo.
     * @param promotion the ComSalePromotionVo to be submitted
     * @return whether the operation is done successfully
     */
    public ResultMessage submit(ComSalePromotionVo promotion) {
        return ResultMessage.Success;
    }

    /**
     * Save a half-completed ComSalePromotionVo as a draft.
     * @param promotion the ComSalePromotionVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    public ResultMessage saveAsDraft(ComSalePromotionVo promotion) {
        return ResultMessage.Success;
    }

    /**
     * Filter ComSalePromotionVo.
     * @param query the filter conditions
     * @return array of ComSalePromotionVo which match the conditions
     */
    public ComSalePromotionVo[] queryPromotion(ComSalePromotionQueryVo query) {
        return new ComSalePromotionVo[]{new ComSalePromotionVo("0001",new Date(),new Date(), PromotionState.Waiting,new PromotionCommodity[0],200)
        };
    }

    /**
     * Delete a overdue or needless ComSalePromotionVo.
     * @param promotion the ComSalePromotionVo to be deleted
     * @return whether the operation is done successfully
     */
    public ResultMessage delete(ComSalePromotionVo promotion) {
        return ResultMessage.Success;
    }

    /**
     * Get id for the next ComSalePromotionVo.
     * @return id for the next ComSalePromotionVo
     */
    public String getId() {
        return "0001";
    }

    /**
     * Delete a draft.
     * @param id id of the draft to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage deleteDraft(String id) {
        return ResultMessage.Success;
    }

}
