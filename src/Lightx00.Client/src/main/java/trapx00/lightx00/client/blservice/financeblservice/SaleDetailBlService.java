package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.vo.financestaff.SaleDetailQueryVo;
import trapx00.lightx00.client.vo.financestaff.SaleDetailVo;
import trapx00.lightx00.shared.po.ResultMessage;

public interface SaleDetailBlService {
    /**
     * Queries SaleDetail.
     * @param query SaleDetail query conditions
     * @return SaleDetail that matches query conditions
     */

    SaleDetailVo query(SaleDetailQueryVo query);

    /**
     * Exports a SaleDetail.
     * @param detail SaleDetail to be exported
     * @return whether the operation is done successfully
     */

    ResultMessage export(SaleDetailVo detail);
}
