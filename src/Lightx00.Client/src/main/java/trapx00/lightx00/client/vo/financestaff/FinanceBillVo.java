package trapx00.lightx00.client.vo.financestaff;

import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;
import trapx00.lightx00.shared.po.financestaff.FinanceBillType;

import java.util.Date;


public abstract class FinanceBillVo extends BillVo {
    protected FinanceBillType financeBillType;

    public FinanceBillVo(FinanceBillType financeBillType, String id, Date date,
                         BillState state, String operatorId) {
        super(BillType.FinanceBill, id, date, state, operatorId);
        this.financeBillType = financeBillType;
    }

    public FinanceBillType getFinanceBillType() {
        return financeBillType;
    }

    public void setFinanceBillType(FinanceBillType financeBillType) {
        this.financeBillType = financeBillType;
    }
}
