package trapx00.lightx00.client.blservice.inventoryblservice;

import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.queryvo.InventoryGiftQueryVo;

public interface InventoryGiftBlService {
    /**
     * Submits a GiftBill.
     * @param inventoryGiftVo
     * @return whether the operation is done successfully
     */
    ResultMessage sumbit(InventoryGiftVo inventoryGiftVo);//提交赠送单

    /**
     *  Gets the id for the next GiftBill
     * @return the id
     */
    String getId();

    /**
     * Saves the current bil
     * @param bill
     * @return  whether the operation is done successfully
     */
    ResultMessage saveAsDraft(InventoryGiftVo bill);//保存草稿

    CommodityItem[] getPromotionCommodity(String id);

    InventoryGiftVo[] query(InventoryGiftQueryVo inventoryBillQueryVo);



}

