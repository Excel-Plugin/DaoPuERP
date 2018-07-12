package trapx00.lightx00.client.vo.salestaff;

import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;
import trapx00.lightx00.shared.po.salestaff.SaleBillType;

import java.util.Date;

public abstract class SaleBillBaseVo extends BillVo implements Reversible {
    private SaleBillType saleBillType;

    public SaleBillBaseVo(String id, Date date, BillState state, SaleBillType saleBillType, String operatorId) {
        super(BillType.SaleBill, id, date, state, operatorId);
        this.saleBillType = saleBillType;
    }

    public SaleBillType getSaleBillType() {
        return saleBillType;
    }
}
