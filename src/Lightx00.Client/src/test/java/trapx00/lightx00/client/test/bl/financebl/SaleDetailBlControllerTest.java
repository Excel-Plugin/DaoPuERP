package trapx00.lightx00.client.test.bl.financebl;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.client.bl.financebl.SaleDetailBlController;
import trapx00.lightx00.client.bl.financebl.factory.SaleDetailBlFactory;
import trapx00.lightx00.client.vo.financestaff.SaleDetailQueryVo;
import trapx00.lightx00.client.vo.financestaff.SaleDetailVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class SaleDetailBlControllerTest {
    private SaleDetailBlController controller = SaleDetailBlFactory.getController();

    private SaleDetailVo saleDetailVo;
    @Before
    public void setUp() {
        saleDetailVo = controller.query( new SaleDetailQueryVo(new Date(), new Date(),"123",null,null,1));
    }

    @Test
    public void query() throws Exception {
        assertEquals(1, saleDetailVo.getRecords().length);
    }

    @Test
    public void export() throws Exception {
        assertEquals(ResultMessage.Success, controller.export(saleDetailVo));
    }

}