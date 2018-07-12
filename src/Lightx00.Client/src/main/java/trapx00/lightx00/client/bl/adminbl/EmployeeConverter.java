package trapx00.lightx00.client.bl.adminbl;

import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.po.employee.EmployeePo;

public interface EmployeeConverter<Vo extends EmployeeVo> {
    /**
     * Convert EmployeePo to EmployeeVo.
     * @param po EmployeePo
     * @return specific employeeVo
     */
    Vo fromPoToVo (EmployeePo po);

    /**
     * Convert EmployeeVo to EmployeePo.
     * @param vo specific EmployeeVo
     * @return EmployeePo
     */
    EmployeePo fromVoToPo(Vo vo);
}
