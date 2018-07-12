package trapx00.lightx00.client.blservice.inventoryblservice;

import trapx00.lightx00.client.vo.salestaff.PurchaseBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.PurchaseBillQueryVo;

public interface PurchaseBillBlService {

    /**
     * submit a purchaseBill
     * @param purchaseBill to be submitted
     * @return whether the operation is done successfully
     */
    ResultMessage submit(PurchaseBillVo purchaseBill);//提交进货单

    /**
     * saves a half-completed purchaseBill as draft.
     *
     * @param purchaseBill PurchaseBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    ResultMessage saveAsDraft(PurchaseBillVo purchaseBill);//保存进货单为草稿

    /**
     * Gets the id for the next purchaseBill
     *
     * @return id for the next purchaseBill
     */
    String getId();//获得新的ID

    /**
     * query for PurchaseBills
     *
     * @param query function
     * @return array of PurchaseBillVo
     */
    PurchaseBillVo[] queryPurchaseBillVo(PurchaseBillQueryVo query);
}