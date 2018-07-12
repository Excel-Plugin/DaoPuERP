package trapx00.lightx00.shared.bldriver.logdataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.logdataservice.LogBackupDataService;
import trapx00.lightx00.shared.dataservicestub.logdataservice.LogBackupDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;

import static org.junit.Assert.assertEquals;

public class LogBackupDataServiceDriver {
    private LogBackupDataService service = new LogBackupDataServiceStub();

    @Test
    public void backupLog() throws Exception {
        assertEquals(ResultMessage.Success, service.backupLog());
    }

    @Test
    public void fetchCloudLog() throws Exception {
        assertEquals("www.123.com", service.fetchCloudLog());
    }
}
