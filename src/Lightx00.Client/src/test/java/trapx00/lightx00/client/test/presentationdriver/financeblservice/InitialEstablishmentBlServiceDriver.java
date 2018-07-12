package trapx00.lightx00.client.test.presentationdriver.financeblservice;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.client.blservice.financeblservice.InitialEstablishmentBlService;
import trapx00.lightx00.client.blservicestub.financeblservice.InitialEstablishmentBlServiceStub;
import trapx00.lightx00.client.vo.financestaff.SystemSnapshotVo;
import trapx00.lightx00.shared.po.ResultMessage;

import static org.junit.Assert.assertEquals;

public class InitialEstablishmentBlServiceDriver {
    private InitialEstablishmentBlService service = new InitialEstablishmentBlServiceStub();

    private SystemSnapshotVo systemSnapshotVo = null;

    @Before
    public void setUp() throws Exception {
        systemSnapshotVo = service.autofill();
    }

    @Test
    public void autofill() throws Exception {
        assertEquals("123",systemSnapshotVo.getId());
    }

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success, service.submit(systemSnapshotVo));
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success, service.saveAsDraft(systemSnapshotVo));
    }

}