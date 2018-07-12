package trapx00.lightx00.client.vo.salestaff;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.inventorybl.factory.PurchaseBillBlFactory;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;
import trapx00.lightx00.client.presentation.helpui.ReversibleUi;
import trapx00.lightx00.client.presentation.inventoryui.PurchaseBillDetailUiController;
import trapx00.lightx00.client.presentation.inventoryui.PurchaseBillUiController;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.po.salestaff.PurchaseBillType;

import java.util.Date;

public class PurchaseBillVo extends PurchaseBillBaseVo {
    private String clientId;
    private int repository;
    private String comment;
    private double total;
    private CommodityItem[] commodityList;

    public PurchaseBillVo(String id, Date date, BillState state, String clientId, int repository, String operatorId, String comment, double total, CommodityItem[] commodityList) {
        super(id, date, state, PurchaseBillType.Purchase, operatorId);
        this.clientId = clientId;
        this.repository = repository;
        this.comment = comment;
        this.total = total;
        this.commodityList = commodityList;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getRepository() {
        return repository;
    }

    public void setRepository(int repository) {
        this.repository = repository;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public CommodityItem[] getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(CommodityItem[] commodityList) {
        this.commodityList = commodityList;
    }

    /**
     * Gets the NotificationActivateService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return NotificationActivateService
     */
    @Override
    public NotificationActivateService notificationActivateService() {
        return PurchaseBillBlFactory.getNotificationActivateService();
    }

    /**
     * Gets the NotificationAbandonService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return NotificationAbandonService
     */
    @Override
    public NotificationAbandonService notificationAbandonService() {
        return PurchaseBillBlFactory.getNotificationAbandonService();
    }

    /**
     * Gets the BillApprovalCompleteService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return BillApprovalCompleteService
     */
    @Override
    public BillApprovalCompleteService billApprovalCompleteService() {
        return PurchaseBillBlFactory.getBillApprovalCompleteService();
    }

    /**
     * Gets DeleteService corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DeleteService
     */
    @Override
    public DraftDeleteService deleteService() {
        return PurchaseBillBlFactory.getDraftDeleteService();
    }

    /**
     * Gets the DraftContinueWritableUiController service corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DraftContinueWritableUiController
     */
    @Override
    public DraftContinueWritableUiController continueWritableUi() {
        return new PurchaseBillUiController();
    }

    @Override
    public BillDetailUi billDetailUi() {
        return new PurchaseBillDetailUiController();
    }

    /**
     * When it is called, it returns a ReversibleUi which can be used to acquire the ui component and controller.
     *
     * @return reversible ui service.
     */
    @Override
    public ReversibleUi reversibleUi() {
        return new PurchaseBillUiController();
    }
}
