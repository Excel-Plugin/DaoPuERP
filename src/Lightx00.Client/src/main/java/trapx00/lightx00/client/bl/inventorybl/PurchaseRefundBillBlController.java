package trapx00.lightx00.client.bl.inventorybl;

import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.EmployeeInfoFactory;
import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.clientbl.ClientModificationService;
import trapx00.lightx00.client.bl.clientbl.factory.ClientModificationServiceFactory;
import trapx00.lightx00.client.bl.commoditybl.InventoryModificationService;
import trapx00.lightx00.client.bl.commoditybl.factory.InventoryModificationServiceFactory;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.bl.util.BillPoVoConverter;
import trapx00.lightx00.client.bl.util.CommonBillBlController;
import trapx00.lightx00.client.blservice.inventoryblservice.PurchaseRefundBillBlService;
import trapx00.lightx00.client.blservice.notificationblservice.NotificationBlService;
import trapx00.lightx00.client.blservice.notificationblservice.NotificationBlServiceFactory;
import trapx00.lightx00.client.datafactory.inventorydataservicefactory.PurchaseRefundBillDataServiceFactory;
import trapx00.lightx00.client.vo.salestaff.PurchaseRefundBillVo;
import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseRefundBillDataService;
import trapx00.lightx00.shared.po.ClientModificationFlag;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.InventoryModificationFlag;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.po.salestaff.PurchaseRefundBillPo;
import trapx00.lightx00.shared.queryvo.PurchaseRefundBillQueryVo;

import java.rmi.RemoteException;
import java.util.List;

public class PurchaseRefundBillBlController implements PurchaseRefundBillBlService, NotificationActivateService, NotificationAbandonService, DraftDeleteService, BillApprovalCompleteService, BillPoVoConverter<PurchaseRefundBillPo, PurchaseRefundBillVo> {
    EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
    PurchaseRefundBillDataService dataService = PurchaseRefundBillDataServiceFactory.getInstance();
    private CommonBillBlController<PurchaseRefundBillVo, PurchaseRefundBillPo, PurchaseRefundBillQueryVo> commonBillBlController
            = new CommonBillBlController<>(dataService, "进货退货单", this);
    private NotificationBlService notificationService = NotificationBlServiceFactory.getInstance();
    private ClientModificationService clientModificationService = ClientModificationServiceFactory.getInstance();
    private InventoryModificationService inventoryModificationService = InventoryModificationServiceFactory.getService();

    private String generatePurchaseRefundBillMessage(String id) {
        String separator = " | ";
        String result = id + " low:" + System.lineSeparator();
        try {
            PurchaseRefundBillPo purchaseRefundBillPo = dataService.query(new PurchaseRefundBillQueryVo().idEq(id))[0];
            CommodityItem[] commodityList = purchaseRefundBillPo.getCommodityList();
            for (CommodityItem commodityItem : commodityList) {
                result += commodityItem.getCommodityId() + separator + commodityItem.getNumber() + System.lineSeparator();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
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
        try {
            PurchaseRefundBillPo purchaseRefundBillPo = dataService.query(new PurchaseRefundBillQueryVo().idEq(id))[0];
            clientModificationService.modifyClient(purchaseRefundBillPo.getClientId(), ClientModificationFlag.PAYABLE, -purchaseRefundBillPo.getTotal());
            for (CommodityItem commodityItem : purchaseRefundBillPo.getCommodityList()) {
                inventoryModificationService.modifyInventory(commodityItem.getCommodityId(), InventoryModificationFlag.Low, commodityItem.getNumber());
            }
            return commonBillBlController.activate(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * submit a purchaseRefundBill
     *
     * @param purchaseRefundBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(PurchaseRefundBillVo purchaseRefundBill) {
        return commonBillBlController.submit(purchaseRefundBill);
    }

    /**
     * saves a half-completed purchaseRefundBill as draft.
     *
     * @param purchaseRefundBill PurchaseRefundBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(PurchaseRefundBillVo purchaseRefundBill) {
        return commonBillBlController.saveAsDraft(purchaseRefundBill);
    }

    /**
     * Gets the id for the next purchaseRefundBill
     *
     * @return id for the next purchaseRefundBill
     */
    @Override
    public String getId() {
        return commonBillBlController.getId();
    }

    /**
     * query for PurchaseRefundBills
     *
     * @param query function
     * @return array of PurchaseRefundBillVo
     */
    @Override
    public PurchaseRefundBillVo[] queryPurchaseRefundBillVo(PurchaseRefundBillQueryVo query) {
        List<PurchaseRefundBillVo> list = commonBillBlController.query(query);
        return list.toArray(new PurchaseRefundBillVo[list.size()]);
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

    /**
     * Convert vo to po.
     *
     * @param vo vo
     * @return po
     */
    @Override
    public PurchaseRefundBillPo fromVoToPo(PurchaseRefundBillVo vo) {
        return new PurchaseRefundBillPo(vo.getId(), vo.getDate(), vo.getState(), vo.getClientId(), vo.getRepository(), vo.getOperatorId(), vo.getComment(), vo.getTotal(), vo.getCommodityList());
    }

    /**
     * Convert po to vo.
     *
     * @param po po
     * @return vo
     */
    @Override
    public PurchaseRefundBillVo fromPoToVo(PurchaseRefundBillPo po) {
        return new PurchaseRefundBillVo(po.getId(), po.getDate(), po.getState(), po.getClientId(), po.getRepository(), po.getOperatorId(), po.getComment(), po.getTotal(), po.getCommodityList());
    }
}
