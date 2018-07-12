package trapx00.lightx00.client.test.presentationdriver.inventoryblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryGiftBlService;
import trapx00.lightx00.client.blservicestub.inventoryblservice.InventoryGiftBlServiceStub;
import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class InventoryGiftBlServiceDriver {
    InventoryGiftBlService service=new InventoryGiftBlServiceStub();
    Date date=new Date();
    PromotionCommodity pCommodity=new PromotionCommodity();
    PromotionCommodity[] input={null};
    InventoryGiftVo gifts=new InventoryGiftVo("G0001",date,null,input,null);

    @Test
    public void sumbit() throws Exception {
        assertEquals(ResultMessage.Success,service.sumbit(gifts));
    }

    @Test
    public void  getId() throws Exception {
        assertEquals("123",service.getId());
    }
//
//    @Test
//    public void getGift() throws Exception {
//        assertEquals(null,service.getGift(date));
//    }

}