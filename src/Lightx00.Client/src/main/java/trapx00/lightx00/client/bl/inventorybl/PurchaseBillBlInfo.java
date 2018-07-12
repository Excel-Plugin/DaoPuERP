package trapx00.lightx00.client.bl.inventorybl;

import trapx00.lightx00.client.vo.salestaff.PurchaseBillVo;
import trapx00.lightx00.client.vo.salestaff.PurchaseRefundBillVo;
import trapx00.lightx00.shared.queryvo.PurchaseBillQueryVo;
import trapx00.lightx00.shared.queryvo.PurchaseRefundBillQueryVo;

public interface PurchaseBillBlInfo {

    /**
     * query for PurchaseBills
     * @param query function
     * @return array of PurchaseBillVo
     */
    PurchaseBillVo[] queryPurchaseBillVo(PurchaseBillQueryVo query);

    /**
     * query for PurchaseRefundBills
     * @param query function
     * @return array of PurchaseRefundBillVo
     */
    PurchaseRefundBillVo[] queryPurchaseRefundBillVo(PurchaseRefundBillQueryVo query);
}
