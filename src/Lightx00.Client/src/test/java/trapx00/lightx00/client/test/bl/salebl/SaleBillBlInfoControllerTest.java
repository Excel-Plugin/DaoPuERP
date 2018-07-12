package trapx00.lightx00.client.test.bl.salebl;

import org.junit.Test;
import trapx00.lightx00.client.bl.salebl.SaleBillBlInfo;
import trapx00.lightx00.client.bl.salebl.factory.SaleBillBlInfoFactory;

import static org.junit.Assert.assertEquals;

public class SaleBillBlInfoControllerTest {
    private SaleBillBlInfo saleBillBlInfo= SaleBillBlInfoFactory.getSaleBillBlInfo();

    @Test
    public void querySaleBill() throws Exception {
        assertEquals("0",saleBillBlInfo.querySaleBill(null)[0].getId());
    }

    @Test
    public void querySaleRefundBill() throws Exception {
        assertEquals("0",saleBillBlInfo.querySaleRefundBill(null)[0].getId());
    }

}