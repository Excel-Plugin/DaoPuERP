package trapx00.lightx00.client.blservice.commodityblservice;

import trapx00.lightx00.client.vo.inventorystaff.CommoditySortVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;

public interface CommoditySortBlService {
    //商品分类管理

    /**
     * Create a new commoditySort attaching to a parentSort
     * @param newSort
     * @return whether the operation is done successfully
     */
    ResultMessage add(CommoditySortVo newSort);//新建分类

    /**
     * Modify a commoditySort
     * @param sort
     * @return whether the operation is done successfully
     */
    ResultMessage modify(CommoditySortVo sort);//修改分类

    /**
     *  Query a commodifySort
     * @param commoditySortQueryVo
     * @return the list of the commoditySort
     */
    CommoditySortVo[] query(CommoditySortQueryVo commoditySortQueryVo);//查询分类

    /**
     *  Delete a commoditySort
     * @param sort
     * @return whether the operation is done successfully
     */
    ResultMessage delete(CommoditySortVo sort);//删除分类

    /**
     * Display all commoditySorts
     * @return the list of commoditySort
     */
    CommoditySortVo[] getAllCommoditySort();//得到all

    String getId();
}