package trapx00.lightx00.client.bl.inventorybl.factory;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.inventorybl.PurchaseRefundBillBlController;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;

public class PurchaseRefundBillBlFactory {

    private static PurchaseRefundBillBlController purchaseRefundBillBlController = new PurchaseRefundBillBlController();
    private static NotificationActivateService notificationActivateService = purchaseRefundBillBlController;
    private static NotificationAbandonService notificationAbandonService = purchaseRefundBillBlController;
    private static BillApprovalCompleteService billApprovalCompleteService = purchaseRefundBillBlController;
    private static DraftDeleteService draftDeleteService = purchaseRefundBillBlController;

    public static PurchaseRefundBillBlController getPurchaseRefundBillBlController() {
        return purchaseRefundBillBlController;
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
