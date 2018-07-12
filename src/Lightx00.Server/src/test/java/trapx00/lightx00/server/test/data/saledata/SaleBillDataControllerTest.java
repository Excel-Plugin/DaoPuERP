package trapx00.lightx00.server.test.data.saledata;

import com.j256.ormlite.dao.Dao;
import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.server.data.saledata.factory.SaleBillDataDaoFactory;
import trapx00.lightx00.server.data.saledata.factory.SaleBillDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.saledataservice.SaleBillDataService;
import trapx00.lightx00.shared.exception.database.BillInvalidStateException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.po.salestaff.SaleBillPo;
import trapx00.lightx00.shared.queryvo.SaleBillQueryVo;
import trapx00.lightx00.shared.util.BillHelper;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

public class SaleBillDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final SaleBillPo bill = new SaleBillPo("XSD-20171122-00001", new Date(), BillState.Draft, "0", "0", "0", 0, null, 0, 0, 0, 0, "",1,"2",new CommodityItem[] { new CommodityItem("123","1","1",1,1,1,"") }, 10);
    private Dao<SaleBillPo, String> dao = SaleBillDataDaoFactory.getSaleBillDao();
    private SaleBillDataService service = SaleBillDataFactory.getService();

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
        assertEquals(1, service.query(new SaleBillQueryVo().eq("id", bill.getId())).length);
        assertEquals(0, service.query(new SaleBillQueryVo().eq("operatorId", "12")).length);
    }

    @Test
    public void getId() throws Exception {
        bill.setId(String.format("XSD-%s-00001", BillHelper.currentDateStringForBill()));
        service.submit(bill);
        assertEquals("00002", service.getId().split("-")[2]);
    }

    @Test(expected = NoMoreBillException.class)
    public void getIdTooMany() throws Exception {
        try {
            bill.setId(String.format("XSD-%s-99999", BillHelper.currentDateStringForBill()));
            service.submit(bill);
            service.getId();
        } finally {
            dao.deleteById(bill.getId());
            bill.setId("XSD-20171122-00001");
        }
    }
}
