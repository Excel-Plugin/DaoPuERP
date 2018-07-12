package trapx00.lightx00.client.vo.financestaff;

import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.FinanceBillType;
import trapx00.lightx00.shared.po.financestaff.Transcation;

import java.util.Date;

public abstract class ReceivalPaymentBillVoBase extends FinanceBillVo implements Reversible {
    private String clientId;
    private Transcation[] transcations;
    private double total;

    public ReceivalPaymentBillVoBase(FinanceBillType financeBillType,
                                     String id, Date date, BillState state, String clientId, String operatorId, Transcation[] transcations, double total) {
        super(financeBillType, id, date, state, operatorId);
        this.clientId = clientId;
        this.operatorId = operatorId;
        this.transcations = transcations;
        this.total = total;
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
}


