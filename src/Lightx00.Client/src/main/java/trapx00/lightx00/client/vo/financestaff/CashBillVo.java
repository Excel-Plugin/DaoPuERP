package trapx00.lightx00.client.vo.financestaff;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.financebl.factory.CashBillBlFactory;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.presentation.financeui.cashbill.CashBillDetailUi;
import trapx00.lightx00.client.presentation.financeui.cashbill.CashBillUiController;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;
import trapx00.lightx00.client.presentation.helpui.ReversibleUi;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.CashBillItem;
import trapx00.lightx00.shared.po.financestaff.FinanceBillType;

import java.util.Arrays;
import java.util.Date;

public class CashBillVo extends FinanceBillVo implements Reversible {
    private int accountId;
    private CashBillItem[] items;

    public CashBillVo(String id, Date date, BillState state, String operatorId, int accountId, CashBillItem[] items) {
        super(FinanceBillType.CashBill, id, date, state, operatorId);
        this.accountId = accountId;
        this.items = items;
    }

    public double getTotal() {
        return Arrays.stream(items).mapToDouble(CashBillItem::getAmount).sum();
    }



    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public CashBillItem[] getItems() {
        return items;
    }

    public void setItems(CashBillItem[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "CashBillVo{" +
            "operatorId='" + operatorId + '\'' +
            ", accountId='" + accountId + '\'' +
            ", items=" + Arrays.toString(items) +
            ", financeBillType=" + financeBillType +
            ", billType=" + billType +
            ", id='" + id + '\'' +
            ", date=" + date +
            ", state=" + state +
            "} " + super.toString();
    }

    /**
     * Gets the NotificationActivateService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return NotificationActivateService
     */
    @Override
    public NotificationActivateService notificationActivateService() {
        return CashBillBlFactory.getNotificationActivateService();
    }

    /**
     * Gets the NotificationAbandonService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return NotificationAbandonService
     */
    @Override
    public NotificationAbandonService notificationAbandonService() {
        return CashBillBlFactory.getNotificationAbandonService();
    }

    /**
     * Gets the BillApprovalCompleteService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return BillApprovalCompleteService
     */
    @Override
    public BillApprovalCompleteService billApprovalCompleteService() {
        return CashBillBlFactory.getBillApprovalCompleteService();
    }


    /**
     * Gets DeleteService corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DeleteService
     */
    @Override
    public DraftDeleteService deleteService() {
        return CashBillBlFactory.getDraftDeleteService();
    }

    /**
     * Gets the DraftContinueWritableUiController service corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DraftContinueWritableUiController
     */
    @Override
    public DraftContinueWritableUiController continueWritableUi() {
        return new CashBillUiController();
    }

    /**
     * When it is called, it returns a ReversibleUi which can be used to acquire the ui component and controller.
     *
     * @return reversible ui service.
     */
    @Override
    public ReversibleUi reversibleUi() {
        return new CashBillUiController(); //对应的单据填写UiController实现红冲接口后，在Vo里new一个新的controller返回。
    }

    @Override
    public BillDetailUi billDetailUi() {
        return new CashBillDetailUi(); // 这个是对应的单据详情显示界面，和上面的不一样。
    }
}
