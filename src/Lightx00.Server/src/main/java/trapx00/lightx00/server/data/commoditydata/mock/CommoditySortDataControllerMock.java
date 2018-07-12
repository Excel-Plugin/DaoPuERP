package trapx00.lightx00.server.data.commoditydata.mock;

import trapx00.lightx00.shared.dataservicestub.commoditydataservice.CommoditySortDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.CommoditySortPo;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;

import java.rmi.RemoteException;


public class CommoditySortDataControllerMock extends CommoditySortDataServiceStub {

    public CommoditySortDataControllerMock() throws RemoteException {
    }

    String commodityIdList[]={"C0001","C0002"};
    String nextIds[]={"S0002"};
    String lowNextIds[]={""};

    @Override
    public ResultMessage add(CommoditySortPo newCSort) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage modify(CommoditySortPo commoditySort) {
        return ResultMessage.Success;
    }

    @Override
    public CommoditySortPo[] query(CommoditySortQueryVo commoditySortQueryVo) {
        return null;
    }

    @Override
    public ResultMessage delete(CommoditySortPo commoditySort) {
        return ResultMessage.Success;
    }

    @Override
    public CommoditySortPo[] display() {
        return null;
    }

    @Override
    public CommoditySortPo[] dispaly(CommoditySortPo commoditySort) {
        return null;
    }

}
