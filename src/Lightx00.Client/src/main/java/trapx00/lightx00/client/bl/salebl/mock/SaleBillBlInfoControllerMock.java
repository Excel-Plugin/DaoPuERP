package trapx00.lightx00.client.bl.salebl.mock;

import trapx00.lightx00.client.bl.salebl.SaleBillBlInfoController;
import trapx00.lightx00.client.bl.salebl.factory.SaleBillBlFactory;
import trapx00.lightx00.client.bl.salebl.factory.SaleRefundBillBlFactory;
import trapx00.lightx00.client.blservice.saleblservice.SaleBillBlService;
import trapx00.lightx00.client.blservice.saleblservice.SaleRefundBillBlService;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.client.vo.salestaff.SaleRefundBillVo;
import trapx00.lightx00.shared.queryvo.SaleBillQueryVo;
import trapx00.lightx00.shared.queryvo.SaleRefundBillQueryVo;

public class SaleBillBlInfoControllerMock extends SaleBillBlInfoController {
    SaleBillBlService saleBillBlService = SaleBillBlFactory.getSaleBillBlController();
    SaleRefundBillBlService saleRefundBillBlService = SaleRefundBillBlFactory.getSaleRefundBillBlController();

    /**
     * query for SaleBills
     *
     * @param query function
     * @return array of SaleBillVo
     */
    public SaleBillVo[] querySaleBill(SaleBillQueryVo query) {
        return saleBillBlService.querySaleBill(query);
    }

    /**
     * query for SaleRefundBills
     *
     * @param query function
     * @return array of SaleRefundBillVo
     */
    public SaleRefundBillVo[] querySaleRefundBill(SaleRefundBillQueryVo query) {
        return saleRefundBillBlService.querySaleRefundBill(query);
    }
}
