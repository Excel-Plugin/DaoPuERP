package trapx00.lightx00.client.test.presentationdriver.logblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.logblservice.LogBackupBlService;
import trapx00.lightx00.client.blservicestub.logblservice.LogBackupBlServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;

import static org.junit.Assert.assertEquals;

public class LogBackupBlServiceDriver {
    private LogBackupBlService service = new LogBackupBlServiceStub();


    @Test
    public void backupLog() throws Exception {
        assertEquals(ResultMessage.Success, service.backupLog());
    }

    @Test
    public void fetchCloudLog() throws Exception {
        assertEquals("www.123.com", service.fetchCloudLog().toString());
    }
}
