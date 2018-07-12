package trapx00.lightx00.shared.bldriver.financedataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.financedataservice.ReceivalBillDataService;
import trapx00.lightx00.shared.dataservicestub.financedataservice.ReceivalBillDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.ReceivalBillPo;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ReceivalDataServiceDriver {
    ReceivalBillDataService service = new ReceivalBillDataServiceStub();
    final ReceivalBillPo bill = new ReceivalBillPo("123", new Date(), BillState.Draft,"","",null,0);
    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success, service.submit(bill));
    }

    @Test
    public void activate() throws Exception {
        assertEquals(ResultMessage.Success, service.activate(bill.getId()));
    }

    @Test
    public void abandon() throws Exception {
        assertEquals(ResultMessage.Success, service.abandon(bill.getId()));
    }

}