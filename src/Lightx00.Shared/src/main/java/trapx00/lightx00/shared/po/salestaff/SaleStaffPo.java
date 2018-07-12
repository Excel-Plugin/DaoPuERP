package trapx00.lightx00.shared.po.salestaff;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;

@DatabaseTable(tableName = "SaleStaffPo")
public class SaleStaffPo extends EmployeePo {
    @DatabaseField
    private SaleStaffType saleStaffType;
    @DatabaseField
    private boolean root;

    public SaleStaffPo(String id, String name, Date workSince, String password, SaleStaffType saleStaffType, EmployeeState state, boolean root) {
        super(id, name, workSince, EmployeePosition.SaleStaff, password,state);
        this.saleStaffType = saleStaffType;
        this.root = root;
    }

    public SaleStaffPo() {
    }

    public SaleStaffType getSaleStaffType() {
        return saleStaffType;
    }

    public void setSaleStaffType(SaleStaffType saleStaffType) {
        this.saleStaffType = saleStaffType;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }
}


