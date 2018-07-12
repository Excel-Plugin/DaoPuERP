package trapx00.lightx00.shared.po.salestaff;

import com.j256.ormlite.field.DatabaseField;
import trapx00.lightx00.shared.po.bill.BillPo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;

import java.util.Date;

public class SaleBillBasePo extends BillPo {
    @DatabaseField
    private SaleBillType saleBillType;

    public SaleBillBasePo(String id, Date date, BillState state, SaleBillType saleBillType, String operatorId) {
        super(BillType.SaleBill, id, date, state, operatorId);
        this.saleBillType = saleBillType;
    }

    public SaleBillBasePo() {
    }

    public SaleBillType getSaleBillType() {
        return saleBillType;
    }
}
