package trapx00.lightx00.client.bl.financebl.factory;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.financebl.CashBillBlController;
import trapx00.lightx00.client.bl.financebl.CashBillInfo;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;

public class CashBillBlFactory {
    private static CashBillBlController controller = new CashBillBlController();
    private static NotificationActivateService notificationActivateService = controller;
    private static NotificationAbandonService notificationAbandonService = controller;
    private static DraftDeleteService draftDeleteService = controller;
    private static CashBillInfo cashBillInfo = controller;
    private static BillApprovalCompleteService billApprovalCompleteService = controller;

    public static BillApprovalCompleteService getBillApprovalCompleteService() {
        return billApprovalCompleteService;
    }

    public static NotificationActivateService getNotificationActivateService() {
        return notificationActivateService;
    }

    public static NotificationAbandonService getNotificationAbandonService() {
        return notificationAbandonService;
    }

    public static CashBillBlController getController() {
        return controller;
    }

    public static DraftDeleteService getDraftDeleteService() {
        return draftDeleteService;
    }

    public static CashBillInfo getCashBillInfo() {
        return cashBillInfo;
    }
}
