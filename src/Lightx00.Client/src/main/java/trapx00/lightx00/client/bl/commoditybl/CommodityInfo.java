package trapx00.lightx00.client.bl.commoditybl;


import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;

public interface CommodityInfo {
    /**
     * Query a commoditybl
     * @param commodityQueryVo
     * @return Commodity that match query condition
     */
    CommodityVo[] queryCommodity(CommodityQueryVo commodityQueryVo);

    ResultMessage update (String id,double value);

    CommodityVo[] getAllCommodity();

    ResultMessage modify(CommodityVo commodityVo);


}
