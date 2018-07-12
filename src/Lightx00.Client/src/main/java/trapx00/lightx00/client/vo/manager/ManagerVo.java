package trapx00.lightx00.client.vo.manager;

import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;

public class ManagerVo extends EmployeeVo {

    public ManagerVo(String id, String name, Date workSince, String password, EmployeeState state) {
        super(id, name, workSince, EmployeePosition.Manager, password, state);
    }
}