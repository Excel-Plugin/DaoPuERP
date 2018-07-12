package trapx00.lightx00.client.bl.adminbl.mock;

import trapx00.lightx00.client.bl.adminbl.UserManagementBlController;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.financestaff.FinanceStaffVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.util.Date;

public class UserManagementBlControllerMock extends UserManagementBlController {
    /**
     * Create a user account for a new employee.
     * @param account a user account to be created
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(EmployeeVo account) {
        return ResultMessage.Success;
    }

    /**
     * Modify some information of a user account.
     * @param account a user account to be modified
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(EmployeeVo account) {
        return ResultMessage.Success;
    }

    /**
     * Filter a user account.
     * @param query the filter conditions
     * @return array of EmployeeVo which match the conditions
     */
    @Override
    public EmployeeVo[] query(UserAccountQueryVo query) {
        return new EmployeeVo[] { new FinanceStaffVo("10001","财务经理",new Date(),"123456", EmployeeState.Active,true)
        };
    }

    /**
     * Delete an needless user account.
     * @param account the user account to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(EmployeeVo account) {
        return ResultMessage.Success;
    }
}
