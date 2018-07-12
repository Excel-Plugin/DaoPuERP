package trapx00.lightx00.shared.dataservice.commoditydataservice;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.CommodityPo;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface CommodityDataService extends Remote {

    /**
     *  Create a new commoditybl
     * @param newCommodity
     * @return whether the operation is done successfully
     */
    ResultMessage add(CommodityPo newCommodity) throws RemoteException;//新建商品

    /**
     *  odiyf a commoditybl
     * @param updateCommodity
     * @return whether the operation is done successfully
     */
    ResultMessage modify(CommodityPo updateCommodity)throws RemoteException;//修改

    /**
     * uery for a commoditybl
     * @param commodityQueryVo
     * @return the list of the commoditybl
     */
    CommodityPo[] query(CommodityQueryVo commodityQueryVo)throws RemoteException;//查询

    CommodityPo[] queryNormally(String query)throws RemoteException;
    /**
     * Delete a commoditybl
     * @param commodity
     * @return whether the operation is done successfully
     */
    ResultMessage delete(CommodityPo commodity)throws RemoteException;//删除


    CommodityPo[] getAllCommodity() throws RemoteException;


    String getId() throws RemoteException;


}
