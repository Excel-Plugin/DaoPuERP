package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.vo.financestaff.CashBillVo;
import trapx00.lightx00.shared.po.ResultMessage;

public interface CashBillBlService {

    /**
     * Submits a CashBill.
     * @param bill CashBillVo to be submitted
     * @return whether the operation is done successfully
     */

    ResultMessage submit(CashBillVo bill);

    /**
     * Saves a half-completed CashBill as draft.
     * @param bill CashBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */

    ResultMessage saveAsDraft(CashBillVo bill);

    /**
     * Gets the id for the next bill.
     * @return id for the next bill
     */
    String getId();

}
