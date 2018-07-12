package trapx00.lightx00.client.test.bl.adminbl;

import org.junit.Test;
import trapx00.lightx00.client.bl.adminbl.UserManagementBlController;
import trapx00.lightx00.client.bl.adminbl.factory.UserManagementBlFactory;
import trapx00.lightx00.client.vo.financestaff.FinanceStaffVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryStaffVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.queryvo.SpecificUserAccountQueryVo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UserManagementBlControllerTest {
    private UserManagementBlController controller = UserManagementBlFactory.getController();
    private FinanceStaffVo account = new FinanceStaffVo("10001","财务经理",new Date(),"123456", EmployeeState.Active,true);
    private InventoryStaffVo inventoryStaffVo = new InventoryStaffVo("10002","库存经理",new Date(),"123456", EmployeeState.Active);

    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success,controller.add(inventoryStaffVo));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success,controller.modify(account));
    }

    @Test
    public void query() throws Exception {
        assertEquals("0001",controller.query(new UserAccountQueryVo().addQueryVoForAllEmployeePosition(new SpecificUserAccountQueryVo()))[0].getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,controller.delete(account));
    }

}