package trapx00.lightx00.client.bl.loginbl;

import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.EmployeeInfoFactory;
import trapx00.lightx00.client.blservice.loginblservice.LoginBlService;
import trapx00.lightx00.client.datafactory.logindataservicefactory.LoginDataServiceFactory;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.dataservice.logindataservice.LoginDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;

import java.rmi.RemoteException;

public class LoginBlController implements LoginBlService, CurrentUserService {
    private EmployeeVo currentUser;
    private LoginDataService dataService = LoginDataServiceFactory.getService();
    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
    /**
     * Login with username and password provided.
     *
     * @param username username
     * @param password password
     * @return EmployeeVo is login is successful
     */
    @Override
    public EmployeeVo login(String username, String password) {
        try {
            String employeeId = dataService.login(username,password);
            if (employeeId == null) {
                return null;
            }
            return currentUser = employeeInfo.queryById(employeeId);
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    void setCurrentUser(EmployeeVo employeeVo) {
        this.currentUser = employeeVo;
    }

    /**
     * Gets current logged-in user.
     *
     * @return current user
     */
    @Override
    public EmployeeVo getCurrentUser() {
        return currentUser;
    }
}
