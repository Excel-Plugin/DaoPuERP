package trapx00.lightx00.shared.dataservice.admindataservice;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserManagementDataService extends Remote {
    /**
     * Filter some user accounts.
     * @param query the filter conditions
     * @return array of user accounts which match the conditions
     */
    EmployeePo[] query(UserAccountQueryVo query) throws RemoteException;

    /**
     * Create a user account for a new employee.
     * @param account the account to be created
     * @return whether the operation is done successfully
     */
    ResultMessage add(EmployeePo account) throws RemoteException;

    /**
     * Modify a user account.
     * @param account the account to be modified
     * @return whether the operation is done successfully
     */
    ResultMessage modify(EmployeePo account) throws RemoteException;

    /**
     * Delete a user account.
     * @param account the account to be deleted
     * @return whether the operation is done successfully
     */
    ResultMessage delete(EmployeePo account) throws RemoteException;

    /**
     * New id for a new employee.
     * @return new id
     */
    String getId()throws RemoteException;
}
