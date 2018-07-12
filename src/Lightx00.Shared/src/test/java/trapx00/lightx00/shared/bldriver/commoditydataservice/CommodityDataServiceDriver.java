package trapx00.lightx00.shared.bldriver.commoditydataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.commoditydataservice.CommodityDataService;
import trapx00.lightx00.shared.dataservicestub.commoditydataservice.CommodityDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.CommodityPo;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CommodityDataServiceDriver {
    CommodityDataService service=new CommodityDataServiceStub();
    CommodityPo commodityPo=null;
    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success,service.add(commodityPo));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success,service.modify(commodityPo));
    }

    @Test
    public void query() throws Exception {
        CommodityPo commodityPo=new CommodityPo("C0001","Led","Led",34,new Date(),"1","2",34,34,34,34,34,0);
        assertEquals("C0001",commodityPo.getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,service.delete(commodityPo));
    }

   // @Test
    //public void init() throws Exception {

    //}

}