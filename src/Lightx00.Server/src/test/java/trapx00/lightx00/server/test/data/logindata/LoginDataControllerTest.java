package trapx00.lightx00.server.test.data.logindata;

import com.j256.ormlite.dao.Dao;
import org.junit.Test;
import trapx00.lightx00.server.data.admindata.factory.AdminDataDaoFactory;
import trapx00.lightx00.server.data.logindata.factory.LoginDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.logindataservice.LoginDataService;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.po.financestaff.FinanceStaffPo;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LoginDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Dao<FinanceStaffPo, String> dao = AdminDataDaoFactory.getFinanceStaffDao();
    private LoginDataService service = LoginDataFactory.getService();
    private FinanceStaffPo employeePo = new FinanceStaffPo("0001","123",new Date(),"123", EmployeeState.Active,true);
    @Test
    public void loginSuccess() throws Exception {
        dao.create(employeePo);
        String id = dao.extractId(employeePo);
        try {
            assertNotNull(service.login("123","123"));
        } finally {
            dao.deleteById(id);
        }
    }

    @Test
    public void loginFailed() throws Exception {
        dao.create(employeePo);
        String id = dao.extractId(employeePo);
        try {
            assertNull(service.login("123","1233"));
        } finally {
            dao.deleteById(id);
        }
    }

}