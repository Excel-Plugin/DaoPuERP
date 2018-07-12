package trapx00.lightx00.client.bl.adminbl.mock;

import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.financestaff.FinanceStaffVo;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.util.Date;

public class EmployeeInfoMock implements EmployeeInfo {
    /**
     * Queries EmployeeVo with queryvo.
     *
     * @param queryVo query vo
     * @return all employeevos that match condition. if 0 return new EmployeeVo[]
     */
    @Override
    public EmployeeVo[] queryEmployee(UserAccountQueryVo queryVo) {
        return new EmployeeVo[0];
    }

    /**
     * Queries EmployeeVo by id.
     *
     * @param id id
     * @return EmployeeVo if found. Null otherwise.
     */
    @Override
    public EmployeeVo queryById(String id) {
        return new FinanceStaffVo("10001","财务经理",new Date(),"123456", EmployeeState.Active,true);
    }
}
