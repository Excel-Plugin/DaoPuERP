package trapx00.lightx00.shared.bldriver.logdataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.logdataservice.LogDataService;
import trapx00.lightx00.shared.dataservicestub.logdataservice.LogDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.LogQueryVo;

import static org.junit.Assert.assertEquals;

public class LogDataServiceDriver {
    private LogDataService service = new LogDataServiceStub();
    @Test
    public void log() throws Exception {
        assertEquals(ResultMessage.Success, service.log(LogSeverity.Success,"123"));
    }

    @Test
    public void query() throws Exception {
        assertEquals("123", service.query(new LogQueryVo())[0].getContent());
    }

}