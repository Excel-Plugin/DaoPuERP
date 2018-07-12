package trapx00.lightx00.shared.po.inventorystaff;

import com.j256.ormlite.table.DatabaseTable;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;

@DatabaseTable(tableName = "InventoryStaff")
public class InventoryStaffPo extends EmployeePo {
    public InventoryStaffPo(String id, String name, Date workSince, String password, EmployeeState state) {
        super(id, name, workSince, EmployeePosition.InventoryStaff, password,state);
    }

    public InventoryStaffPo() {
    }
}
