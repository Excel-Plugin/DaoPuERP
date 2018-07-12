package trapx00.lightx00.shared.bldriver.inventorydataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseBillDataService;
import trapx00.lightx00.shared.dataservicestub.inventorydataservice.PurchaseBillDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.salestaff.PurchaseBillPo;

import static org.junit.Assert.assertEquals;

public class PurchaseBillDataServiceDriver {

    PurchaseBillDataService service=new PurchaseBillDataServiceStub();
    PurchaseBillPo purchaseBillPo;

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(purchaseBillPo));
    }

}