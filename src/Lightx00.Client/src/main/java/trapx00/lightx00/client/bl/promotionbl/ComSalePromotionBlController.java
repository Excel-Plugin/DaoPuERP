package trapx00.lightx00.client.bl.promotionbl;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.blservice.promotionblservice.ComSalePromotionBlService;
import trapx00.lightx00.client.datafactory.promotiondataservicefactory.ComSalePromotionDataServiceFactory;
import trapx00.lightx00.client.vo.manager.promotion.ComSalePromotionVo;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ComSalePromotionDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.ComSalePromotionPo;
import trapx00.lightx00.shared.queryvo.promotion.ComSalePromotionQueryVo;

import java.util.List;

public class ComSalePromotionBlController implements ComSalePromotionBlService, DraftDeleteService, PromotionPoVoConverter<ComSalePromotionPo,ComSalePromotionVo> {
    private ComSalePromotionDataService dataService = ComSalePromotionDataServiceFactory.getService();

    private CommonPromotionBlController<ComSalePromotionVo, ComSalePromotionPo, ComSalePromotionQueryVo> commonPromotionBlController
            = new CommonPromotionBlController<>(dataService, "组合降价促销策略", this);


    public ComSalePromotionVo fromPoToVo(ComSalePromotionPo po) {
        return new ComSalePromotionVo(po.getId(), po.getStartDate(), po.getEndDate(), po.getState(), po.getPromotionCommodities(),po.getOnSalePrice());

    }

    public ComSalePromotionPo fromVoToPo(ComSalePromotionVo vo) {
        return new ComSalePromotionPo(vo.getId(), vo.getStartDate(), vo.getEndDate(), vo.getState(), vo.getPromotionCommodities(), vo.getOnSalePrice());
    }

    /**
     * Submit a ComSalePromotionVo.
     * @param promotion the ComSalePromotionVo to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(ComSalePromotionVo promotion) {
        return commonPromotionBlController.submit(promotion);
    }

    /**
     * Save a half-completed ComSalePromotionVo as a draft.
     * @param promotion the ComSalePromotionVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(ComSalePromotionVo promotion) {
        return commonPromotionBlController.saveAsDraft(promotion);
    }

    /**
     * Filter ComSalePromotionVo.
     * @param query the filter conditions
     * @return array of ComSalePromotionVo which match the conditions
     */
    @Override
    public ComSalePromotionVo[] queryPromotion(ComSalePromotionQueryVo query) {
        List<ComSalePromotionVo> result = commonPromotionBlController.queryPromotion(query);
        return result.toArray(new ComSalePromotionVo[result.size()]);
    }

    /**
     * Delete a overdue or needless ComSalePromotionVo.
     * @param id id of the ComSalePromotionVo to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(String id) {
        return commonPromotionBlController.delete(id);
    }

    /**
     * Get id for the next ComSalePromotionVo.
     * @return id for the next ComSalePromotionVo
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
