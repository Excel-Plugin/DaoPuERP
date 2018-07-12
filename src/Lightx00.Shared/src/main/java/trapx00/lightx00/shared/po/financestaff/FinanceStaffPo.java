package trapx00.lightx00.shared.po.financestaff;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;

@DatabaseTable(tableName = "FinanceStaff")
public class FinanceStaffPo extends EmployeePo {
    @DatabaseField
    private boolean root;

    public FinanceStaffPo(String id, String name, Date workSince, String password, EmployeeState state, boolean root) {
        super(id, name, workSince, EmployeePosition.FinanceStaff, password, state);
        this.root = root;
    }

    public FinanceStaffPo() {
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public boolean isRoot() {
        return root;
    }
}


