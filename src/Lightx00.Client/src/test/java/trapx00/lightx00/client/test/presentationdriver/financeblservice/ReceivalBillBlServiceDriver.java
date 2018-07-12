package trapx00.lightx00.client.test.presentationdriver.financeblservice;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.client.blservice.financeblservice.ReceivalBillBlService;
import trapx00.lightx00.client.blservicestub.financeblservice.ReceivalBillBlServiceStub;
import trapx00.lightx00.client.vo.financestaff.ReceivalBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ReceivalBillBlServiceDriver {
    private ReceivalBillBlService service = new ReceivalBillBlServiceStub();
    private ReceivalBillVo bill = null;
    @Before
    public void setUp() throws Exception {
        bill = new ReceivalBillVo("123",new Date(), BillState.Draft,"123","123",null,0);
    }

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success, service.submit(bill));
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success, service.saveAsDraft(bill));
    }


}