package trapx00.lightx00.client.test.bl.inventorybl;

import org.junit.Test;
import trapx00.lightx00.client.bl.inventorybl.InventoryWarningBlController;
import trapx00.lightx00.client.bl.inventorybl.factory.InventoryWarningServiceFactory;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryDetailBillVo;
import trapx00.lightx00.shared.po.ResultMessage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InventoryWarningBlControllerTest {
    private InventoryWarningBlController service= InventoryWarningServiceFactory.getInventoryWarningBlController();
    InventoryDetailBillVo inventoryDetailBillVo =null;
    CommodityVo commodityVo=null;
    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(inventoryDetailBillVo));
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(inventoryDetailBillVo));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success,service.modify("01111",100));
    }

   @Test
   public void query() throws Exception {
    }

    @Test
    public void getId() throws Exception {
     assertEquals("123",service.getId());
   }

    @Test
    public void deleteDraft() throws Exception {
        assertEquals(ResultMessage.Success, service.deleteDraft("SKD-20171112-00001"));
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
    public void queryCommodity() throws Exception {
        assertNotNull(1);
    }

    @Test
    public void queryInventoryBill() throws Exception {
        assertNotNull(1);
    }

}