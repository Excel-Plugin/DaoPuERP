package trapx00.lightx00.shared.po.financestaff;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import trapx00.lightx00.shared.po.bill.BillState;

import java.util.Arrays;
import java.util.Date;


public class  ReceivalPaymentBillPoBase extends FinanceBillPo {
    //收款单格式是SKD-yyyyMMDD-00001，付款单格式是FKD-yyyyMMDD-00001
    @DatabaseField
    protected String clientId;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    protected Transcation[] transcations;
    @DatabaseField
    protected double total;

    public ReceivalPaymentBillPoBase(String id, Date date, BillState state,
                                     FinanceBillType financeBillType, String clientId,
                                     String operatorId, Transcation[] transcations, double total) {
        super(id, date, state, financeBillType, operatorId);
        this.clientId = clientId;
        this.transcations = transcations;
        this.total = total;
    }

    public ReceivalPaymentBillPoBase() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Transcation[] getTranscations() {
        return transcations;
    }

    public void setTranscations(Transcation[] transcations) {
        this.transcations = transcations;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ReceivalPaymentBillPoBase{" +
            "clientId='" + clientId + '\'' +
            ", transcations=" + Arrays.toString(transcations) +
            ", total=" + total +
            "} " + super.toString();
    }
}
