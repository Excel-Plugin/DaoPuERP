package trapx00.lightx00.client.test.bl.commoditybl;

import org.junit.Test;
import trapx00.lightx00.client.bl.commoditybl.CommodityBlController;
import trapx00.lightx00.client.bl.commoditybl.factory.CommodityServiceFactory;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.InventoryModificationFlag;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CommodityBlControllerTest {

    private CommodityBlController commodityBlController= CommodityServiceFactory.getController();

    Date date=new Date() ;

    CommodityVo LedLight=new CommodityVo("C0001","LedLight", "Led", 98, date,
            "1", "2", 56, 60, 90, 99, 50
    ,0);

    CommodityVo[] commoditys={LedLight};

    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success,commodityBlController.add(LedLight));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success,commodityBlController.modify(LedLight));
    }

    @Test
    public void query() throws Exception {
        assertEquals("C0001",LedLight.getId() );
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,commodityBlController.delete(LedLight) );
    }

  //  @Test
  //  public void queryCommodity() throws Exception {
   //
  //  }

    @Test
    public void modifyInventory() throws Exception {
        assertEquals(ResultMessage.Success,commodityBlController.modifyInventory("C0001", InventoryModificationFlag.Up,0.1));
    }

}
