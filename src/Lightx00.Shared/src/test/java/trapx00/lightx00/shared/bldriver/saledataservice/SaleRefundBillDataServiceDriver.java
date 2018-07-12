package trapx00.lightx00.shared.bldriver.saledataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.saledataservice.SaleRefundBillDataService;
import trapx00.lightx00.shared.dataservicestub.saledataservice.SaleRefundBillDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.salestaff.SaleRefundBillPo;

import static org.junit.Assert.assertEquals;

public class SaleRefundBillDataServiceDriver {

    SaleRefundBillDataService service=new SaleRefundBillDataServiceStub();
    SaleRefundBillPo saleRefundBillPo;

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(saleRefundBillPo));
    }

}