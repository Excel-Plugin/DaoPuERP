package trapx00.lightx00.client.vo.admin;

import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;

public class AdminVo extends EmployeeVo {

    public AdminVo(String id, String name, Date workSince, String password, EmployeeState state) {
        super(id, name, workSince, EmployeePosition.Admin, password, state);
    }
}
