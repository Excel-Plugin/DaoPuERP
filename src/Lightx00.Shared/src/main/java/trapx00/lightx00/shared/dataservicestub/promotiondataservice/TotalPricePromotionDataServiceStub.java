package trapx00.lightx00.shared.dataservicestub.promotiondataservice;

import trapx00.lightx00.shared.dataservice.promotiondataservice.TotalPricePromotionDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.po.manager.promotion.TotalPricePromotionPo;
import trapx00.lightx00.shared.queryvo.promotion.TotalPricePromotionQueryVo;

import java.util.Date;

public class TotalPricePromotionDataServiceStub implements TotalPricePromotionDataService {
    /**
     * Submit a completed TotalPricePromotionPo.
     * @param promotion the TotalPricePromotionPo to be submitted
     * @return whether the operation is done successfully
     */
    public ResultMessage submit(TotalPricePromotionPo promotion) {
        return ResultMessage.Success;
    }

    /**
     * Filter TotalPricePromotionPo.
     * @param query the filter conditions
     * @return array of TotalPricePromotionPo which match the conditions
     */
    public TotalPricePromotionPo[] queryPromotion(TotalPricePromotionQueryVo query) {
        return new TotalPricePromotionPo[]{new TotalPricePromotionPo("ZJCXCL-20171112-00001",new Date(),new Date(),PromotionState.Waiting,200,1000,null)};
    }

    /**
     * Delete a useless TotalPricePromotionPo.
     * @param id id of the TotalPricePromotionPo to be deleted
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
        return "ZJCXCL-20171112-00001";
    }
}
