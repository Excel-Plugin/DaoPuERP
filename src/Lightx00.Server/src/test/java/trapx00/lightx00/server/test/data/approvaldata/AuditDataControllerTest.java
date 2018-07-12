package trapx00.lightx00.server.test.data.approvaldata;

import com.j256.ormlite.dao.Dao;
import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.server.data.approvaldata.factory.AuditDataDaoFactory;
import trapx00.lightx00.server.data.approvaldata.factory.AuditDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.approvaldataservice.AuditDataService;
import trapx00.lightx00.shared.po.bill.BillPo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.CashBillItem;
import trapx00.lightx00.shared.po.financestaff.CashBillPo;
import trapx00.lightx00.shared.po.manager.AuditIdPo;
import trapx00.lightx00.shared.queryvo.AuditIdQueryVo;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

public class AuditDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Dao<AuditIdPo, String> dao = AuditDataDaoFactory.getAuditDao();
    private AuditDataService service = AuditDataFactory.getService();
    private final BillPo bill = new CashBillPo("XJFYD-20171112-00001", new Date(), BillState.WaitingForApproval, "123",1, new CashBillItem[] { new CashBillItem("123",0,"123") });
    private final AuditIdPo billInfo1 = new AuditIdPo("XJFYD-20171112-00001",new Date());
    private final AuditIdPo billInfo2 = new AuditIdPo("XJFYD-20171112-00002",new Date());
    private final AuditIdPo billInfo3 = new AuditIdPo("JHXSD-20171111-00001",new Date());

    @Before
    public void setUp() throws Exception {
        dao.deleteById(billInfo1.getId());
        dao.deleteById(billInfo2.getId());
        dao.deleteById(billInfo3.getId());
    }

    @Test
    public void query() throws Exception {

        try {
            dao.create(billInfo1);
            dao.create(billInfo2);
            dao.create(billInfo3);
            assertEquals(3, service.query(new AuditIdQueryVo()).length);
        } finally {
            dao.deleteById(billInfo1.getId());
            dao.deleteById(billInfo2.getId());
            dao.deleteById(billInfo3.getId());
        }
    }

    @Test
    public void reject() throws Exception {
        try {
            dao.create(billInfo2);
            service.reject(billInfo2);
            assertFalse(dao.idExists(billInfo2.getId()));
        } finally {
        }
    }

    @Test
    public void pass() throws Exception {
        try {
            dao.create(billInfo2);
            service.pass(billInfo2);
            assertFalse(dao.idExists(billInfo2.getId()));
        } finally {
        }
    }

    @Test
    public void requestApproval() throws Exception {
        try {
            service.requestApproval(bill.getId());
            assertTrue(dao.idExists(bill.getId()));
        } finally {
            dao.deleteById(bill.getId());
        }
    }

}