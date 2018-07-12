package trapx00.lightx00.client.test.presentationdriver.logblservice;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.client.blservice.logblservice.LogBlService;
import trapx00.lightx00.client.blservicestub.logblservice.LogBlServiceStub;
import trapx00.lightx00.client.vo.log.LogVo;
import trapx00.lightx00.shared.queryvo.LogQueryVo;

import static org.junit.Assert.assertEquals;

public class LogBlServiceDriver {
    private LogBlService service = new LogBlServiceStub();
    private LogVo[] logs;
    @Before
    public void setUp() throws Exception {
        logs = service.query(new LogQueryVo());
    }

    @Test
    public void query() throws Exception {
        assertEquals("123",logs[0].getContent());
    }

}