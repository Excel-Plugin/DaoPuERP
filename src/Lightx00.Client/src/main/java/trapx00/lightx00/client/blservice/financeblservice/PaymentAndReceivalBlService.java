package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.vo.financestaff.ReceivalPaymentBillVoBase;
import trapx00.lightx00.shared.po.ResultMessage;

public interface PaymentAndReceivalBlService<T extends ReceivalPaymentBillVoBase> {
    /**
     * Submits a bill.
     * @param bill bill to be submitted
     * @return whether the operation is done successfully
     */
    ResultMessage submit(T bill);

    /**
     * Saves a half-completed PaymentBill as a draft.
     * @param bill PaymentBill to be saved as a draft
     * @return whether the operation is done successfully
     */

    ResultMessage saveAsDraft(T bill);

    /**
     * Gets the id for the next bill.
     * @return id for the next bill
     */
    String getId();
}
