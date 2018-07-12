package trapx00.lightx00.shared.bldriver.inventorydataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseRefundBillDataService;
import trapx00.lightx00.shared.dataservicestub.inventorydataservice.PurchaseRefundBillDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.salestaff.PurchaseRefundBillPo;

import static org.junit.Assert.assertEquals;

public class PurchaseRefundBillDataServiceDriver {

    PurchaseRefundBillDataService service=new PurchaseRefundBillDataServiceStub();
    PurchaseRefundBillPo purchaseRefundBillPo;

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(purchaseRefundBillPo));
    }

}