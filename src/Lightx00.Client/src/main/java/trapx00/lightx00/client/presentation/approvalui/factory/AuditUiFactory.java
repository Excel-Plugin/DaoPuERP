package trapx00.lightx00.client.presentation.approvalui.factory;

import trapx00.lightx00.client.bl.approvalbl.AuditBlController;

public class AuditUiFactory {
    private static AuditBlController controller = new AuditBlController();

    public static AuditBlController getController() {
        return controller;
    }
}
