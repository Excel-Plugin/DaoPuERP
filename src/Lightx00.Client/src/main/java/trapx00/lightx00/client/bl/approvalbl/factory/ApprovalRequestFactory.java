package trapx00.lightx00.client.bl.approvalbl.factory;

import trapx00.lightx00.client.bl.approvalbl.ApprovalRequest;
import trapx00.lightx00.client.bl.approvalbl.ApprovalRequestBlController;

public class ApprovalRequestFactory {
    private static ApprovalRequest service = new ApprovalRequestBlController();

    public static ApprovalRequest getService(){
        return service;
    }
}
