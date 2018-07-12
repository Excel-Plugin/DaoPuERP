package trapx00.lightx00.client.bl.financebl;

import trapx00.lightx00.client.vo.financestaff.FinanceBillVo;
import trapx00.lightx00.shared.po.financestaff.FinanceBillPo;
import trapx00.lightx00.shared.queryvo.SpecificFinanceBillQueryVo;

public interface BaseFinanceBillInfo<VT extends FinanceBillVo, PT extends FinanceBillPo, Q extends SpecificFinanceBillQueryVo<PT, Q>> {
    VT[] query(Q query);
}
