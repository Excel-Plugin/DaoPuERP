package trapx00.lightx00.client.bl.commoditybl;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.InventoryModificationFlag;

public interface InventoryModificationService {
    /**
     * Modify the num of the commoditybl
     * @param commodityId
     * @param flag
     * @param delta
     * @return whether the operation is done successfully
     */
    ResultMessage modifyInventory(String commodityId, InventoryModificationFlag flag, double delta);

}
