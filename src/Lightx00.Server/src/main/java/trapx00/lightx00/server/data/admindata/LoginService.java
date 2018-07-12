package trapx00.lightx00.server.data.admindata;

public interface LoginService {
    /**
     * Login.
     * @param username username
     * @param password password
     * @return EmployeeId if login is successful. Null otherwise
     */
    String login(String username, String password);
}
