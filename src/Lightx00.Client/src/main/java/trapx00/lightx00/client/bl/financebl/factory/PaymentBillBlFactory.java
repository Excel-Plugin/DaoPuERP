package trapx00.lightx00.client.bl.financebl.factory;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.financebl.PaymentBillBlController;
import trapx00.lightx00.client.bl.financebl.PaymentBillInfo;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;

public class PaymentBillBlFactory {
    private static PaymentBillBlController controller = new PaymentBillBlController();
    private static NotificationActivateService notificationActivateService = controller;
    private static NotificationAbandonService notificationAbandonService = controller;
    private static DraftDeleteService draftDeleteService = controller;
    private static PaymentBillInfo paymentBillInfo = controller;
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

    public static DraftDeleteService getDraftDeleteService() {
        return draftDeleteService;
    }

    public static PaymentBillInfo getPaymentBillInfo() {
        return paymentBillInfo;
    }

    public static PaymentBillBlController getController() {
        return controller;
    }
}
