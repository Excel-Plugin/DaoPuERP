package trapx00.lightx00.client.test.bl.financebl;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.client.bl.financebl.InitialEstablishmentBlController;
import trapx00.lightx00.client.bl.financebl.factory.InitialEstablishmentBlFactory;
import trapx00.lightx00.client.vo.financestaff.SystemSnapshotVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.SystemSnapshotQueryVo;

import static org.junit.Assert.assertEquals;

public class InitialEstablishmentBlControllerTest {
    private InitialEstablishmentBlController controller = InitialEstablishmentBlFactory.getController();
    private SystemSnapshotVo snapshotVo = null;
    @Before
    public void setUp() {
        snapshotVo = controller.autofill();
    }

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success, controller.submit(snapshotVo));
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success, controller.saveAsDraft(snapshotVo));
    }

    @Test
    public void deleteDraft() throws Exception {
        assertEquals(ResultMessage.Success, controller.deleteDraft("SYSSNAP-20171112-00001"));
    }

    @Test
    public void abandon() throws Exception {
        assertEquals(ResultMessage.Success, controller.abandon("SYSSNAP-20171112-00001"));
    }

    @Test
    public void activate() throws Exception {
        assertEquals(ResultMessage.Success, controller.activate("SYSSNAP-20171112-00001"));
    }

    @Test
    public void query() throws Exception {
        assertEquals(1, controller.query(new SystemSnapshotQueryVo()).length);
    }

}