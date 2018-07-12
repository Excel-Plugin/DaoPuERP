package trapx00.lightx00.shared.dataservicestub.admindataservice;

import trapx00.lightx00.shared.dataservice.admindataservice.UserManagementDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

public class UserManagementDataServiceStub implements UserManagementDataService {
    /**
     * Filter some user accounts.
     * @param query the filter conditions
     * @return array of user accounts which match the conditions
     */
    @Override
    public EmployeePo[] query(UserAccountQueryVo query) {
        return new EmployeePo[0];
    }

    /**
     * Create a user account for a new employee.
     * @param account the account to be created
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(EmployeePo account) {
        return ResultMessage.Success;
    }

    /**
     * Modify a user account.
     * @param account the account to be modified
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(EmployeePo account) {
        return ResultMessage.Success;
    }

    /**
     * Delete a user account.
     * @param account the account to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(EmployeePo account) {
        return ResultMessage.Success;
    }

    /**
     * New id for a new employee.
     * @return new id
     */
    @Override
    public String getId(){return "";}
}
