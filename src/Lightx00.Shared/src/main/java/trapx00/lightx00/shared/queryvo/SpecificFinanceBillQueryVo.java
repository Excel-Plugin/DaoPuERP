package trapx00.lightx00.shared.queryvo;

import trapx00.lightx00.shared.po.financestaff.FinanceBillPo;

public class SpecificFinanceBillQueryVo <T extends FinanceBillPo, Q extends SpecificFinanceBillQueryVo<T, Q>> extends BaseQueryVo<T, String, Q> {
    public SpecificFinanceBillQueryVo() {
    }

    public SpecificFinanceBillQueryVo(BaseQueryVo another) {
        super(another);
    }
}
