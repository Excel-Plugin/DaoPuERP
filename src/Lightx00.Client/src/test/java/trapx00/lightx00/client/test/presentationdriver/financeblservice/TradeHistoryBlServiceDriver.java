package trapx00.lightx00.client.test.presentationdriver.financeblservice;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.client.blservice.financeblservice.TradeHistoryBlService;
import trapx00.lightx00.client.blservicestub.financeblservice.TradeHistoryBlServiceStub;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.financestaff.TradeHistoryQueryVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillType;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TradeHistoryBlServiceDriver {
    private TradeHistoryBlService service = new TradeHistoryBlServiceStub();
    private BillVo bill;

    @Before
    public void setUp() throws Exception {
        bill = service.query(
                new TradeHistoryQueryVo(new Date(), new Date(), new BillType[]{BillType.FinanceBill},null,null,1)).getBills()[0];
    }

    @Test
    public void query() throws Exception {
        assertEquals(BillType.FinanceBill, bill.getBillType());
    }

    @Test
    public void export() throws Exception {
        assertEquals(ResultMessage.Success,
                service.export(service.query(
                new TradeHistoryQueryVo(new Date(), new Date(), new BillType[]{BillType.FinanceBill},null,null,1))));
    }

}