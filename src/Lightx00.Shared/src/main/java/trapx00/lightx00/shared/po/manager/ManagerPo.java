package trapx00.lightx00.shared.po.manager;

import com.j256.ormlite.table.DatabaseTable;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;

@DatabaseTable(tableName = "Manager")
public class ManagerPo extends EmployeePo {
    public ManagerPo(String id, String name, Date workSince, String password, EmployeeState state) {
        super(id, name, workSince, EmployeePosition.Manager, password,state);
    }

    public ManagerPo() {
    }
}

