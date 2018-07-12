package trapx00.lightx00.server.test.data.inventorydata;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.server.data.inventorydata.factory.InventoryDataDaoFactory;
import trapx00.lightx00.server.data.inventorydata.factory.InventoryGiftDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.inventorydataservice.InventoryGiftDataService;
import trapx00.lightx00.shared.exception.database.BillInvalidStateException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;
import trapx00.lightx00.shared.po.inventorystaff.InventoryBillType;
import trapx00.lightx00.shared.po.inventorystaff.InventoryGiftPo;
import trapx00.lightx00.shared.util.BillHelper;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

public class InventoryGiftDataControllerTest {

    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Dao<InventoryGiftPo, String> dao = InventoryDataDaoFactory.getInventoryGiftDao();
    private InventoryGiftDataService service = InventoryGiftDataFactory.getService();
    private final InventoryGiftPo bill = new InventoryGiftPo(BillType.InventoryBill,"GIFT-20171126-00001",new Date(),BillState.Draft,
            InventoryBillType.Gift,null,null);

    @Before
    public void setUp() throws Exception {
       ;
    }
    private void resetTable() throws Exception {
        TableUtils.dropTable(dao.getConnectionSource(),InventoryGiftPo.class,true);
        TableUtils.createTable(dao.getConnectionSource(), InventoryGiftPo.class);
    }
    @Test
    public void submitOne() throws Exception {
        try {
            service.submit(bill);
            assertTrue(dao.idExists(bill.getId()));
        } finally {
            resetTable();
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
            resetTable();
        }
    }

    @Test
    public void submitButActuallyUpdateIfExistingIsDraft() throws Exception {
        try {
            service.submit(bill);
            service.submit(bill);
        } finally {
            resetTable();
        }
    }

    @Test
    public void activate() throws Exception {
        try {
            bill.setState(BillState.WaitingForApproval);
            service.submit(bill);
            service.approvalComplete(bill.getId(),BillState.Approved);
            assertEquals(BillState.Approved, dao.queryForEq("id",bill.getId()).get(0).getState());
        } finally {
            bill.setState(BillState.Draft);
            resetTable();
        }
    }

    @Test(expected = BillInvalidStateException.class)
    public void activateButTheStateIsNotWaitingForApproval() throws Exception {
        try {
            service.submit(bill);
            service.activate(bill.getId());
        } finally {
            resetTable();
        }
    }

    @Test
    public void abandonDraft() throws Exception {
        service.submit(bill);
        service.abandon(bill.getId());
        assertFalse(dao.idExists(bill.getId()));
        resetTable();
    }

    @Test
    public void abandonWaitingForApprovalBill() throws Exception {
        bill.setState(BillState.WaitingForApproval);
        service.submit(bill);
        service.abandon(bill.getId());
        assertEquals(BillState.Abandoned, dao.queryForEq("id", bill.getId()).get(0).getState());
        resetTable();
    }



    @Test
    public void getId() throws Exception {
        bill.setId(String.format("GIFT-%s-00001", BillHelper.currentDateStringForBill()));
        service.submit(bill);
        assertEquals("00002", service.getId().split("-")[2]);
        resetTable();
    }

    @Test(expected = NoMoreBillException.class)
    public void getIdTooMany() throws Exception {
        try {
            bill.setId(String.format("GIFT-%s-99999", BillHelper.currentDateStringForBill()));
            service.submit(bill);
            service.getId();
        } finally {
            bill.setId("GIFT-20171126-00001");
            resetTable();
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
            resetTable();
            bill.setState(BillState.Draft);
        }
    }

    @Test(expected = BillInvalidStateException.class)
    public void changeStateInvalidState() throws Exception {
        dao.create(bill);
        String id = dao.extractId(bill);
        try {
            service.approvalComplete(bill.getId(), BillState.Approved);
        } finally {
            resetTable();
        }
    }
}