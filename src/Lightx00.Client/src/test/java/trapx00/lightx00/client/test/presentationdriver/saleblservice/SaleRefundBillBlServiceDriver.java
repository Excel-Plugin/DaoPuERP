package trapx00.lightx00.client.test.presentationdriver.saleblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.saleblservice.SaleRefundBillBlService;
import trapx00.lightx00.client.blservice.saleblservice.SaleRefundBillBlServiceFactory;
import trapx00.lightx00.client.vo.salestaff.SaleRefundBillVo;
import trapx00.lightx00.shared.po.ResultMessage;

import static org.junit.Assert.assertEquals;

public class SaleRefundBillBlServiceDriver {

    SaleRefundBillBlService service=SaleRefundBillBlServiceFactory.getInstance();
    SaleRefundBillVo saleRefundBillVo;

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(saleRefundBillVo));
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(saleRefundBillVo));
    }

    @Test
    public void getId() throws Exception{
        assertEquals("0",service.getId());
    }

    @Test
    public void querySaleRefundBill() throws Exception{
        assertEquals("0",service.querySaleRefundBill(null)[0].getId());
    }
}