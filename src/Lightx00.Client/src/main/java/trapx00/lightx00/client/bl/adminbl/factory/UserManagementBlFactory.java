package trapx00.lightx00.client.bl.adminbl.factory;

import trapx00.lightx00.client.bl.adminbl.UserManagementBlController;

import static trapx00.lightx00.client.bl.adminbl.UserManagementBlController.init;

public class UserManagementBlFactory {
    private static UserManagementBlController controller = new UserManagementBlController();

    public static UserManagementBlController getController() {
        init();
        return controller;
    }

}
