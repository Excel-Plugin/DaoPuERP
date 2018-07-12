package trapx00.lightx00.shared.bldriver.admindataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.admindataservice.UserManagementDataService;
import trapx00.lightx00.shared.dataservicestub.admindataservice.UserManagementDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.po.manager.ManagerPo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UserManagementDataServiceDriver {
    private UserManagementDataService service = new UserManagementDataServiceStub();
    private EmployeePo account = new ManagerPo("0001","张三",new Date(),"10081", EmployeeState.Practice);

    @Test
    public void query() throws Exception {
        assertEquals("0001",service.query(new UserAccountQueryVo())[0].getId());
    }

    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success,service.add(account));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success,service.modify(account));
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,service.delete(account));
    }

}