package trapx00.lightx00.server.test.data.inventorydata;

import com.j256.ormlite.dao.Dao;
import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.server.data.inventorydata.factory.PurchaseBillDataDaoFactory;
import trapx00.lightx00.server.data.inventorydata.factory.PurchaseBillDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseBillDataService;
import trapx00.lightx00.shared.exception.database.BillInvalidStateException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.PurchaseBillPo;
import trapx00.lightx00.shared.queryvo.PurchaseBillQueryVo;
import trapx00.lightx00.shared.util.BillHelper;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

public class PurchaseBillDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final PurchaseBillPo bill = new PurchaseBillPo("JHD-20171122-00001", new Date(), BillState.Draft, "0", 0, "0", "", 0, null);
    private Dao<PurchaseBillPo, String> dao = PurchaseBillDataDaoFactory.getPurchaseBillDao();
    private PurchaseBillDataService service = PurchaseBillDataFactory.getService();

    @Before
    public void setUp() throws Exception {
        dao.deleteById(bill.getId());
    }

    @Test
    public void submitOne() throws Exception {
        try {
            service.submit(bill);
            assertTrue(dao.idExists(bill.getId()));
        } finally {
            dao.deleteById(bill.getId());
        }

    }

    @Test(expected = IdExistsException.class)
    public void submitButIdExistsAndItIsNotDraft() throws Exception {
        try {
            bill.setState(BillState.WaitingForApproval);
            service.submit(bill);
            service.submit(bill);
        } finally {
            bill.setState(BillState.Draft);
            dao.deleteById(bill.getId());
        }

    }

    @Test
    public void submitButActuallyUpdateIfExistingIsDraft() throws Exception {
        try {
            service.submit(bill);
            service.submit(bill);
        } finally {
            dao.deleteById(bill.getId());
        }


    }

    @Test
    public void activate() throws Exception {
        try {
            bill.setState(BillState.Approved);
            service.submit(bill);
            service.activate(bill.getId());
            assertEquals(BillState.Activated, dao.queryForEq("id", bill.getId()).get(0).getState());
        } finally {
            bill.setState(BillState.Draft);
            dao.deleteById(bill.getId());
        }
    }

    @Test(expected = BillInvalidStateException.class)
    public void activateButTheStateIsNotWaitingForApproval() throws Exception {
        try {
            service.submit(bill);
            service.activate(bill.getId());
        } finally {
            dao.deleteById(bill.getId());
        }
    }

    @Test
    public void abandonDraft() throws Exception {
        service.submit(bill);
        service.abandon(bill.getId());
        assertFalse(dao.idExists(bill.getId()));
    }

    @Test
    public void abandonWaitingForApprovalBill() throws Exception {
        bill.setState(BillState.WaitingForApproval);
        service.submit(bill);
        service.abandon(bill.getId());
        assertEquals(BillState.Abandoned, dao.queryForEq("id", bill.getId()).get(0).getState());
    }

    @Test
    public void query() throws Exception {
        service.submit(bill);
        assertEquals(1, service.query(new PurchaseBillQueryVo().eq("id", bill.getId())).length);
        assertEquals(0, service.query(new PurchaseBillQueryVo().eq("operatorId", "12")).length);
    }

    @Test
    public void getId() throws Exception {
        bill.setId(String.format("JHD-%s-00001", BillHelper.currentDateStringForBill()));
        service.submit(bill);
        assertEquals("00002", service.getId().split("-")[2]);
    }

    @Test(expected = NoMoreBillException.class)
    public void getIdTooMany() throws Exception {
        try {
            bill.setId(String.format("JHD-%s-99999", BillHelper.currentDateStringForBill()));
            service.submit(bill);
            service.getId();
        } finally {
            dao.deleteById(bill.getId());
            bill.setId("JHD-20171122-00001");
        }
    }

    @Test
    public void changeState() throws Exception {
        bill.setState(BillState.WaitingForApproval);
        dao.create(bill);
        String id = dao.extractId(bill);
        try {
            service.approvalComplete(bill.getId(),BillState.Approved);
            assertEquals(BillState.Approved, dao.queryForId(bill.getId()).getState());
        } finally {
            dao.deleteById(id);
            bill.setState(BillState.Draft);
        }
    }

    @Test(expected = BillInvalidStateException.class)
    public void changeStateInvalidState() throws Exception {
        dao.create(bill);
        String id = dao.extractId(bill);
        try {
            service.approvalComplete(bill.getId(),BillState.Approved);
        } finally {
            dao.deleteById(id);
        }
    }
}
