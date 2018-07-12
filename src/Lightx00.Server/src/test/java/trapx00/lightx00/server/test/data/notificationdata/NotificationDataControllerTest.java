package trapx00.lightx00.server.test.data.notificationdata;

import com.j256.ormlite.dao.Dao;
import org.junit.Test;
import trapx00.lightx00.server.data.notificationdata.factory.NotificationDataDaoFactory;
import trapx00.lightx00.server.data.notificationdata.factory.NotificationDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.notificationdataservice.NotificationDataService;
import trapx00.lightx00.shared.po.notification.NotificationPo;
import trapx00.lightx00.shared.po.notification.NotificationType;
import trapx00.lightx00.shared.queryvo.NotificationQueryVo;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class NotificationDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Dao<NotificationPo, Integer> dao = NotificationDataDaoFactory.getDao();
    private NotificationPo dumbPo = new NotificationPo(new java.util.Date(),"123",new String[]{"123","1234"}, NotificationType.Others,"123");
    private NotificationDataService service = NotificationDataFactory.getService();
    @Test
    public void query() throws Exception {
        dao.create(dumbPo);
        int id = dao.extractId(dumbPo);
        try {
            assertEquals(1, service.query(new NotificationQueryVo().eq("senderId","123")).length);
            assertEquals(1, service.query(new NotificationQueryVo()).length);
        } finally {
            dao.deleteById(id);
        }
    }

    @Test
    public void acknowledge() throws Exception {
        dao.create(dumbPo);
        int id = dao.extractId(dumbPo);

        long previous = dao.countOf();
        try {
            service.acknowledge(id);
            assertEquals(previous-1, dao.countOf());
        } finally {
            dao.deleteById(id);
        }
    }

    @Test
    public void add() throws Exception {
        long previous = dao.countOf();
        service.add(dumbPo);
        int id = dao.extractId(dumbPo);
        try {
            assertEquals(previous + 1, dao.countOf());
        } finally {
          dao.deleteById(id);
        }


    }

}