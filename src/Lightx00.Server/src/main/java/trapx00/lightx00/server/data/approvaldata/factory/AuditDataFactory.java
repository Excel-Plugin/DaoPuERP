package trapx00.lightx00.server.data.approvaldata.factory;

import trapx00.lightx00.server.data.approvaldata.AuditDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.approvaldataservice.AuditDataService;

import java.rmi.RemoteException;

@Export
public class AuditDataFactory {
    private static AuditDataService service;

    static {
        try {
            service = new AuditDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static AuditDataService getService() {
        return service;
    }
}
