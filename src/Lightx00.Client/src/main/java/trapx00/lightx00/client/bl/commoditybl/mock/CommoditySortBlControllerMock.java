package trapx00.lightx00.client.bl.commoditybl.mock;

import trapx00.lightx00.client.bl.commoditybl.CommoditySortBlController;
import trapx00.lightx00.client.vo.inventorystaff.CommoditySortVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;

public class CommoditySortBlControllerMock extends CommoditySortBlController {

    /**
     * Add a commoditySort
     * @param newSort
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(CommoditySortVo newSort) {
        return super.add(newSort);
    }

    /**
     * Modifys a commoditySort
     * @param sort
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(CommoditySortVo sort) {
        return super.modify(sort);
    }

    /**
     * Querys a commoditySort
     * @param commoditySortQueryVo
     * @return The commoditySort that match to the requirement
     */
    @Override
    public CommoditySortVo[] query(CommoditySortQueryVo commoditySortQueryVo) {
        return super.query(commoditySortQueryVo);
    }

    /**
     * Deletes a commoditySort
     * @param sort
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(CommoditySortVo sort) {
        return super.delete(sort);
    }

    /**
     * Display all commoditySorts
     * @return The list of all commoditySorts
     */

}
