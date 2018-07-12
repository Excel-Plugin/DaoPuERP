package trapx00.lightx00.client.vo.financestaff;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.financebl.factory.InitialEstablishmentBlFactory;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.presentation.financeui.InitialEstablishmentDetailUiController;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.FinanceBillType;

import java.util.Date;

public class SystemSnapshotVo extends FinanceBillVo { //账
    //存储一次不修改
    private CommodityVo[] commodities;
    private ClientVo[] clients;
    private BankAccountVo[] bankAccounts;
    private String operatorId;

    public SystemSnapshotVo(String id, Date date, BillState state, CommodityVo[] commodities, ClientVo[] clients, BankAccountVo[] bankAccounts, String operatorId) {
        super(FinanceBillType.SystemSnapshot, id, date, state, operatorId);
        this.commodities = commodities;
        this.clients = clients;
        this.bankAccounts = bankAccounts;
    }

    public CommodityVo[] getCommodities() {
        return commodities;
    }

    public void setCommodities(CommodityVo[] commodities) {
        this.commodities = commodities;
    }

    public ClientVo[] getClients() {
        return clients;
    }

    public void setClients(ClientVo[] clients) {
        this.clients = clients;
    }

    public BankAccountVo[] getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(BankAccountVo[] bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    /**
     * Gets the NotificationActivateService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return NotificationActivateService
     */
    @Override
    public NotificationActivateService notificationActivateService() {
        return InitialEstablishmentBlFactory.getNotificationActivateService();
    }

    /**
     * Gets the NotificationAbandonService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return NotificationAbandonService
     */
    @Override
    public NotificationAbandonService notificationAbandonService() {
        return InitialEstablishmentBlFactory.getNotificationAbandonService();
    }

    /**
     * Gets the BillApprovalCompleteService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return BillApprovalCompleteService
     */
    @Override
    public BillApprovalCompleteService billApprovalCompleteService() {
        return InitialEstablishmentBlFactory.getBillApprovalCompleteService();
    }

    /**
     * Gets DeleteService corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DeleteService
     */
    @Override
    public DraftDeleteService deleteService() {
        return InitialEstablishmentBlFactory.getDraftDeleteService();
    }

    /**
     * Gets the DraftContinueWritableUiController service corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DraftContinueWritableUiController
     */
    @Override
    public DraftContinueWritableUiController continueWritableUi() {
        return null; // this type can't be reversed as there is no "save as draft" button on its ui.
    }

    @Override
    public BillDetailUi billDetailUi() {
        return new InitialEstablishmentDetailUiController();
    }
}
