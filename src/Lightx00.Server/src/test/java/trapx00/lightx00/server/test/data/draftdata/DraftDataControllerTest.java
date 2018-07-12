package trapx00.lightx00.server.test.data.draftdata;

import com.j256.ormlite.dao.Dao;
import org.junit.Test;
import trapx00.lightx00.server.data.draftdata.factory.DraftDataDaoFactory;
import trapx00.lightx00.server.data.draftdata.factory.DraftDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.draftdataservice.DraftDataService;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.po.draft.DraftPo;
import trapx00.lightx00.shared.po.draft.DraftType;
import trapx00.lightx00.shared.queryvo.DraftQueryVo;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DraftDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Dao<DraftPo, Integer> dao = DraftDataDaoFactory.getDao();
    private DraftDataService service = DraftDataFactory.getService();
    private DraftPo dumbDraftPo = new DraftPo(new Date(), "123", "XJFYD-20171112-00001", DraftType.Bill);
    private DraftPo anotherDumbDraftPo = new DraftPo(new Date(), "123", "CXDJ-00001", DraftType.Promotion);

    @Test
    public void query() throws Exception {
        dao.create(dumbDraftPo);
        dao.create(anotherDumbDraftPo);
        int id1 = dao.extractId(dumbDraftPo), id2 = dao.extractId(anotherDumbDraftPo);
        try {
            assertEquals(2, service.query(new DraftQueryVo()).length);
            assertEquals(1, service.query(new DraftQueryVo().eq("id",id1)).length);
            assertEquals(0, service.query(new DraftQueryVo().eq("draftableId","XJFYD-20171113-00001")).length);
        } finally {
            dao.deleteById(id1);
            dao.deleteById(id2);
        }
    }

    @Test
    public void add() throws Exception {
        long previous = dao.countOf();
        service.add(dumbDraftPo);
        int id = dao.extractId(dumbDraftPo);
        try {
            assertEquals(previous + 1, dao.countOf());
        } finally {
            dao.deleteById(id);
        }
    }


    @Test
    public void delete() throws Exception {

        dao.create(dumbDraftPo);
        int id = dao.extractId(dumbDraftPo);
        long previous = dao.countOf();
        try {
            service.delete(id);
            assertEquals(previous - 1, dao.countOf());
        } finally {
            dao.deleteById(id);
        }
    }

    @Test(expected = IdNotExistsException.class)
    public void deleteNotExist() throws Exception {
        service.delete(1000);
    }

}