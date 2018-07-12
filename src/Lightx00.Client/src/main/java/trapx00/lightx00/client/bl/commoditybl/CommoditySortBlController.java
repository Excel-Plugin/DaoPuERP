package trapx00.lightx00.client.bl.commoditybl;

import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.bl.util.PoVoConverter;
import trapx00.lightx00.client.blservice.commodityblservice.CommoditySortBlService;
import trapx00.lightx00.client.datafactory.commoditydataservicefactory.CommoditySortDataServiceFactory;
import trapx00.lightx00.client.vo.inventorystaff.CommoditySortVo;
import trapx00.lightx00.shared.dataservice.commoditydataservice.CommoditySortDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.CommoditySortPo;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;

import java.rmi.RemoteException;

public class CommoditySortBlController implements CommoditySortBlService,CommoditySortInfo,PoVoConverter<CommoditySortPo,CommoditySortVo> {


    private LogService logService = LogServiceFactory.getLogService();
    private CommoditySortDataService dataService= CommoditySortDataServiceFactory.getService();
    /**
     * Add a commoditySort
     * @param newSort
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(CommoditySortVo newSort) {
        CommoditySortPo commoditySortPo=this.fromVoToPo(newSort);
        try{
            ResultMessage opResult = dataService.add(commoditySortPo);
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("创建了商品种类%s,编号为%s。", newSort.getName(), newSort.getId()));
            } else {
                logService.log(LogSeverity.Failure, String.format("创建商品种类%s失败，原因不明。",newSort.getName()));
            }
            return opResult;
        }catch (RemoteException e) { //RemoteException是网络原因的错误。这里记下日志，然后用UncheckedRemoteException包一下继续抛出。
            logService.log(LogSeverity.Failure, String.format("创建商品种类%s失败，原因网络原因，具体信息是%s，商品名是%s",newSort.getId(), e.getMessage(),newSort.getName()) );
            throw new UncheckedRemoteException(e);
        } catch (IdExistsException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, String.format("创建商品种类%s重复，原因是商品种类id已经存在。已经更新商品%s", newSort.getId(), newSort.getName()));
            throw e;
        }
    }

    /**
     * Modifys a commoditySort
     * @param sort
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(CommoditySortVo sort) {
        CommoditySortPo commoditySortPo=this.fromVoToPo(sort);
        try{
            ResultMessage opResult = dataService.modify(commoditySortPo);
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("更新了商品种类%s,编号为%s。", sort.getName(), sort.getId()));
            } else {
                logService.log(LogSeverity.Failure, String.format("更新商品种类%s失败，原因不明。",sort.getName()));
            }
            return opResult;
        }catch (RemoteException e) { //RemoteException是网络原因的错误。这里记下日志，然后用UncheckedRemoteException包一下继续抛出。
            logService.log(LogSeverity.Failure, String.format("更新商品种类%s失败，原因网络原因，具体信息是%s，商品名是%s",sort.getId(), e.getMessage(),sort.getName()) );
            throw new UncheckedRemoteException(e);
        } catch (IdNotExistsException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, String.format("更新商品种类%s%s成功，但原商品种类不存在，创建了新商品种类", sort.getId(), sort.getName()));
            throw e;
        }

    }

    /**
     * Querys a commoditySort
     * @param commoditySortQueryVo
     * @return The commoditySort that match to the requirement
     */
    @Override
    public CommoditySortVo[] query(CommoditySortQueryVo commoditySortQueryVo) {
        String logLeadingText = String.format("查找商品种类");
        try {
            CommoditySortPo[] queryResult = dataService.query(commoditySortQueryVo);
            logService.log(LogSeverity.Success, String.format(logLeadingText + "成功，查找到%d条记录。", queryResult.length));
            CommoditySortVo[] result=new CommoditySortVo[queryResult.length];
            for(int i=0;i<queryResult.length;i++){
                result[i]=this.fromPoToVo(queryResult[i]);
            }
            return result;
        } catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format(logLeadingText + "失败，原因是网络原因，具体信息是%s。", e.getMessage()));
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Deletes a commoditySort
     * @param commoditySortVo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(CommoditySortVo commoditySortVo) {
        CommoditySortPo commoditySortPo=this.fromVoToPo(commoditySortVo);
        try{
            ResultMessage opResult = dataService.delete(commoditySortPo);
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("删除了商品种类%s,编号为%s。", commoditySortVo.getName(), commoditySortVo.getId()));
            } else {
                logService.log(LogSeverity.Failure, String.format("删除商品种类%s失败，原因不明。",commoditySortVo.getName()));
            }
            return opResult;
        }catch (RemoteException e) {
            logService.log(LogSeverity.Failure, String.format( "删除%s商品种类失败，原因是网络原因，具体信息是%s。", commoditySortVo.getName(),e.getMessage()));
            throw new UncheckedRemoteException(e);
        }catch (IdNotExistsException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, String.format("删除商品种类%s%s失败，原商品不存在", commoditySortVo.getId(), commoditySortVo.getName()));
            throw e;
        }
    }

    /**
     * Display all commoditySorts
     * @return The list of all commoditySorts
     */
    @Override
    public CommoditySortVo[] getAllCommoditySort() {
       try{
           CommoditySortPo[]commoditySortPos=dataService.query(new CommoditySortQueryVo());
           CommoditySortVo[]result=new CommoditySortVo[commoditySortPos.length];
           for(int i=0;i<commoditySortPos.length;i++){
               result[i]=this.fromPoToVo(commoditySortPos[i]);
           }
           logService.log(LogSeverity.Success,String.format("得到所有商品种类成功"));
           return result;
       }catch (RemoteException e) {
           logService.log(LogSeverity.Failure, String.format("得到所有商品种类失败，原因是网络原因，具体信息是%s。", e.getMessage()));
           throw new UncheckedRemoteException(e);
       }
    }

    @Override
    public String getId() {
        try {
            String id = dataService.getId();
            logService.log(LogSeverity.Info, String.format("获得了一个新的分类ID：%s", id));
            return id;
        } catch (RemoteException e) { //RemoteException是网络原因的错误。这里记下日志，然后用UncheckedRemoteException包一下继续抛出。
            logService.log(LogSeverity.Failure, String.format("获得新ID失败，原因是网络原因，具体是%s", e.getMessage()));
            throw new UncheckedRemoteException(e);
        } catch (NoMoreBillException e) { //参考服务器端写的代码，直接抓对应的异常即可，记得如果不在这里处理，需要继续往外抛出。比如这里就需要UI层继续处理这个异常，所以需要继续抛出。
            logService.log(LogSeverity.Failure, String.format("获得新ID失败，原因是当日ID已满。"));
            throw e;
        }
    }

    /**
     * Query a commoditySort
     * @param commoditySortQueryVo
     * @return commodtySort that match query condition
     */
    @Override
    public CommoditySortVo[] queryCommoditySort(CommoditySortQueryVo commoditySortQueryVo) {
        String logLeadingText = String.format("查找商品种类");
        try {
            CommoditySortPo[] queryResult = dataService.query(commoditySortQueryVo);
            logService.log(LogSeverity.Success, String.format(logLeadingText + "成功，查找到%d条记录。", queryResult.length));
            CommoditySortVo[] result=new CommoditySortVo[queryResult.length];
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
    public CommoditySortPo fromVoToPo(CommoditySortVo vo) {
        return new CommoditySortPo(vo.getId(),vo.getName(),vo.getLeaf(),vo.getPreId(),vo.getCommoditySortItems());
    }

    @Override
    public CommoditySortVo fromPoToVo(CommoditySortPo po) {
        return new CommoditySortVo(po.getId(),po.getName(),po.getLeaf(),po.getPreId(),po.getCommoditySortItems());
    }
}
