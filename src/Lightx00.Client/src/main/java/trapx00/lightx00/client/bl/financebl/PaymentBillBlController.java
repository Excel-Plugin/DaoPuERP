package trapx00.lightx00.client.bl.financebl;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.bankaccountbl.BankAccountModificationService;
import trapx00.lightx00.client.bl.bankaccountbl.factory.BankAccountFactory;
import trapx00.lightx00.client.bl.clientbl.ClientModificationService;
import trapx00.lightx00.client.bl.clientbl.factory.ClientModificationServiceFactory;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.bl.util.BillPoVoConverter;
import trapx00.lightx00.client.bl.util.CommonBillBlController;
import trapx00.lightx00.client.blservice.financeblservice.PaymentBillBlService;
import trapx00.lightx00.client.datafactory.financedataservicefactory.PaymentBillDataServiceFactory;
import trapx00.lightx00.client.vo.financestaff.PaymentBillVo;
import trapx00.lightx00.shared.dataservice.financedataservice.PaymentBillDataService;
import trapx00.lightx00.shared.po.ClientModificationFlag;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.PaymentBillPo;
import trapx00.lightx00.shared.po.financestaff.Transcation;
import trapx00.lightx00.shared.queryvo.PaymentBillQueryVo;

import java.util.List;

public class PaymentBillBlController
    implements PaymentBillBlService, NotificationActivateService, NotificationAbandonService,
    DraftDeleteService, PaymentBillInfo, BillApprovalCompleteService, BillPoVoConverter<PaymentBillPo, PaymentBillVo> {

    private PaymentBillDataService dataService = PaymentBillDataServiceFactory.getService();
    private ClientModificationService clientModificationService = ClientModificationServiceFactory.getInstance();
    private BankAccountModificationService bankAccountModificationService = BankAccountFactory.getModificationService();

    private CommonBillBlController<PaymentBillVo, PaymentBillPo, PaymentBillQueryVo> commonBillBlController =
        new CommonBillBlController<>(dataService, "付款单", this);

    public PaymentBillPo fromVoToPo(PaymentBillVo vo) {
        return new PaymentBillPo(vo.getId(),vo.getDate(), vo.getState(), vo.getClientId(), vo.getOperatorId(), vo.getTranscations(), vo.getTotal());
    }

    public PaymentBillVo fromPoToVo(PaymentBillPo po) {
        return new PaymentBillVo(po.getId(), po.getDate(), po.getState(), po.getClientId(), po.getOperatorId(), po.getTranscations(), po.getTotal());
    }

    /**
     * Submits a PaymentBill.
     *
     * @param bill PaymentBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(PaymentBillVo bill) {
        return commonBillBlController.submit(bill);
    }

    /**
     * Saves a half-completed PaymentBill as a draft.
     *
     * @param bill PaymentBill to be saved a a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(PaymentBillVo bill) {
        return commonBillBlController.saveAsDraft(bill);
    }

    /**
     * Gets the id for the next bill.
     *
     * @return id for the next bill
     */
    @Override
    public String getId() {
        return commonBillBlController.getId();
    }

    /**
     * Deletes a draft.
     *
     * @param id id of the draft to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage deleteDraft(String id) {
        return commonBillBlController.deleteDraft(id);
    }

    /**
     * Abandons a bill.
     *
     * @param id id for the bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(String id) {
        return commonBillBlController.abandon(id);
    }

    /**
     * Activates a bill that has been approved of.
     *
     * @param id id for the bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage activate(String id) {
        PaymentBillVo paymentBillVo = query(new PaymentBillQueryVo().idEq(id))[0];
        clientModificationService.modifyClient(paymentBillVo.getClientId(), ClientModificationFlag.RECEIVABLE, -paymentBillVo.getTotal());
        for (Transcation transcation : paymentBillVo.getTranscations()) {
            bankAccountModificationService.modifyBankAccount(transcation.getAccountId(), -transcation.getTotal());
        }
        return commonBillBlController.activate(id);
    }

    /**
     * Queries PaymentBill.
     *
     * @param query query
     * @return PaymentBillVos that match the condition
     */
    @Override
    public PaymentBillVo[] query(PaymentBillQueryVo query) {
        List<PaymentBillVo> queryResult = commonBillBlController.query(query);
        return queryResult.toArray(new PaymentBillVo[queryResult.size()]);
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
        return commonBillBlController.approvalComplete(billId, state);
    }
}
