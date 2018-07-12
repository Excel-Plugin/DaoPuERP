package trapx00.lightx00.client.vo;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.ContentDisplayUi;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;

import java.io.Serializable;
import java.util.Date;

public abstract class BillVo implements Draftable, Serializable {

    protected BillType billType;
    protected String id;
    protected Date date;
    protected BillState state;
    protected String operatorId;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public BillVo(BillType billType, String id, Date date, BillState state, String operatorId) {
        this.billType = billType;
        this.id = id;
        this.date = date;
        this.state = state;
        this.operatorId = operatorId;
    }



    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType bill) {
        this.billType = bill;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BillState getState() {
        return state;
    }

    public void setState(BillState state) {
        this.state = state;
    }

    /**
     * Gets the NotificationActivateService corresponding to this type of bill. Overrides to meet the specific bill type.
     * @return NotificationActivateService
     */

    public abstract NotificationActivateService notificationActivateService();

    /**
     * Gets the NotificationAbandonService corresponding to this type of bill. Overrides to meet the specific bill type.
     * @return NotificationAbandonService
     */
    public abstract NotificationAbandonService notificationAbandonService();

    /**
     * Gets the BillApprovalCompleteService corresponding to this type of bill. Overrides to meet the specific bill type.
     * @return BillApprovalCompleteService
     */
    public abstract BillApprovalCompleteService billApprovalCompleteService();

    /**
     * Gets DeleteService corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DeleteService
     */
    @Override
    public abstract DraftDeleteService deleteService();

    /**
     * Gets the DraftContinueWritableUiController service corresponding to this type of draft. Overrides to meet the specific bill type.
     *
     * @return DraftContinueWritableUiController
     */
    @Override
    public abstract DraftContinueWritableUiController continueWritableUi();

    public abstract BillDetailUi billDetailUi();

    /**
     * 显示详细信息UI
     *
     * @return 显示详细信息UI
     */
    @Override
    public ContentDisplayUi contentDisplayUi() {
        return billDetailUi();
    }
}
