package trapx00.lightx00.shared.queryvo;

import trapx00.lightx00.shared.po.financestaff.PaymentBillPo;

public class PaymentBillQueryVo extends SpecificFinanceBillQueryVo<PaymentBillPo, PaymentBillQueryVo> {

    public PaymentBillQueryVo() {
    }

    public PaymentBillQueryVo(BaseQueryVo another) {
        super(another);
    }
}
