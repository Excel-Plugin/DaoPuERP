package trapx00.lightx00.client.bl.commoditybl;


import trapx00.lightx00.client.vo.inventorystaff.CommoditySortVo;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;

public interface CommoditySortInfo {
    /**
     * Query a commoditySort
     * @param commoditySortQueryVo
     * @return commodtySort that match query condition
     */
    CommoditySortVo[] queryCommoditySort(CommoditySortQueryVo commoditySortQueryVo);

}
