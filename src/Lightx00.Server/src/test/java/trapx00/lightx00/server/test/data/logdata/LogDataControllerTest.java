package trapx00.lightx00.server.test.data.logdata;

import com.j256.ormlite.dao.Dao;
import org.junit.Test;
import trapx00.lightx00.server.data.logdata.factory.LogDataDaoFactory;
import trapx00.lightx00.server.data.logdata.factory.LogDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.logdataservice.LogDataService;
import trapx00.lightx00.shared.po.log.LogPo;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.LogQueryVo;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unchecked")
public class LogDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private LogDataService service = LogDataFactory.getService();
    private Dao<LogPo, Integer> dao = LogDataDaoFactory.getLogDao();
    private LogPo logPo = new LogPo(new Date(), LogSeverity.Success, "123");

    @Test
    public void log() throws Exception {
        long previous = dao.countOf();
        service.log(logPo.getSeverity(), logPo.getContent());
        try {
            assertEquals(1, dao.countOf()-previous);
        } finally {
           dao.deleteBuilder().delete();
        }

    }

    @Test
    public void query() throws Exception {
        try {
            service.log(logPo.getSeverity(), logPo.getContent());
            service.log(LogSeverity.Failure, "failure");
            assertEquals(2, service.query(new LogQueryVo()).length);
            assertEquals(1, service.query(new LogQueryVo().eq("severity",LogSeverity.Failure)).length);

        } finally {
            dao.deleteBuilder().delete();
        }
    }

}