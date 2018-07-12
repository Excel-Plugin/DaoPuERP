package trapx00.lightx00.shared.dataservice.commoditydataservice;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.CommoditySortPo;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface CommoditySortDataService extends Remote {

    /**
     * Create a new commoditySort attaching to a parentSort
     * @param newCSort
     * @return whether the operation is done successfully
     */
    ResultMessage add(CommoditySortPo newCSort) throws RemoteException;//增加分类

    /**
     * Modify a commoditySort
     * @param commoditySort
     * @return whether the operation is done successfully
     */
    ResultMessage modify(CommoditySortPo commoditySort) throws RemoteException;//修改分类

    /**
     *  Query a commodifySort
     * @param commoditySortQueryVo
     * @return the list of the commoditySort
     */
    CommoditySortPo[] query(CommoditySortQueryVo commoditySortQueryVo) throws RemoteException;//查询

    /**
     *  Delete a commoditySort
     * @param commoditySort
     * @return whether the operation is done successfully
     */
    ResultMessage delete(CommoditySortPo commoditySort) throws RemoteException;//删除

    /**
     * Display all commoditySorts
     * @return the list of commoditySort
     */
    CommoditySortPo[] display() throws RemoteException;//

    /**
     *  Get the childsoft of a commoditySort
     * @param commoditySort
     * @return the childCommoditySort
     */
    CommoditySortPo[] dispaly(CommoditySortPo commoditySort) throws RemoteException;

    String getId()throws RemoteException;

}
