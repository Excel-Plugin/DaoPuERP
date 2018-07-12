package trapx00.lightx00.client.bl.salebl.factory;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.bl.salebl.SaleRefundBillBlController;

public class SaleRefundBillBlFactory {
    private static SaleRefundBillBlController saleRefundBillBlController = new SaleRefundBillBlController();
    private static NotificationActivateService notificationActivateService = saleRefundBillBlController;
    private static NotificationAbandonService notificationAbandonService = saleRefundBillBlController;
    private static BillApprovalCompleteService billApprovalCompleteService = saleRefundBillBlController;
    private static DraftDeleteService draftDeleteService = saleRefundBillBlController;

    public static SaleRefundBillBlController getSaleRefundBillBlController() {
        return saleRefundBillBlController;
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
