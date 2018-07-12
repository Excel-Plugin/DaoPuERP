package trapx00.lightx00.client.blservicestub.financeblservice;

import trapx00.lightx00.client.blservice.financeblservice.SaleDetailBlService;
import trapx00.lightx00.client.vo.financestaff.SaleDetailQueryVo;
import trapx00.lightx00.client.vo.financestaff.SaleDetailVo;
import trapx00.lightx00.client.vo.financestaff.SaleRecordVo;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

public class SaleDetailBlServiceStub implements SaleDetailBlService {
    private CommodityVo commodityVo = new CommodityVo("123","123","123",10,new Date(),"123","123",10,10,10,10,10
    ,0);
    @Override
    public SaleDetailVo query(SaleDetailQueryVo query) {
        return new SaleDetailVo(new SaleRecordVo[]
                { new SaleRecordVo(
                        new Date(),commodityVo,123,0 ,0) });
    }

    @Override
    public ResultMessage export(SaleDetailVo detail) {
        return ResultMessage.Success;
    }
}
