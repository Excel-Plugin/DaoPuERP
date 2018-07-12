package trapx00.lightx00.shared.po.financestaff;

import com.j256.ormlite.table.DatabaseTable;
import trapx00.lightx00.shared.po.bill.BillState;

import java.util.Arrays;
import java.util.Date;

@DatabaseTable(tableName = "ReceivalBill")
public class  ReceivalBillPo extends ReceivalPaymentBillPoBase {
    public ReceivalBillPo(String id, Date date, BillState state,
                          String clientId, String operatorId, Transcation[] transcations, double total) {
        super(id, date, state, FinanceBillType.ReceivalBill, clientId, operatorId, transcations, total);
    }

    public ReceivalBillPo() {
    }

    @Override
    public String toString() {
        return "ReceivalBillPo{" +
            "clientId='" + clientId + '\'' +
            ", transcations=" + Arrays.toString(transcations) +
            ", total=" + total +
            "} " + super.toString();
    }
}
