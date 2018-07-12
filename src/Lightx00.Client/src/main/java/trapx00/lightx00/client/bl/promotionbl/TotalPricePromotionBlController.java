package trapx00.lightx00.client.bl.promotionbl;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.blservice.promotionblservice.TotalPricePromotionBlService;
import trapx00.lightx00.client.datafactory.promotiondataservicefactory.TotalPricePromotionDataServiceFactory;
import trapx00.lightx00.client.vo.manager.promotion.TotalPricePromotionVo;
import trapx00.lightx00.shared.dataservice.promotiondataservice.TotalPricePromotionDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.TotalPricePromotionPo;
import trapx00.lightx00.shared.queryvo.promotion.TotalPricePromotionQueryVo;

import java.util.List;

public class TotalPricePromotionBlController implements TotalPricePromotionBlService, DraftDeleteService, PromotionPoVoConverter<TotalPricePromotionPo,TotalPricePromotionVo> {
    private TotalPricePromotionDataService dataService = TotalPricePromotionDataServiceFactory.getService();

    private CommonPromotionBlController<TotalPricePromotionVo, TotalPricePromotionPo, TotalPricePromotionQueryVo> commonPromotionBlController
            = new CommonPromotionBlController<>(dataService, "客户促销策略", this);


    public TotalPricePromotionVo fromPoToVo(TotalPricePromotionPo po) {
        return new TotalPricePromotionVo(po.getId(), po.getStartDate(), po.getEndDate(), po.getState(), po.getCouponPrice(),po.getTotalPrice(),po.getPromotionCommodities());

    }

    public TotalPricePromotionPo fromVoToPo(TotalPricePromotionVo vo) {
        return new TotalPricePromotionPo(vo.getId(), vo.getStartDate(), vo.getEndDate(), vo.getState(), vo.getCouponPrice(), vo.getTotalPrice(),vo.getPromotionCommodities());
    }

    /**
     * Submit a TotalPriceGiftVo.
     * @param promotion the TotalPriceGiftVo to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(TotalPricePromotionVo promotion) {
        return commonPromotionBlController.submit(promotion);
    }

    /**
     * Save a half-completed TotalPriceGiftVo as a draft.
     * @param promotion the TotalPriceGiftVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(TotalPricePromotionVo promotion) {
        return commonPromotionBlController.saveAsDraft(promotion);
    }

    /**
     * Filter TotalPricePromotionVo.
     * @param query the filter conditions
     * @return array of TotalPricePromotionVo which match the conditions
     */
    @Override
    public TotalPricePromotionVo[] queryPromotion(TotalPricePromotionQueryVo query) {
        List<TotalPricePromotionVo> result = commonPromotionBlController.queryPromotion(query);
        return result.toArray(new TotalPricePromotionVo[result.size()]);
    }

    /**
     * Delete a overdue or needless TotalPriceGiftVo.
     * @param id id of the TotalPriceGiftVo to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(String id) {
        return commonPromotionBlController.deleteDraft(id);
    }

    /**
     * Get id for the next TotalPriceGiftVo.
     * @return id for the next TotalPriceGiftVo
     */
    @Override
    public String getId() {
        return commonPromotionBlController.getId();
    }

    /**
     * Delete a draft.
     * @param id id of the draft to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage deleteDraft(String id) {
        return commonPromotionBlController.deleteDraft(id);
    }
}
