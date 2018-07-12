package trapx00.lightx00.client.blservice.adminblservice;

import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

public interface UserManagementBlService {
    /**
     * Create a user account for a new employee.
     * @param account a user account to be created
     * @return whether the operation is done successfully
     */
    ResultMessage add(EmployeeVo account);

    /**
     * Modify some information of a user account.
     * @param account a user account to be modified
     * @return whether the operation is done successfully
     */
    ResultMessage modify(EmployeeVo account);

    /**
     * Filter a user account.
     * @param query the filter conditions
     * @return array of EmployeeVo which match the conditions
     */
    EmployeeVo[] query(UserAccountQueryVo query);

    /**
     * Delete an needless user account.
     * @param account the user account to be deleted
     * @return whether the operation is done successfully
     */
    ResultMessage delete(EmployeeVo account);

    /**
     * New id for a new employee.
     * @return new id
     */
    String getId();
}
