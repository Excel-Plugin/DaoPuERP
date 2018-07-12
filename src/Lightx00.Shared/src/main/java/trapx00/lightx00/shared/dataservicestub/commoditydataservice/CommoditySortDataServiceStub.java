package trapx00.lightx00.shared.dataservicestub.commoditydataservice;

import trapx00.lightx00.shared.dataservice.commoditydataservice.CommoditySortDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.CommoditySortPo;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;


public class CommoditySortDataServiceStub implements CommoditySortDataService {
    String commodityIdList[]={"C0001","C0002"};
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
        CommoditySortPo commoditySortPo= new CommoditySortPo("PRO-0002","DgLed",0,"PRO-0001",null);
        CommoditySortPo  commoditySortPo1=new CommoditySortPo("PRO-0001","Led",0,null,null);
        CommoditySortPo[]result={commoditySortPo,commoditySortPo1};
        return result;

    }

    @Override
    public CommoditySortPo[] dispaly(CommoditySortPo commoditySort) {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }


}
