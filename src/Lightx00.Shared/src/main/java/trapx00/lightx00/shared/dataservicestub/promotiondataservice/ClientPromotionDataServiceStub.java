package trapx00.lightx00.shared.dataservicestub.promotiondataservice;

import trapx00.lightx00.shared.dataservice.promotiondataservice.ClientPromotionDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.ClientPromotionPo;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.queryvo.promotion.ClientPromotionQueryVo;

import java.util.Date;

public class ClientPromotionDataServiceStub implements ClientPromotionDataService {
    /**
     * Submit a completed ClientPromotionPo.
     * @param promotion the ClientPromotionPo to be submitted
     * @return whether the operation is done successfully
     */
    public ResultMessage submit(ClientPromotionPo promotion) {
        return ResultMessage.Success;
    }

    /**
     * Filter ClientPromotionPo.
     * @param query the filter conditions
     * @return array of ClientPromotionPo which match the conditions
     */
    public ClientPromotionPo[] queryPromotion(ClientPromotionQueryVo query) {
        return new ClientPromotionPo[]{new ClientPromotionPo("KHCXCL-20171112-00001",new Date(),new Date(),PromotionState.Waiting,1,200,null,0)};
    }

    /**
     * Delete a useless ClientPromotionPo.
     * @param id id of the ClientPromotionPo to be deleted
     * @return whether the operation is done successfully
     */
    public ResultMessage delete(String id) {
        return ResultMessage.Success;
    }

    /**
     * Get id for the next promotion.
     *
     * @return id for the next promotion
     */
    public String getId() {
        return "KHCXCL-20171112-00001";
    }
}
