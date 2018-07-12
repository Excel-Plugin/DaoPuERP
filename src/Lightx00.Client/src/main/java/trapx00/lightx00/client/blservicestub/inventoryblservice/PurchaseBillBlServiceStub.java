package trapx00.lightx00.client.blservicestub.inventoryblservice;

import trapx00.lightx00.client.blservice.inventoryblservice.PurchaseBillBlService;
import trapx00.lightx00.client.vo.salestaff.PurchaseBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.PurchaseBillQueryVo;

public class PurchaseBillBlServiceStub implements PurchaseBillBlService {
    @Override
    public ResultMessage submit(PurchaseBillVo purchaseBill) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage saveAsDraft(PurchaseBillVo purchaseBill) {
        return ResultMessage.Success;
    }

    /**
     * Gets the id for the next purchaseBill
     *
     * @return id for the next purchaseBill
     */
    @Override
    public String getId() {
        return null;
    }

    /**
     * query for PurchaseBills
     *
     * @param query function
     * @return array of PurchaseBillVo
     */
    @Override
    public PurchaseBillVo[] queryPurchaseBillVo(PurchaseBillQueryVo query) {
        return new PurchaseBillVo[0];
    }
}
