package trapx00.lightx00.client.bl.inventorybl;

import trapx00.lightx00.client.blservice.inventoryblservice.PurchaseBillBlService;
import trapx00.lightx00.client.blservice.inventoryblservice.PurchaseRefundBillBlService;
import trapx00.lightx00.client.vo.salestaff.PurchaseBillVo;
import trapx00.lightx00.client.vo.salestaff.PurchaseRefundBillVo;
import trapx00.lightx00.shared.queryvo.PurchaseBillQueryVo;
import trapx00.lightx00.shared.queryvo.PurchaseRefundBillQueryVo;

public class PurchaseBillBlInfoController implements PurchaseBillBlInfo {
    PurchaseBillBlService purchaseBillBlService = new PurchaseBillBlController();
    PurchaseRefundBillBlService purchaseRefundBillBlService = new PurchaseRefundBillBlController();

    /**
     * query for PurchaseBills
     *
     * @param query function
     * @return array of PurchaseBillVo
     */
    @Override
    public PurchaseBillVo[] queryPurchaseBillVo(PurchaseBillQueryVo query) {
        return purchaseBillBlService.queryPurchaseBillVo(query);
    }

    /**
     * query for PurchaseRefundBills
     *
     * @param query function
     * @return array of PurchaseRefundBillVo
     */
    @Override
    public PurchaseRefundBillVo[] queryPurchaseRefundBillVo(PurchaseRefundBillQueryVo query) {
        return purchaseRefundBillBlService.queryPurchaseRefundBillVo(query);
    }

}
