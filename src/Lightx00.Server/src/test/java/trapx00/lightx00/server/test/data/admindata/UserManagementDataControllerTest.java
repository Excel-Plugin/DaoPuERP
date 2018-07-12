package trapx00.lightx00.server.test.data.admindata;

import com.j256.ormlite.dao.Dao;
import org.junit.Test;
import trapx00.lightx00.server.data.admindata.factory.AdminDataDaoFactory;
import trapx00.lightx00.server.data.admindata.factory.AdminDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.admindataservice.UserManagementDataService;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.po.financestaff.FinanceStaffPo;
import trapx00.lightx00.shared.po.inventorystaff.InventoryStaffPo;
import trapx00.lightx00.shared.po.manager.ManagerPo;
import trapx00.lightx00.shared.queryvo.SpecificUserAccountQueryVo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SuppressWarnings("unchecked")
public class UserManagementDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private UserManagementDataService service = AdminDataFactory.getService();
    private Dao<ManagerPo, String> managerDao = AdminDataDaoFactory.getManagerDao();
    private Dao<FinanceStaffPo, String> financeStaffDao = AdminDataDaoFactory.getFinanceStaffDao();
    private Dao<InventoryStaffPo,String> inventoryStaffPos=AdminDataDaoFactory.getInventoryStaffDao();
    private ManagerPo account = new ManagerPo("001","总经理",new Date(),"123456", EmployeeState.Active);
    private FinanceStaffPo financeStaffPo = new FinanceStaffPo("10002","财务人员",new Date(), "123456",EmployeeState.Active,true);
    private InventoryStaffPo inventoryStaffPo=new InventoryStaffPo("10003","库存管理",new Date(),"1215",EmployeeState.Active);

    @Test
    public void add1() throws  Exception{
        inventoryStaffPos.create(inventoryStaffPo);
    }
    @Test
    public void queryOneTable() throws Exception {
        ManagerPo anotherManagerPo = new ManagerPo("10005","张三", new Date(),"12345", EmployeeState.Active);
        managerDao.create(account);
        managerDao.create(anotherManagerPo);
        try {
            assertEquals(2, service.query(new UserAccountQueryVo().addQueryVoForOneEmployeePosition(EmployeePosition.Manager, new SpecificUserAccountQueryVo())).length);
            assertEquals(1, service.query(new UserAccountQueryVo().addQueryVoForAllEmployeePosition((SpecificUserAccountQueryVo) new SpecificUserAccountQueryVo().eq("name","总经理"))).length);
            assertEquals(0, service.query(new UserAccountQueryVo().addQueryVoForOneEmployeePosition(EmployeePosition.Admin, new SpecificUserAccountQueryVo())).length);
        } finally {
            managerDao.deleteById(account.getId());
            managerDao.deleteById(anotherManagerPo.getId());
        }
    }

    @Test
    public void queryMultiTable() throws Exception {
        managerDao.create(account);
        financeStaffDao.create(financeStaffPo);
        try {
            assertEquals(2, service.query(new UserAccountQueryVo().addQueryVoForAllEmployeePosition(new SpecificUserAccountQueryVo())).length);
            assertEquals("001", service.query(new UserAccountQueryVo().addQueryVoForOneEmployeePosition(EmployeePosition.Manager, new SpecificUserAccountQueryVo()))[0].getId());
            assertEquals(0, service.query(new UserAccountQueryVo().addQueryVoForOneEmployeePosition(EmployeePosition.InventoryStaff, new SpecificUserAccountQueryVo())).length);
        } finally {
            managerDao.deleteById(account.getId());
            financeStaffDao.deleteById(financeStaffPo.getId());
        }

    }

    @Test
    public void addOne() throws Exception {
        try {
            long previous = managerDao.countOf();
            service.add(account);
            assertEquals(previous + 1, managerDao.countOf());
        } finally {
            managerDao.deleteById(account.getId());
        }
    }

    @Test(expected = IdExistsException.class)
    public void addOneIdExist() throws Exception {
        try {
            service.add(account);
            service.add(account);
        } finally {
            managerDao.deleteById(account.getId());
        }
    }

    @Test
    public void addMultipleInOneTable() throws Exception {
        ManagerPo another = new ManagerPo("123","123",new Date(), "123",EmployeeState.Active);
        try {
            long previous = managerDao.countOf();
            service.add(account);
            service.add(another);
            assertEquals(previous + 2, managerDao.countOf());
        } finally {
            managerDao.deleteById(account.getId());
            managerDao.deleteById(another.getId());
        }
    }

    @Test
    public void addMultipleInDifferentTable() throws Exception {
        try {
            long previousManager = managerDao.countOf();
            long previousFinance = financeStaffDao.countOf();
            service.add(account);
            service.add(financeStaffPo);
            assertEquals(previousManager + 1, managerDao.countOf());
            assertEquals(previousFinance + 1, financeStaffDao.countOf());
        } finally {
            managerDao.deleteById(account.getId());
            financeStaffDao.deleteById(financeStaffPo.getId());
        }
    }

    @Test
    public void modify() throws Exception {
        try {
            managerDao.create(account);
            ManagerPo addedPo = managerDao.queryForId(account.getId());
            addedPo.setName("张六");
            service.modify(addedPo);
            addedPo = managerDao.queryForId(account.getId());
            assertEquals("张六", addedPo.getName());
        } finally {
            managerDao.deleteById(account.getId());
        }
    }

    @Test
    public void delete() throws Exception {
        try {
            managerDao.create(account);
            service.delete(account);
            assertNull(managerDao.queryForId(account.getId()));
        } finally {
            managerDao.deleteById(account.getId());
        }
    }

}