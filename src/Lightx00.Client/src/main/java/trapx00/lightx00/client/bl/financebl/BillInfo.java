package trapx00.lightx00.client.bl.financebl;

import trapx00.lightx00.client.vo.BillVo;

public interface BillInfo {
    /**
     * Queries Bill with id.
     * @param id bill id
     * @return BillVos that has the
     */
    BillVo queryBill(String id);
}
