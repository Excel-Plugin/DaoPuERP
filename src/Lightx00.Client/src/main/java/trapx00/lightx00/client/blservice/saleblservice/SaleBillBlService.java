package trapx00.lightx00.client.blservice.saleblservice;

import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.SaleBillQueryVo;

public interface SaleBillBlService {

    /**
     * submit a saleBill
     *
     * @param saleBill to be submitted
     * @return whether the operation is done successfully
     */
    ResultMessage submit(SaleBillVo saleBill);//提交销售单

    /**
     * saves a half-completed saleBill as draft.
     *
     * @param saleBill SaleBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    ResultMessage saveAsDraft(SaleBillVo saleBill);//保存销售单为草稿

    /**
     * Gets the id for the next saleBill
     *
     * @return id for the next saleBill
     */
    String getId();//获得新单据的ID

    /**
     * query for SaleBills
     *
     * @param query function
     * @return array of SaleBillVo
     */
    SaleBillVo[] querySaleBill(SaleBillQueryVo query);

    /**
     * query for sale promotion
     *
     * @param saleBillVo current sale bill
     * @return promotions
     */
    PromotionVoBase[] queryPromotion(SaleBillVo saleBillVo);
}