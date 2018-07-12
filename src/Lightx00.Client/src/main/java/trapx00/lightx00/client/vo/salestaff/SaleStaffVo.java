package trapx00.lightx00.client.vo.salestaff;

import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.po.salestaff.SaleStaffType;

import java.util.Date;

public class SaleStaffVo extends EmployeeVo {
    SaleStaffType saleStaffType;
    boolean root;

    public SaleStaffVo(String id, String name, Date workSince, String password, EmployeeState state, boolean root,SaleStaffType saleStaffType) {
        super(id, name, workSince, EmployeePosition.SaleStaff, password, state);
        this.saleStaffType = saleStaffType;
        this.root = root;
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

    public String getRoot() {
        if(root)
            return "是";
        else
            return "否";
    }
}
