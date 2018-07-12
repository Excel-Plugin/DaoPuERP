package trapx00.lightx00.client.blservice.commodityblservice;

import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;


public interface CommodityBlService {
    //商品管理

    /**
     *  Create a new commoditybl
     * @param newCommodity
     * @return whether the operation is done successfully
     */
    ResultMessage add(CommodityVo newCommodity);//新建商品

    /**
     *  odiyf a commoditybl
     * @param updateCommodity
     * @return whether the operation is done successfully
     */
    ResultMessage modify(CommodityVo updateCommodity);//修改商品

    /**
     * uery for a commoditybl
     * @param commodityQueryVo
     * @return the list of the commoditybl
     */
    CommodityVo[] query(CommodityQueryVo commodityQueryVo);//查询商品
    CommodityVo[] queryNormally(String query);
    /**
     * Delete a commoditybl
     * @param commodity
     * @return whether the operation is done successfully
     */
    ResultMessage delete(CommodityVo commodity);//删除商品


    String getId(String sortId);
}