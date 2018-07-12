package trapx00.lightx00.client.blservicestub.saleblservice;

import trapx00.lightx00.client.blservice.saleblservice.SaleRefundBillBlService;
import trapx00.lightx00.client.vo.salestaff.SaleRefundBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.SaleRefundBillQueryVo;

public class SaleRefundBillBlServiceStub implements SaleRefundBillBlService{

    /**
     * submit a saleRefundBill
     *
     * @param saleRefundBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(SaleRefundBillVo saleRefundBill) {
        return null;
    }

    /**
     * saves a half-completed saleRefundBill as draft.
     *
     * @param saleRefundBill SaleRefundBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(SaleRefundBillVo saleRefundBill) {
        return null;
    }

    /**
     * Gets the id for the next saleRefundBill
     *
     * @return id for the next saleRefundBill
     */
    @Override
    public String getId() {
        return null;
    }

    /**
     * query for SaleRefundBills
     *
     * @param query function
     * @return array of SaleRefundBillVo
     */
    @Override
    public SaleRefundBillVo[] querySaleRefundBill(SaleRefundBillQueryVo query) {
        return new SaleRefundBillVo[0];
    }
}
