package trapx00.lightx00.client.test.bl.inventorybl;

import org.junit.Test;
import trapx00.lightx00.client.bl.inventorybl.InventoryGiftBlController;
import trapx00.lightx00.client.bl.inventorybl.factory.InventoryGiftServiceFactory;
import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class InventoryGiftBlControllerTest {

    private InventoryGiftBlController service= InventoryGiftServiceFactory.getInventoryGiftBlController();

    Date date=new Date();
    PromotionCommodity pCommodity=new PromotionCommodity();
    PromotionCommodity[] input={null};
    InventoryGiftVo gifts=new InventoryGiftVo("G0001",date,null,input,null);
    @Test
    public void sumbit() throws Exception {
        assertEquals(ResultMessage.Success,service.sumbit(gifts));
    }

    @Test
    public void getId() throws Exception {
        assertEquals("123",service.getId());
    }

   @Test
    public void queryCommodity() throws Exception {
   }

   @Test
   public void abandon() throws Exception {
        assertEquals(ResultMessage.Success, service.abandon("SKD-20171112-00001"));
    }

   @Test
    public void activate() throws Exception {
        assertEquals(ResultMessage.Success, service.activate("SKD-20171112-00001"));
   }

   @Test
    public void deleteDraft() throws Exception {
        assertEquals(ResultMessage.Success, service.deleteDraft("SKD-20171112-00001"));
    }
}
