package trapx00.lightx00.shared.queryvo;

import trapx00.lightx00.shared.po.financestaff.BankAccountPo;

public class  BankAccountQueryVo extends BaseQueryVo<BankAccountPo, Integer, BankAccountQueryVo>{
    public BankAccountQueryVo() {
    }

    public BankAccountQueryVo(BaseQueryVo another) {
        super(another);
    }
}
