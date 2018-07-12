package trapx00.lightx00.client.test.presentationdriver.commodityblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlService;
import trapx00.lightx00.client.blservicestub.commodityblservice.CommodityBlServiceStub;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CommodityBlServiceDriver {

    CommodityBlService service=new CommodityBlServiceStub();

    Date date=new Date() ;

    CommodityVo LedLight=new CommodityVo("C0001","LedLight", "Led", 98, date,
    "1", "2", 56, 60, 90, 99, 50
    ,0);

    CommodityVo[] commoditys;


    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success,service.add(LedLight));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success,service.modify(LedLight));
    }

 //   @Test
  //  public void query() throws Exception {
  //      assertEquals("C0001",commoditys[0].getId() );
  //  }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,service.delete(LedLight) );
    }

}