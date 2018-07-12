package trapx00.lightx00.client.test.presentationdriver.inventoryblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryWarningBlService;
import trapx00.lightx00.client.blservicestub.inventoryblservice.InventoryWarningBlServiceStub;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryDetailBillVo;
import trapx00.lightx00.shared.po.ResultMessage;

import static org.junit.Assert.assertEquals;

public class InventoryWarningBlServiceDriver {
    InventoryWarningBlService service=new InventoryWarningBlServiceStub();
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


}