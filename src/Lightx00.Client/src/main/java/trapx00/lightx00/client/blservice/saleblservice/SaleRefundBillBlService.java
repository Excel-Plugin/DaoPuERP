package trapx00.lightx00.client.blservice.saleblservice;

import trapx00.lightx00.client.vo.salestaff.SaleRefundBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.SaleRefundBillQueryVo;

public interface SaleRefundBillBlService {

    /**
     * submit a saleRefundBill
     * @param saleRefundBill to be submitted
     * @return whether the operation is done successfully
     */
    ResultMessage submit(SaleRefundBillVo saleRefundBill);//提交销售退货单

    /**
     * saves a half-completed saleRefundBill as draft.
     *
     * @param saleRefundBill SaleRefundBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    ResultMessage saveAsDraft(SaleRefundBillVo saleRefundBill);//保存销售退货单为草稿

    /**
     * Gets the id for the next saleRefundBill
     *
     * @return id for the next saleRefundBill
     */
    String getId();//获得新的ID

    /**
     * query for SaleRefundBills
     *
     * @param query function
     * @return array of SaleRefundBillVo
     */
    SaleRefundBillVo[] querySaleRefundBill(SaleRefundBillQueryVo query);
}