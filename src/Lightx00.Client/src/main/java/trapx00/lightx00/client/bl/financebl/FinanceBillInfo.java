package trapx00.lightx00.client.bl.financebl;

import trapx00.lightx00.client.vo.financestaff.FinanceBillVo;
import trapx00.lightx00.shared.queryvo.FinanceBillQueryVo;

public interface FinanceBillInfo {
    /**
     * Queries FinanceBill.
     * @param query FinanceBill query condition
     * @return Finance bills that match query condition
     */
    FinanceBillVo[] query(FinanceBillQueryVo query);
}
