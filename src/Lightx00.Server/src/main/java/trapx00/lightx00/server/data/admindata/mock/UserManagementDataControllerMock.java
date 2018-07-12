package trapx00.lightx00.server.data.admindata.mock;

import trapx00.lightx00.server.data.admindata.UserManagementDataController;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.po.financestaff.FinanceStaffPo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.util.Date;

public class UserManagementDataControllerMock extends UserManagementDataController {

    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     *
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public UserManagementDataControllerMock() throws RemoteException {
    }

    /**
     * Filter some user accounts.
     * @param query the filter conditions
     * @return array of user accounts which match the conditions
     */
    @Override
    public EmployeePo[] query(UserAccountQueryVo query) {
        return new EmployeePo[]{new FinanceStaffPo("0001","张三",new Date(),"123456", EmployeeState.Practice,false)};
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
}
