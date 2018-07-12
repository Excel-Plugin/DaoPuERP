package trapx00.lightx00.shared.po.financestaff;

import com.j256.ormlite.table.DatabaseTable;
import trapx00.lightx00.shared.po.bill.BillState;

import java.util.Arrays;
import java.util.Date;

@DatabaseTable(tableName = "PaymentBill")
public class  PaymentBillPo extends ReceivalPaymentBillPoBase {

    public PaymentBillPo(String id, Date date, BillState state, String clientId, String operatorId,
                         Transcation[] transcations, double total) {
        super(id, date, state, FinanceBillType.PaymentBill, clientId, operatorId, transcations, total);
    }

    public PaymentBillPo() {
    }

    @Override
    public String toString() {
        return "PaymentBillPo{" +
            "clientId='" + clientId + '\'' +
            ", transcations=" + Arrays.toString(transcations) +
            ", total=" + total +
            "} " + super.toString();
    }
}
