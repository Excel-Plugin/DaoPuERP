package trapx00.lightx00.shared.bldriver.commoditydataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.commoditydataservice.CommoditySortDataService;
import trapx00.lightx00.shared.dataservicestub.commoditydataservice.CommoditySortDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.CommoditySortPo;

import static org.junit.Assert.assertEquals;

public class CommoditySortDataServiceDriver {
    CommoditySortDataService service=new CommoditySortDataServiceStub();
    CommoditySortPo commoditySortPo=null;
    CommoditySortPo fatheCommoditySortPo=null;
    String commodityIdList[]={""};
    String nextIds[]={"S0002"};
    CommoditySortPo Led=null;

    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success,service.add(commoditySortPo));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success,service.modify(commoditySortPo));
    }

    @Test
    public void query() throws Exception {
        CommoditySortPo commoditySortPo=new CommoditySortPo("S0001","Led",0,"123",null);
        assertEquals("S0001",commoditySortPo.getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,service.delete(commoditySortPo));
    }

    @Test
    public void display() throws Exception {
        assertEquals("S0001",service.display()[0].getId());
    }

    @Test
    public void dispaly() throws Exception {
        assertEquals("S0001",service.dispaly(Led)[0].getId());
    }

    //@Test
   // public void init() throws Exception {
   // }

}