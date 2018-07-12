package trapx00.lightx00.server.data.admindata.mock;

import trapx00.lightx00.server.data.admindata.LoginService;

public class LoginServiceMock implements LoginService {
    /**
     * Login.
     *
     * @param username username
     * @param password password
     * @return EmployeeId if login is successful. Null otherwise
     */
    @Override
    public String login(String username, String password) {
        return "1";
    }
}
