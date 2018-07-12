package trapx00.lightx00.client.bl.adminbl.factory;

import trapx00.lightx00.client.bl.adminbl.AdminEmployeeConverter;

public class AdminEmployeeConverterFactory {
    private static AdminEmployeeConverter adminEmployeeConverter = new AdminEmployeeConverter();

    public static AdminEmployeeConverter getAdminEmployeeConverter() {
        return adminEmployeeConverter;
    }
}
