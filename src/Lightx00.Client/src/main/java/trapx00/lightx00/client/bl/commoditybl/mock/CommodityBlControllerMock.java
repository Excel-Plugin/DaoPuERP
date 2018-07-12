package trapx00.lightx00.client.bl.commoditybl.mock;

import trapx00.lightx00.client.bl.commoditybl.CommodityBlController;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.InventoryModificationFlag;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;


public class CommodityBlControllerMock extends CommodityBlController {

    /**
     * Add a new commoditybl
     * @param newCommodity
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(CommodityVo newCommodity) {
        return super.add(newCommodity);
    }

    /**
     *  Modifys a commoditybl
     * @param updateCommodity
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(CommodityVo updateCommodity) {
        return super.modify(updateCommodity);
    }

    /**
     * Querys a commoditybl
     * @param commodityQueryVo
     * @return The commoditybl that match to the requirement
     */
    @Override
    public CommodityVo[] query(CommodityQueryVo commodityQueryVo) {
        return super.query(commodityQueryVo);
    }

    /**
     * Deltes the existed commoditybl
     * @param commodity
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(CommodityVo commodity) {
        return super.delete(commodity);
    }
    /**
     * Modify the inventoryNum
     * @param commodityId
     * @param flag
     * @param delta
     * @return ResultMessage
     */
    @Override
    public ResultMessage modifyInventory(String commodityId, InventoryModificationFlag flag, double delta) {
        return super.modifyInventory(commodityId, flag, delta);
    }
}
