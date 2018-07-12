package trapx00.lightx00.client.bl.promotionbl;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.blservice.promotionblservice.ClientPromotionBlService;
import trapx00.lightx00.client.datafactory.promotiondataservicefactory.ClientPromotionDataServiceFactory;
import trapx00.lightx00.client.vo.manager.promotion.ClientPromotionVo;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ClientPromotionDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.ClientPromotionPo;
import trapx00.lightx00.shared.queryvo.promotion.ClientPromotionQueryVo;

import java.util.List;

public class ClientPromotionBlController implements ClientPromotionBlService,DraftDeleteService,PromotionPoVoConverter<ClientPromotionPo,ClientPromotionVo> {
    private ClientPromotionDataService dataService = ClientPromotionDataServiceFactory.getService();

    private CommonPromotionBlController<ClientPromotionVo, ClientPromotionPo, ClientPromotionQueryVo> commonPromotionBlController
            = new CommonPromotionBlController<>(dataService, "客户促销策略", this);


    public ClientPromotionVo fromPoToVo(ClientPromotionPo po) {
        return new ClientPromotionVo(po.getId(), po.getStartDate(), po.getEndDate(), po.getState(), po.getClientLevel(),po.getCouponPrice(),po.getPromotionCommodities(),po.getSalePrice());

    }

    public ClientPromotionPo fromVoToPo(ClientPromotionVo vo) {
        return new ClientPromotionPo(vo.getId(), vo.getStartDate(), vo.getEndDate(), vo.getState(), vo.getClientLevel(), vo.getCouponPrice(),vo.getPromotionCommodities(),vo.getSalePrice());
    }

    /**
     * Submit a ClientPromotionVo.
     * @param promotion the ClientPromotionVo to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(ClientPromotionVo promotion) {
        return commonPromotionBlController.submit(promotion);
    }

    /**
     * Save a half-completed ClientPromotionVo as a draft.
     * @param promotion the ClientPromotionVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(ClientPromotionVo promotion) {
        return commonPromotionBlController.saveAsDraft(promotion);
    }

    /**
     * Filter ClientPromotionVo.
     * @param query the filter conditions
     * @return array of ClientPromotionVo which match the conditions
     */
    @Override
    public ClientPromotionVo[] queryPromotion(ClientPromotionQueryVo query) {
        List<ClientPromotionVo> result = commonPromotionBlController.queryPromotion(query);
        return result.toArray(new ClientPromotionVo[result.size()]);
    }

    /**
     * Delete a overdue or needless ClientPromotionVo.
     * @param id id of the ClientPromotionVo to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(String id) {
        return commonPromotionBlController.delete(id);
    }

    /**
     * Get id for the next ClientPromotionVo.
     * @return id for the next ClientPromotionVo
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
