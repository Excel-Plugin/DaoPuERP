package trapx00.lightx00.client.bl.salebl.mock;

import trapx00.lightx00.client.bl.salebl.SaleRefundBillBlController;
import trapx00.lightx00.client.vo.salestaff.SaleRefundBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.SaleRefundBillQueryVo;

public class SaleRefundBillBlControllerMock extends SaleRefundBillBlController {

    /**
     * Deletes a draft.
     *
     * @param id id of the draft to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage deleteDraft(String id) {
        return ResultMessage.Success;
    }

    /**
     * Abandons a bill.
     *
     * @param id id for the bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(String id) {
        return ResultMessage.Success;
    }

    /**
     * Activates a bill that has been approved of.
     *
     * @param id id for the bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage activate(String id) {
        return ResultMessage.Success;
    }

    /**
     * submit a saleRefundBill
     *
     * @param saleRefundBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(SaleRefundBillVo saleRefundBill) {
        return ResultMessage.Success;
    }

    /**
     * saves a half-completed saleRefundBill as draft.
     *
     * @param saleRefundBill SaleRefundBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(SaleRefundBillVo saleRefundBill) {
        return ResultMessage.Success;
    }

    /**
     * Gets the id for the next saleRefundBill
     *
     * @return id for the next saleRefundBill
     */
    @Override
    public String getId() {
        return "0";
    }

    /**
     * query for SaleRefundBills
     *
     * @param query function
     * @return array of SaleRefundBillVo
     */
    @Override
    public SaleRefundBillVo[] querySaleRefundBill(SaleRefundBillQueryVo query) {
        SaleRefundBillVo[] saleRefundBillVos=new SaleRefundBillVo[1];
        saleRefundBillVos[0]=new SaleRefundBillVo("XSD-171203-00000",null,null,null,null,null,1,null,100,100,100,100,null);
        return saleRefundBillVos;
    }
}
