package trapx00.lightx00.client.test.presentationdriver.financeblservice;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.client.blservice.financeblservice.TradeSituationBlService;
import trapx00.lightx00.client.blservicestub.financeblservice.TradeSituationBlServiceStub;
import trapx00.lightx00.client.vo.financestaff.TradeSituationVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TradeSituationBlServiceDriver {
    private TradeSituationBlService service = new TradeSituationBlServiceStub();
    private TradeSituationVo situationVo;

    @Before
    public void setUp() throws Exception {
        situationVo = service.query(new Date(), new Date());
    }

    @Test
    public void query() throws Exception {
        assertEquals(123,situationVo.getCommodityCost(),0.1);
    }

    @Test
    public void export() throws Exception {
        assertEquals(ResultMessage.Success, service.export(situationVo));
    }

}