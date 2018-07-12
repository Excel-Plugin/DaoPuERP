package trapx00.lightx00.client.presentation.adminui.factory;

import trapx00.lightx00.client.presentation.adminui.EmployeeSelection;
import trapx00.lightx00.client.presentation.adminui.EmployeeSelectionUiController;
import trapx00.lightx00.client.presentation.adminui.UserManagementUiController;

public class UserManagementUiFactory {
    private static UserManagementUiController controller = new UserManagementUiController();

    public static EmployeeSelection getEmployeeSelectionUi() {
        return new EmployeeSelectionUiController();
    }

}
