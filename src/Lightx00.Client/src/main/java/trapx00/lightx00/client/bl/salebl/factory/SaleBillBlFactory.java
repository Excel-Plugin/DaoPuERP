package trapx00.lightx00.client.bl.salebl.factory;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.bl.salebl.SaleBillBlController;

public class SaleBillBlFactory {
    private static SaleBillBlController saleBillBlController = new SaleBillBlController();
    private static NotificationActivateService notificationActivateService = saleBillBlController;
    private static NotificationAbandonService notificationAbandonService = saleBillBlController;
    private static BillApprovalCompleteService billApprovalCompleteService = saleBillBlController;
    private static DraftDeleteService draftDeleteService = saleBillBlController;

    public static SaleBillBlController getSaleBillBlController() {
        return saleBillBlController;
    }

    public static NotificationActivateService getNotificationActivateService() {
        return notificationActivateService;
    }

    public static NotificationAbandonService getNotificationAbandonService() {
        return notificationAbandonService;
    }

    public static BillApprovalCompleteService getBillApprovalCompleteService() {
        return billApprovalCompleteService;
    }

    public static DraftDeleteService getDraftDeleteService() {
        return draftDeleteService;
    }
}
