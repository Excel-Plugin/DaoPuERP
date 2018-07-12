package trapx00.lightx00.client.test.bl.financebl;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.client.bl.financebl.TradeSituationBlController;
import trapx00.lightx00.client.bl.financebl.factory.TradeSituationBlFactory;
import trapx00.lightx00.client.vo.financestaff.TradeSituationVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TradeSituationBlControllerTest {
    private TradeSituationBlController service = TradeSituationBlFactory.getController();
    private TradeSituationVo situationVo;

    @Before
    public void setUp() throws Exception {
        situationVo = service.query(new Date(), new Date());
    }

    @Test
    public void query() throws Exception {
        assertEquals(0.0,situationVo.getCommodityCost(),0.1);
    }

    @Test
    public void export() throws Exception {
        assertEquals(ResultMessage.Success, service.export(situationVo));
    }

}