package trapx00.lightx00.client.bl.adminbl.factory;

import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.UserManagementBlController;

public class EmployeeInfoFactory {
    private static EmployeeInfo employeeInfo = new UserManagementBlController();

    public static EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }
}
