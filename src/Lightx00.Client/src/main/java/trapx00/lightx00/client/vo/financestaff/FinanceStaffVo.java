package trapx00.lightx00.client.vo.financestaff;

import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;

public class FinanceStaffVo extends EmployeeVo {
    private boolean root;

    public FinanceStaffVo(String id, String name, Date workSince, String password, EmployeeState state, boolean root) {
        super(id, name, workSince, EmployeePosition.FinanceStaff, password, state);
        this.root = root;
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
