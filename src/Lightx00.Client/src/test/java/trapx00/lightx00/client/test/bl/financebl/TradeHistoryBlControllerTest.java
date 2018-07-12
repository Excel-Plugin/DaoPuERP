package trapx00.lightx00.client.test.bl.financebl;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.client.bl.financebl.TradeHistoryBlController;
import trapx00.lightx00.client.bl.financebl.factory.TradeHistoryBlFactory;
import trapx00.lightx00.client.vo.financestaff.FinanceBillVo;
import trapx00.lightx00.client.vo.financestaff.TradeHistoryQueryVo;
import trapx00.lightx00.client.vo.financestaff.TradeHistoryVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillType;
import trapx00.lightx00.shared.queryvo.FinanceBillQueryVo;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TradeHistoryBlControllerTest {
    private TradeHistoryBlController service = TradeHistoryBlFactory.getController();
    private FinanceBillVo[] financeBillVos;
    private TradeHistoryVo tradeHistoryVo;

    @Before
    public void setUp() throws Exception {
        tradeHistoryVo = service.query(
                new TradeHistoryQueryVo(new Date(), new Date(), new BillType[]{BillType.FinanceBill},null,null,1));
        financeBillVos = service.query(new FinanceBillQueryVo());
    }

    @Test
    public void queryAll() throws Exception {
        assertNotNull(tradeHistoryVo);
    }

    @Test
    public void queryFinanceBill() throws Exception {
        assertNotNull(financeBillVos);
    }

    @Test
    public void export() throws Exception {
        assertEquals(ResultMessage.Success,
                service.export(service.query(
                        new TradeHistoryQueryVo(new Date(), new Date(), new BillType[]{BillType.FinanceBill},null,null,1))));
    }

}