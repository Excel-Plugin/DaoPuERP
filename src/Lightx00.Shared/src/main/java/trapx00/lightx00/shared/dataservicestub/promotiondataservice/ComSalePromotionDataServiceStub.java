package trapx00.lightx00.shared.dataservicestub.promotiondataservice;

import trapx00.lightx00.shared.dataservice.promotiondataservice.ComSalePromotionDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.ComSalePromotionPo;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.queryvo.promotion.ComSalePromotionQueryVo;

import java.util.Date;

public class ComSalePromotionDataServiceStub implements ComSalePromotionDataService {
    /**
     * Submit a completed ComSalePromotionPo.
     * @param promotion the ComSalePromotionPo to be submitted
     * @return whether the operation is done successfully
     */
    public ResultMessage submit(ComSalePromotionPo promotion) {
        return ResultMessage.Success;
    }

    /**
     * Filter ComSalePromotionPo.
     * @param query the filter conditions
     * @return array of ComSalePromotionPo which match the conditions
     */
    public ComSalePromotionPo[] queryPromotion(ComSalePromotionQueryVo query) {
        return new ComSalePromotionPo[]{new ComSalePromotionPo("SPJJCXCL-20171112-00001",new Date(),new Date(),PromotionState.Waiting,null,0)};
    }


    /**
     * Delete a useless ComSalePromotionPo.
     * @param id id of the ComSalePromotionPo to be deleted
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
        return "SPJJCXCL-20171112-00001";
    }
}
