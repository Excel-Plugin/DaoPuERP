package trapx00.lightx00.client.bl.financebl;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.bankaccountbl.BankAccountModificationService;
import trapx00.lightx00.client.bl.bankaccountbl.factory.BankAccountFactory;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.bl.util.BillPoVoConverter;
import trapx00.lightx00.client.bl.util.CommonBillBlController;
import trapx00.lightx00.client.blservice.financeblservice.CashBillBlService;
import trapx00.lightx00.client.datafactory.financedataservicefactory.CashBillDataServiceFactory;
import trapx00.lightx00.client.vo.financestaff.CashBillVo;
import trapx00.lightx00.shared.dataservice.financedataservice.CashBillDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.CashBillPo;
import trapx00.lightx00.shared.queryvo.CashBillQueryVo;

import java.util.List;

public class CashBillBlController implements CashBillBlService, NotificationActivateService, NotificationAbandonService, DraftDeleteService, CashBillInfo, BillApprovalCompleteService, BillPoVoConverter<CashBillPo, CashBillVo> {


    private CashBillDataService dataService = CashBillDataServiceFactory.getService();
    private BankAccountModificationService modificationService = BankAccountFactory.getModificationService();

    private CommonBillBlController<CashBillVo, CashBillPo, CashBillQueryVo> commonBillBlController
        = new CommonBillBlController<>(dataService, "现金费用单", this);

    @Override
    public CashBillVo fromPoToVo(CashBillPo po) {
        return new CashBillVo(po.getId(), po.getDate(), po.getState(), po.getOperatorId(), po.getAccountId(), po.getItems());

    }

    @Override
    public CashBillPo fromVoToPo(CashBillVo vo) {
        return new CashBillPo(vo.getId(), vo.getDate(), vo.getState(), vo.getOperatorId(), vo.getAccountId(), vo.getItems());
    }

    /**
     *
     * Submits a CashBill.
     *
     * @param bill CashBillVo to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(CashBillVo bill) {
        return commonBillBlController.submit(bill);
    }

    /**
     * Saves a half-completed CashBill as draft.
     *
     * @param bill CashBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(CashBillVo bill) {
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
        CashBillVo cashBillVo = query(new CashBillQueryVo().idEq(id))[0];
        modificationService.modifyBankAccount(cashBillVo.getAccountId(), -cashBillVo.getTotal());
        return commonBillBlController.activate(id);
    }

    /**
     * Queries CashBill.
     *
     * @param query query
     * @return CashBillVos that match the condition
     */
    @Override
    public CashBillVo[] query(CashBillQueryVo query) {
        List<CashBillVo> result = commonBillBlController.query(query);
        return result.toArray(new CashBillVo[result.size()]);
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
