package trapx00.lightx00.client.bl.financebl.mock;

import trapx00.lightx00.client.bl.financebl.CashBillBlController;
import trapx00.lightx00.client.vo.financestaff.CashBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.CashBillItem;
import trapx00.lightx00.shared.queryvo.CashBillQueryVo;

import java.util.Date;

public class CashBillBlControllerMock extends CashBillBlController {
    /**
     * Submits a CashBill.
     *
     * @param bill CashBillVo to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(CashBillVo bill) {
        return ResultMessage.Success;
    }

    /**
     * Saves a half-completed CashBill as draft.
     *
     * @param bill CashBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(CashBillVo bill) {
        return ResultMessage.Success;
    }

    /**
     * Gets the id for the next bill
     *
     * @return id for the next bill
     */
    @Override
    public String getId() {
        return "XJFYD-20171107-00001";
    }

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
     * Queries CashBill.
     *
     * @param query query
     * @return CashBillVos that match the condition
     */
    @Override
    public CashBillVo[] query(CashBillQueryVo query) {
        return new CashBillVo[] {
            new CashBillVo("XJFYD-20171107-00001",new Date(), BillState.Rejected, "",1,new CashBillItem[0])
        };
    }

    /**
     * When bill is approved, this method is called to modify the state of the bill.
     *
     * @param billId id for the bill
     * @param state  newState. Only BillState.Approved and BillState.Rejected are allowed.
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage approvalComplete(String billId, BillState state) {
        return ResultMessage.Success;
    }
}
