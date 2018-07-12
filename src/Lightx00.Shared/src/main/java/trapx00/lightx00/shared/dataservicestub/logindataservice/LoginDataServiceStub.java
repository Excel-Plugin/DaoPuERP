package trapx00.lightx00.shared.dataservicestub.logindataservice;

import trapx00.lightx00.shared.dataservice.logindataservice.LoginDataService;


public class LoginDataServiceStub implements LoginDataService {
    /**
     * Login.
     *
     * @param username username
     * @param password password
     * @return EmployeePo if login is successful
     */
    @Override
    public String login(String username, String password) {
//        return new EmployeePo(username, "123", new Date(),
//                username,password, EmployeePosition.Admin);
        return "1";
    }
}
