package trapx00.lightx00.client.test.presentationdriver.inventoryblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.inventoryblservice.PurchaseBillBlService;
import trapx00.lightx00.client.blservice.inventoryblservice.PurchaseBillBlServiceFactory;
import trapx00.lightx00.client.vo.salestaff.PurchaseBillVo;
import trapx00.lightx00.shared.po.ResultMessage;

import static org.junit.Assert.assertEquals;

public class PurchaseBillBlServiceDriver {

    PurchaseBillBlService service= PurchaseBillBlServiceFactory.getInstance();
    PurchaseBillVo purchaseBillVo;

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(purchaseBillVo));
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success,service.saveAsDraft(purchaseBillVo));
    }

    @Test
    public void getId() throws Exception{
        assertEquals("0",service.getId());
    }

    @Test
    public void queryPurchaseBillVo() throws Exception{
        assertEquals("0",service.queryPurchaseBillVo(null)[0].getId());
    }

}