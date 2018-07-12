package trapx00.lightx00.client.vo.inventorystaff;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.inventorybl.factory.InventoryWarningServiceFactory;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;
import trapx00.lightx00.client.presentation.helpui.ReversibleUi;
import trapx00.lightx00.client.presentation.inventoryui.warning.InventoryWarningDetailUi;
import trapx00.lightx00.client.presentation.inventoryui.warning.InventoryWarningUiController;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.InventoryBillType;
import trapx00.lightx00.shared.po.inventorystaff.InventoryWarningItem;

import java.util.Date;

public  class InventoryDetailBillVo extends InventoryBillBaseVo implements Reversible {
    //报损报溢报警
    private InventoryWarningItem[] commodities;//商品
    private InventoryBillType type;

    public InventoryDetailBillVo(String id, Date date, BillState state, String operatorId, InventoryWarningItem[] commodities,  InventoryBillType type) {
        super(id, date, state,type,operatorId);
        this.commodities = commodities;
        this.type = type;
    }


    public InventoryWarningItem[] getCommodities() {
        return commodities;
    }

    public void setCommodities(InventoryWarningItem[] commodities) {
        this.commodities = commodities;
    }

    public InventoryBillType getType() {
        return type;
    }

    /**
     * Gets the BillApprovalCompleteService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return BillApprovalCompleteService
     */
    @Override
    public BillApprovalCompleteService billApprovalCompleteService() {
        return InventoryWarningServiceFactory.getBillApprovalCompleteService();
    }

    /**
     * Gets the NotificationActivateService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return NotificationActivateService
     */
    @Override
    public NotificationActivateService notificationActivateService() {
        return InventoryWarningServiceFactory.getNotificationActivateService();
    }

    /**
     * Gets the NotificationAbandonService corresponding to this type of bill. Overrides to meet the specific bill type.
     *
     * @return NotificationAbandonService
     */
    @Override
    public NotificationAbandonService notificationAbandonService() {
        return InventoryWarningServiceFactory.getNotificationAbandonService();
    }

    /**
     * Gets DeleteService corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DeleteService
     */
    @Override
    public DraftDeleteService deleteService() {
        return InventoryWarningServiceFactory.getDraftDeleteService();
    }

    /**
     * Gets the DraftContinueWritableUiController service corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DraftContinueWritableUiController
     */
    @Override
    public DraftContinueWritableUiController continueWritableUi() {
        return new InventoryWarningUiController();
    }

    @Override
    public BillDetailUi billDetailUi() {
        return new InventoryWarningDetailUi();
    }

    /**
     * When it is called, it returns a ReversibleUi which can be used to acquire the ui component and controller.
     *
     * @return reversible ui service.
     */
    @Override
    public ReversibleUi reversibleUi() {
        return new InventoryWarningUiController();
    }
}
