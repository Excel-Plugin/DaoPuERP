package trapx00.lightx00.shared.queryvo;

import trapx00.lightx00.shared.po.financestaff.CashBillPo;

public class CashBillQueryVo extends SpecificFinanceBillQueryVo<CashBillPo, CashBillQueryVo> {
    public CashBillQueryVo() {
    }

    public CashBillQueryVo(BaseQueryVo another) {
        super(another);
    }
}
