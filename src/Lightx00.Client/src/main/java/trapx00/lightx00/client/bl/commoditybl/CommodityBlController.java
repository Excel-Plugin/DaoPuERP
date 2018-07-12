package trapx00.lightx00.client.bl.commoditybl;

import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.bl.util.PoVoConverter;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlService;
import trapx00.lightx00.client.datafactory.commoditydataservicefactory.CommodityDataServiceFactory;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.dataservice.commoditydataservice.CommodityDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.CommodityPo;
import trapx00.lightx00.shared.po.inventorystaff.InventoryModificationFlag;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;

import java.rmi.RemoteException;
import java.util.ArrayList;


public class CommodityBlController implements CommodityBlService,CommodityInfo,InventoryModificationService,PoVoConverter<CommodityPo,CommodityVo> {
    private LogService logService = LogServiceFactory.getLogService();
    private CommodityDataService dataService= CommodityDataServiceFactory.getService();

    /**
     * Add a new commoditybl
     * @param newCommodity
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(CommodityVo newCommodity) {
        CommodityPo commodityPo=this.fromVoToPo(newCommodity);
        try{
            ResultMessage opResult = dataService.add(commodityPo);
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("创建了商品%s,编号为%s。", newCommodity.getName(), newCommodity.getId()));
            } else {
                logService.log(LogSeverity.Failure, String.format("创建商品%s失败，原因不明。",newCommodity.getName()));
            }
            return opResult;
        }catch (RemoteException e) { //RemoteException是网络原因的错误。这里记下日志，然后用UncheckedRemoteException包一下继续抛出。
            logService.log(LogSeverity.Failure, String.format("创建商品%s失败，原因网络原因，具体信息是%s，商品名是%s",newCommodity.getId(), e.getMessage(),newCommodity.getName()) );
            throw new UncheckedRemoteException(e);
        } catch (IdExistsException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, String.format("创建商品%s重复，原因是商品id已经存在。已经更新商品%s", newCommodity.getId(), newCommodity.getName()));
            throw e;
        }


    }

    /**
     *  Modifys a commoditybl
     * @param updateCommodity
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(CommodityVo updateCommodity) {
        CommodityPo commodityPo=this.fromVoToPo(updateCommodity);
        try{
            ResultMessage opResult = dataService.modify(commodityPo);
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("更新了商品%s,编号为%s。", updateCommodity.getName(), updateCommodity.getId()));
            } else {
                logService.log(LogSeverity.Failure, String.format("更新商品%s失败，原因不明。",updateCommodity.getName()));
            }
            return opResult;
        }catch (RemoteException e) { //RemoteException是网络原因的错误。这里记下日志，然后用UncheckedRemoteException包一下继续抛出。
            logService.log(LogSeverity.Failure, String.format("更新商品%s失败，原因网络原因，具体信息是%s，商品名是%s",updateCommodity.getId(), e.getMessage(),updateCommodity.getName()) );
            throw new UncheckedRemoteException(e);
        } catch (IdNotExistsException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, String.format("更新商品%s%s成功，但原商品不存在，创建了新商品", updateCommodity.getId(), updateCommodity.getName()));
            throw e;
        }

    }

    /**
     * Querys a commoditybl
     * @param commodityQueryVo
     * @return The commoditybl that match to the requirement
     */
    @Override
    public CommodityVo[] query(CommodityQueryVo commodityQueryVo) {
        String logLeadingText = String.format("查找商品");
        try {
            CommodityPo[] queryResult = dataService.query(commodityQueryVo);
            logService.log(LogSeverity.Success, String.format(logLeadingText + "成功，查找到%d条记录。", queryResult.length));
            CommodityVo[] result=new CommodityVo[queryResult.length];
            for(int i=0;i<queryResult.length;i++){
                result[i]=this.fromPoToVo(queryResult[i]);
            }
            return result;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format(logLeadingText + "失败，原因是网络原因，具体信息是%s。", e.getMessage()));
            throw new UncheckedRemoteException(e);
        }
    }

    @Override
    public CommodityVo[] queryNormally(String query) {
        try {
            CommodityPo[] commodityPos = dataService.queryNormally(query);
            ArrayList<CommodityVo> commodityVos = new ArrayList<CommodityVo>();
            for (int i = 0; i < commodityPos.length; i++) {
                    commodityVos.add(fromPoToVo(commodityPos[i]));
            }
            return commodityVos.toArray(new CommodityVo[commodityVos.size()]);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deltes the existed commoditybl
     * @param commodity
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(CommodityVo commodity) {
        CommodityPo commodityPo=this.fromVoToPo(commodity);
        try{
            ResultMessage opResult = dataService.delete(commodityPo);
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("删除了商品%s,编号为%s。", commodity.getName(), commodity.getId()));
            } else {
                logService.log(LogSeverity.Failure, String.format("删除商品%s失败，原因不明。",commodity.getName()));
            }
            return opResult;
        }catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format( "删除%s商品失败，原因是网络原因，具体信息是%s。", commodity.getName(),e.getMessage()));
            throw new UncheckedRemoteException(e);
        }catch (IdNotExistsException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, String.format("删除商品%s%s失败，原商品不存在", commodity.getId(), commodity.getName()));
            throw e;
        }
    }

    @Override
    public String getId(String sortId) {
        try {
            String id = dataService.getId();
            logService.log(LogSeverity.Info, String.format("获得了一个新的商品ID：%s", sortId+id));
            return sortId+id;
        } catch (RemoteException e) { //RemoteException是网络原因的错误。这里记下日志，然后用UncheckedRemoteException包一下继续抛出。
            logService.log(LogSeverity.Failure, String.format("获得新ID失败，原因是网络原因，具体是%s", e.getMessage()));
            throw new UncheckedRemoteException(e);
        } catch (NoMoreBillException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, "获得新ID失败，原因是当日ID已满。");
            throw e;
        }
    }

    /**
     * Query a commoditybl
     * @param commodityQueryVo
     * @return Commodity that match query condition
     */
    @Override
    public CommodityVo[] queryCommodity(CommodityQueryVo commodityQueryVo) {
        String logLeadingText = String.format("查找商品");
        try {
            CommodityPo[] queryResult = dataService.query(commodityQueryVo);
            logService.log(LogSeverity.Success, String.format(logLeadingText + "成功，查找到%d条记录。", queryResult.length));
            CommodityVo[] result=new CommodityVo[queryResult.length];
            for(int i=0;i<queryResult.length;i++){
                result[i]=this.fromPoToVo(queryResult[i]);
            }
            return result;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format(logLeadingText + "失败，原因是网络原因，具体信息是%s。", e.getMessage()));
            throw new UncheckedRemoteException(e);
        }
    }

    @Override
    public ResultMessage update(String id, double value) {
        String logLeadingText = String.format("修改警戒值");
        try{
            CommodityPo[] commodityPos=dataService.query(new CommodityQueryVo().eq("id",id));
            CommodityVo commodityVo=this.fromPoToVo(commodityPos[0]);
            commodityVo.setWarningValue(value);
            this.modify(commodityVo);
            logService.log(LogSeverity.Success, String.format("修改%s%s的警戒值成功",commodityVo.getId(),commodityVo.getName()));
            return ResultMessage.Success;
        }catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format(logLeadingText + "失败，原因是网络原因，具体信息是%s。", e.getMessage()));
            throw new UncheckedRemoteException(e);
        }
    }

    @Override
    public CommodityVo[] getAllCommodity() {
        String logLeadingText = String.format("得到所有商品");
        try{
            CommodityPo[] commodityPos=dataService.getAllCommodity();
            logService.log(LogSeverity.Success, String.format(logLeadingText + "成功，查找到%d条记录。", commodityPos.length));
            CommodityVo[] commodityVos=new CommodityVo[commodityPos.length];
            for(int i=0;i<commodityPos.length;i++){
                commodityVos[i]= this.fromPoToVo(commodityPos[i]);
            }
            return commodityVos;
        }catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format(logLeadingText + "失败，原因是网络原因，具体信息是%s。", e.getMessage()));
            throw new UncheckedRemoteException(e);
        }
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
        String logLeadingText = String.format("修改库存");
        try{
            CommodityPo[] commodityPos=dataService.query(new CommodityQueryVo().eq("id",commodityId));
            CommodityVo commodityVo=this.fromPoToVo(commodityPos[0]);
            commodityVo.setAmount(commodityVo.getAmount()+((flag==InventoryModificationFlag.Up?1:-1)*delta));
            commodityVo.setActualAmount(commodityVo.getAmount());
            this.modify(commodityVo);
            logService.log(LogSeverity.Success, String.format("修改%s%s的库存值成功",commodityVo.getId(),commodityVo.getName()));
            return ResultMessage.Success;
        }catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format(logLeadingText + "失败，原因是网络原因，具体信息是%s。", e.getMessage()));
            throw new UncheckedRemoteException(e);
        }
    }

    @Override
    public CommodityPo fromVoToPo(CommodityVo commodityVo) {
        return new CommodityPo(commodityVo.getId(),commodityVo.getName(),commodityVo.getType(),commodityVo.getAmount(),
                commodityVo.getProductionDate(),commodityVo.getBatch(),commodityVo.getBatchNo(),commodityVo.getPurchasePrice(),
                commodityVo.getRecentPurchasePrice(),commodityVo.getRetailPrice(),commodityVo.getRecentRetailPrice(),
                commodityVo.getWarningValue(),commodityVo.getActualAmount());
    }

    @Override
    public CommodityVo fromPoToVo(CommodityPo commodityPo) {
        return new CommodityVo(commodityPo.getId(),commodityPo.getName(),commodityPo.getType(),commodityPo.getAmount(),
                commodityPo.getProductionDate(),commodityPo.getBatch(),commodityPo.getBatchNo(),commodityPo.getPurchasePrice(),
                commodityPo.getRecentPurchasePrice(),commodityPo.getRetailPrice(),commodityPo.getRecentRetailPrice(),
                commodityPo.getWarningValue(),commodityPo.getActualValue());
    }
}