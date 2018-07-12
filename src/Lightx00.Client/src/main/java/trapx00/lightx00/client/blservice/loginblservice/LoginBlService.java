package trapx00.lightx00.client.blservice.loginblservice;

import trapx00.lightx00.client.vo.EmployeeVo;

public interface LoginBlService {
    /**
     * Login with username and password provided.
     * @param username username
     * @param password password
     * @return EmployeeVo is login is successful
     */
    EmployeeVo login(String username, String password);
}
