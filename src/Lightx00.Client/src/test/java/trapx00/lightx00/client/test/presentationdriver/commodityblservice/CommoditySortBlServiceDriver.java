package trapx00.lightx00.client.test.presentationdriver.commodityblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.commodityblservice.CommoditySortBlService;
import trapx00.lightx00.client.blservicestub.commodityblservice.CommoditySortBlServiceStub;
import trapx00.lightx00.client.vo.inventorystaff.CommoditySortVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.CommoditySortItem;

import static org.junit.Assert.assertEquals;

public class CommoditySortBlServiceDriver {

    private CommoditySortBlService service=new CommoditySortBlServiceStub();

    String commodityIdList[]={""};
    private String nextIds[]={"S0002"};
    CommoditySortVo Led=new CommoditySortVo("S0001","Led", 0,
    "",  new CommoditySortItem[] {new CommoditySortItem("123","123")});
    private CommoditySortVo[] commoditysorts={Led};


    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success,service.add(Led));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success,service.modify(Led));
    }

    @Test
    public void query() throws Exception {
        assertEquals("S0001",commoditysorts[0].getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,service.delete(Led));
    }


}