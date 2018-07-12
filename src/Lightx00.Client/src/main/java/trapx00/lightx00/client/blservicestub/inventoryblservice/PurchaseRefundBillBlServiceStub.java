package trapx00.lightx00.client.blservicestub.inventoryblservice;

import trapx00.lightx00.client.blservice.inventoryblservice.PurchaseRefundBillBlService;
import trapx00.lightx00.client.vo.salestaff.PurchaseRefundBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.PurchaseRefundBillQueryVo;

public class PurchaseRefundBillBlServiceStub implements PurchaseRefundBillBlService {
    @Override
    public ResultMessage submit(PurchaseRefundBillVo purchaseRefundBill) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage saveAsDraft(PurchaseRefundBillVo purchaseRefundBill) {
        return ResultMessage.Success;
    }

    /**
     * Gets the id for the next purchaseRefundBill
     *
     * @return id for the next purchaseRefundBill
     */
    @Override
    public String getId() {
        return null;
    }

    /**
     * query for PurchaseRefundBills
     *
     * @param query function
     * @return array of PurchaseRefundBillVo
     */
    @Override
    public PurchaseRefundBillVo[] queryPurchaseRefundBillVo(PurchaseRefundBillQueryVo query) {
        return new PurchaseRefundBillVo[0];
    }

}
