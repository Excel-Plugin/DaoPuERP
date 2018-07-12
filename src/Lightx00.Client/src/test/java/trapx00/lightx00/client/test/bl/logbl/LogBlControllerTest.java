package trapx00.lightx00.client.test.bl.logbl;

import org.junit.Test;
import trapx00.lightx00.client.bl.logbl.LogBlController;
import trapx00.lightx00.client.bl.logbl.factory.LogBlFactory;
import trapx00.lightx00.shared.queryvo.LogQueryVo;

import static org.junit.Assert.assertNotNull;

public class LogBlControllerTest {
    private LogBlController controller = LogBlFactory.getController();
    @Test
    public void log() throws Exception {

    }

    @Test
    public void query() throws Exception {
        assertNotNull(controller.query(new LogQueryVo()));
    }

}