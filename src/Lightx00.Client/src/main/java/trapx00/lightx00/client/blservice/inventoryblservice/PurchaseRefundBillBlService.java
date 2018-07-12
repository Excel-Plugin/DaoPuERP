package trapx00.lightx00.client.blservice.inventoryblservice;

import trapx00.lightx00.client.vo.salestaff.PurchaseRefundBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.PurchaseRefundBillQueryVo;

public interface PurchaseRefundBillBlService {

    /**
     * submit a purchaseRefundBill
     * @param purchaseRefundBill to be submitted
     * @return whether the operation is done successfully
     */
    ResultMessage submit(PurchaseRefundBillVo purchaseRefundBill);//提交进货退货单

    /**
     * saves a half-completed purchaseRefundBill as draft.
     *
     * @param purchaseRefundBill PurchaseRefundBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    ResultMessage saveAsDraft(PurchaseRefundBillVo purchaseRefundBill);//保存进货退货单为草稿

    /**
     * Gets the id for the next purchaseRefundBill
     *
     * @return id for the next purchaseRefundBill
     */
    String getId();//获得新的ID

    /**
     * query for PurchaseRefundBills
     *
     * @param query function
     * @return array of PurchaseRefundBillVo
     */
    PurchaseRefundBillVo[] queryPurchaseRefundBillVo(PurchaseRefundBillQueryVo query);
}