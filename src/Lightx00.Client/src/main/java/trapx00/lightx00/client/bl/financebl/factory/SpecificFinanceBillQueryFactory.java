package trapx00.lightx00.client.bl.financebl.factory;


import trapx00.lightx00.client.bl.financebl.CashBillInfo;
import trapx00.lightx00.client.bl.financebl.PaymentBillInfo;
import trapx00.lightx00.client.bl.financebl.ReceivalBillInfo;
import trapx00.lightx00.client.bl.financebl.SystemSnapshotInfo;

public class SpecificFinanceBillQueryFactory {
    private static CashBillInfo cashbillInfo = CashBillBlFactory.getCashBillInfo();
    private static PaymentBillInfo paymentBillInfo = PaymentBillBlFactory.getPaymentBillInfo();
    private static ReceivalBillInfo receivalBillInfo = ReceivalBillBlFactory.getReceivalBillInfo();
    private static SystemSnapshotInfo systemSnapshotInfo = InitialEstablishmentBlFactory.getSystemSnapshotInfo();


    public static CashBillInfo getCashbillInfo() {
        return cashbillInfo;
    }

    public static PaymentBillInfo getPaymentBillInfo() {
        return paymentBillInfo;
    }

    public static ReceivalBillInfo getReceivalBillInfo() {
        return receivalBillInfo;
    }

    public static SystemSnapshotInfo getSystemSnapshotInfo() {
        return systemSnapshotInfo;
    }
}
