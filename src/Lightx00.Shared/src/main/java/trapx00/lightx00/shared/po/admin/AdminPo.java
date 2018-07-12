package trapx00.lightx00.shared.po.admin;

import com.j256.ormlite.table.DatabaseTable;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;


@DatabaseTable(tableName = "Admin")
public class AdminPo extends EmployeePo {

    public AdminPo(String id, String name, Date workSince, String password, EmployeeState state) {
        super(id, name, workSince, EmployeePosition.Admin, password, state);
    }

    public AdminPo() {
    }

}

