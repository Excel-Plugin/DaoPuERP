package trapx00.lightx00.shared.dataservicestub.commoditydataservice;

import trapx00.lightx00.shared.dataservice.commoditydataservice.CommodityDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.CommodityPo;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;

import java.rmi.RemoteException;
import java.util.Date;

public class CommodityDataServiceStub implements CommodityDataService {

    @Override
    public ResultMessage add(CommodityPo newCommodity) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage modify(CommodityPo updateCommodity) {
        return ResultMessage.Success;
    }

    @Override
    public CommodityPo[] query(CommodityQueryVo commodityQueryVo) {
        return  new CommodityPo[]{
                new CommodityPo("C0001", "LedLight", "Led", 98, new Date(),
                        "1", "2", 56, 60, 90, 99, 50,0)
        };
    }

    @Override
    public CommodityPo[] queryNormally(String query) throws RemoteException {
        return new CommodityPo[0];
    }


    @Override
    public ResultMessage delete(CommodityPo commodity) {
        return ResultMessage.Success;
    }

    @Override
    public CommodityPo[] getAllCommodity() {
        return new CommodityPo[0];
    }

    @Override
    public String getId() {
        return null;
    }


}
