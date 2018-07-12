package trapx00.lightx00.client.blservicestub.inventoryblservice;

import trapx00.lightx00.client.blservice.inventoryblservice.InventoryGiftBlService;
import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.queryvo.InventoryGiftQueryVo;

import java.util.Date;

public class InventoryGiftBlServiceStub implements InventoryGiftBlService {


    @Override
    public ResultMessage sumbit(InventoryGiftVo inventoryGiftVo) {
        Date date=new Date();
        PromotionCommodity pCommodity=new PromotionCommodity();
        PromotionCommodity[] input={null};
        return ResultMessage.Success;
    }

    @Override
    public String getId() {
        return "123";
    }

    /**
     * Saves the current bil
     *
     * @param bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(InventoryGiftVo bill) {
        return null;
    }


    @Override
    public CommodityItem[] getPromotionCommodity(String id) {
        return new CommodityItem[0];
    }


    @Override
    public InventoryGiftVo[] query(InventoryGiftQueryVo inventoryBillQueryVo) {
        return null;
    }


}
