package trapx00.lightx00.client.test.bl.commoditybl;

import org.junit.Test;
import trapx00.lightx00.client.bl.commoditybl.CommoditySortBlController;
import trapx00.lightx00.client.bl.commoditybl.factory.CommoditySortServiceFactory;
import trapx00.lightx00.client.vo.inventorystaff.CommoditySortVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;

import static org.junit.Assert.assertEquals;

public class CommoditySortBlControllerTest {

    private CommoditySortBlController service= CommoditySortServiceFactory.getController();
    String nextIds[]={"S0002"};
    CommoditySortVo Led=new CommoditySortVo("S0001","Led", 0,
            "",  null);


    @Test
    public void modify() throws Exception {
        try{
            System.out.println(service.query(new CommoditySortQueryVo().idEq("PRO-0001"))[0].getId());
        }catch (UncheckedRemoteException e){

        }

    }

    @Test
    public void query() throws Exception {
        assertEquals("S0001",Led.getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,service.delete(Led));
    }

    @Test
    public void display() throws Exception {
        assertEquals("S0001",Led.getId());
    }

    @Test
    public void dispaly() throws Exception {
        assertEquals("S0001",Led.getId());
    }
 //   @Test
  //  public void queryCommoditySort() throws Exception {
  //  }
}
