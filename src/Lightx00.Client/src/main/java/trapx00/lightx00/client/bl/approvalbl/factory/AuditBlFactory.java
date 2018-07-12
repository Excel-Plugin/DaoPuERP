package trapx00.lightx00.client.bl.approvalbl.factory;

import trapx00.lightx00.client.bl.approvalbl.AuditBlController;

public class AuditBlFactory {
    private static AuditBlController controller = new AuditBlController();

    public static AuditBlController getController() {
        return controller;
    }

}
