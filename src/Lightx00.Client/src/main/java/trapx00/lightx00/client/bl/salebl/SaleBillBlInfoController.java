package trapx00.lightx00.client.bl.salebl;

import trapx00.lightx00.client.blservice.saleblservice.SaleBillBlService;
import trapx00.lightx00.client.blservice.saleblservice.SaleRefundBillBlService;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.client.vo.salestaff.SaleRefundBillVo;
import trapx00.lightx00.shared.queryvo.SaleBillQueryVo;
import trapx00.lightx00.shared.queryvo.SaleRefundBillQueryVo;

public class SaleBillBlInfoController implements SaleBillBlInfo {

    SaleBillBlService saleBillBlService = new SaleBillBlController();
    SaleRefundBillBlService saleRefundBillBlService = new SaleRefundBillBlController();

    /**
     * query for SaleBills
     *
     * @param query function
     * @return array of SaleBillVo
     */
    @Override
    public SaleBillVo[] querySaleBill(SaleBillQueryVo query) {
        return saleBillBlService.querySaleBill(query);
    }

    /**
     * query for SaleRefundBills
     *
     * @param query function
     * @return array of SaleRefundBillVo
     */
    @Override
    public SaleRefundBillVo[] querySaleRefundBill(SaleRefundBillQueryVo query) {
        return saleRefundBillBlService.querySaleRefundBill(query);
    }
}
