package trapx00.lightx00.client.vo.inventorystaff;

import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;

public class InventoryStaffVo extends EmployeeVo {
    public InventoryStaffVo(String id, String name, Date workSince, String password, EmployeeState state) {
        super(id, name, workSince, EmployeePosition.InventoryStaff, password, state);
    }

}
