package trapx00.lightx00.client.test.bl.financebl;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.client.bl.financebl.ReceivalBillBlController;
import trapx00.lightx00.client.bl.financebl.factory.ReceivalBillBlFactory;
import trapx00.lightx00.client.vo.financestaff.ReceivalBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.ReceivalBillQueryVo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReceivalBillBlControllerTest {
    private ReceivalBillBlController controller = ReceivalBillBlFactory.getController();
    private ReceivalBillVo bill = null;
    @Before
    public void setUp() {
        bill = controller.query(new ReceivalBillQueryVo())[0];
    }

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success, controller.submit(bill));
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success, controller.saveAsDraft(bill));
    }

    @Test
    public void getId() throws Exception {
        assertEquals("SKD-20171112-00001", controller.getId());
    }

    @Test
    public void deleteDraft() throws Exception {
        assertEquals(ResultMessage.Success, controller.deleteDraft("SKD-20171112-00001"));
    }

    @Test
    public void abandon() throws Exception {
        assertEquals(ResultMessage.Success, controller.abandon("SKD-20171112-00001"));
    }

    @Test
    public void activate() throws Exception {
        assertEquals(ResultMessage.Success, controller.activate("SKD-20171112-00001"));
    }

    @Test
    public void query() throws Exception {
        assertNotNull(bill);
    }

}