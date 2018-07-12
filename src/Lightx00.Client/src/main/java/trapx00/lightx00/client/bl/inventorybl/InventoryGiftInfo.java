package trapx00.lightx00.client.bl.inventorybl;

import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;
import trapx00.lightx00.shared.queryvo.InventoryGiftQueryVo;

public interface InventoryGiftInfo {
    /**
     * Querys inventoryBill(Loss,Warning...)
     * @param inventoryBillQueryVo
     * @return  the list of inventoryBIlls that match to the requirement
     */
    InventoryGiftVo[] queryInventoryGiftBill(InventoryGiftQueryVo inventoryBillQueryVo);
}
