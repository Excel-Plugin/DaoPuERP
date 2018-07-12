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
import trapx00.lightx00.client.blservice.financeblservice.ReceivalBillBlService;
import trapx00.lightx00.client.datafactory.financedataservicefactory.ReceivalBillDataServiceFactory;
import trapx00.lightx00.client.vo.financestaff.ReceivalBillVo;
import trapx00.lightx00.shared.dataservice.financedataservice.ReceivalBillDataService;
import trapx00.lightx00.shared.po.ClientModificationFlag;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.ReceivalBillPo;
import trapx00.lightx00.shared.po.financestaff.Transcation;
import trapx00.lightx00.shared.queryvo.ReceivalBillQueryVo;

import java.util.List;

public class ReceivalBillBlController
    implements ReceivalBillBlService, NotificationActivateService, NotificationAbandonService,
    DraftDeleteService, ReceivalBillInfo, BillApprovalCompleteService, BillPoVoConverter<ReceivalBillPo, ReceivalBillVo> {

    private ReceivalBillDataService dataService = ReceivalBillDataServiceFactory.getService();
    private ClientModificationService modificationService = ClientModificationServiceFactory.getInstance();
    private BankAccountModificationService bankAccountModificationService = BankAccountFactory.getModificationService();

    private CommonBillBlController<ReceivalBillVo, ReceivalBillPo, ReceivalBillQueryVo> commonBillBlController
        = new CommonBillBlController<>(dataService, "付款单", this);

    public ReceivalBillVo fromPoToVo(ReceivalBillPo po) {
        return new ReceivalBillVo(po.getId(), po.getDate(), po.getState(), po.getClientId(), po.getOperatorId(), po.getTranscations(), po.getTotal());
    }

    public ReceivalBillPo fromVoToPo(ReceivalBillVo vo) {
        return new ReceivalBillPo(vo.getId(), vo.getDate(), vo.getState(), vo.getClientId(), vo.getOperatorId(), vo.getTranscations(), vo.getTotal());
    }
    /**
     * Submits a ReceivalBill.
     *
     * @param bill ReceivalBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(ReceivalBillVo bill) {
        return commonBillBlController.submit(bill);
    }

    /**
     * Saves a half-completed ReceivalBill as a draft.
     *
     * @param bill ReceivalBill to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(ReceivalBillVo bill) {
        return commonBillBlController.saveAsDraft(bill);
    }

    /**
     * Gets the id for the next bill
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
        ReceivalBillVo receivalBillVo = query(new ReceivalBillQueryVo().idEq(id))[0];
        modificationService.modifyClient(receivalBillVo.getClientId(), ClientModificationFlag.PAYABLE, -receivalBillVo.getTotal());
        for (Transcation transcation : receivalBillVo.getTranscations()) {
            bankAccountModificationService.modifyBankAccount(transcation.getAccountId(), transcation.getTotal());
        }
        return commonBillBlController.activate(id);
    }

    /**
     * Queries ReceivalBillVo.
     *
     * @param query query
     * @return ReceivalBills that match the condition
     */
    @Override
    public ReceivalBillVo[] query(ReceivalBillQueryVo query) {
        List<ReceivalBillVo> receivalBillVos = commonBillBlController.query(query);
        return receivalBillVos.toArray(new ReceivalBillVo[receivalBillVos.size()]);
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
