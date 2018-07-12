package trapx00.lightx00.client.blservicestub.commodityblservice;

import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlService;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;

import java.util.Date;

public class CommodityBlServiceStub implements CommodityBlService {

    Date date=new Date();

    @Override
    public ResultMessage add(CommodityVo newCommodity) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage modify(CommodityVo updateCommodity) {
        return ResultMessage.Success;
    }

    @Override
    public CommodityVo[] query(CommodityQueryVo commodityQueryVo) {
        return new CommodityVo[]{
            new CommodityVo("C0001", "LedLight", "Led", 98, date,
                    "1", "2", 56, 60, 90, 99, 50
            ,0)
        };
    }

    @Override
    public CommodityVo[] queryNormally(String query) {
        return new CommodityVo[0];
    }

    @Override
    public ResultMessage delete(CommodityVo commodity) {
        return ResultMessage.Success;
    }

    @Override
    public String getId(String sortId) {
        return null;
    }
}
