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
import trapx00.lightx00.client.blservice.inventoryblservice.PurchaseBillBlService;
import trapx00.lightx00.client.blservice.notificationblservice.NotificationBlService;
import trapx00.lightx00.client.blservice.notificationblservice.NotificationBlServiceFactory;
import trapx00.lightx00.client.datafactory.inventorydataservicefactory.PurchaseBillDataServiceFactory;
import trapx00.lightx00.client.vo.salestaff.PurchaseBillVo;
import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseBillDataService;
import trapx00.lightx00.shared.po.ClientModificationFlag;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.InventoryModificationFlag;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.po.salestaff.PurchaseBillPo;
import trapx00.lightx00.shared.queryvo.PurchaseBillQueryVo;

import java.rmi.RemoteException;
import java.util.List;

public class PurchaseBillBlController implements PurchaseBillBlService, NotificationActivateService, NotificationAbandonService, DraftDeleteService, BillApprovalCompleteService, BillPoVoConverter<PurchaseBillPo, PurchaseBillVo> {
    EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
    PurchaseBillDataService dataService = PurchaseBillDataServiceFactory.getInstance();
    private CommonBillBlController<PurchaseBillVo, PurchaseBillPo, PurchaseBillQueryVo> commonBillBlController
            = new CommonBillBlController<>(dataService, "进货单", this);
    private NotificationBlService notificationService = NotificationBlServiceFactory.getInstance();
    private ClientModificationService clientModificationService = ClientModificationServiceFactory.getInstance();
    private InventoryModificationService inventoryModificationService = InventoryModificationServiceFactory.getService();

    private String generatePurchaseBillMessage(String id) {
        String separator = " | ";
        String result = id + " up:" + System.lineSeparator();
        try {
            PurchaseBillPo purchaseBillPo = dataService.query(new PurchaseBillQueryVo().idEq(id))[0];
            CommodityItem[] commodityList = purchaseBillPo.getCommodityList();
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
            PurchaseBillPo purchaseBillPo = dataService.query(new PurchaseBillQueryVo().idEq(id))[0];
            clientModificationService.modifyClient(purchaseBillPo.getClientId(), ClientModificationFlag.PAYABLE, purchaseBillPo.getTotal());
            for (CommodityItem commodityItem : purchaseBillPo.getCommodityList()) {
                inventoryModificationService.modifyInventory(commodityItem.getCommodityId(), InventoryModificationFlag.Up, commodityItem.getNumber());
            }
            return commonBillBlController.activate(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * submit a purchaseBill
     *
     * @param purchaseBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(PurchaseBillVo purchaseBill) {
        return commonBillBlController.submit(purchaseBill);
    }

    /**
     * saves a half-completed purchaseBill as draft.
     *
     * @param purchaseBill PurchaseBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(PurchaseBillVo purchaseBill) {
        return commonBillBlController.saveAsDraft(purchaseBill);
    }

    /**
     * Gets the id for the next purchaseBill
     *
     * @return id for the next purchaseBill
     */
    @Override
    public String getId() {
        return commonBillBlController.getId();
    }

    /**
     * query for PurchaseBills
     *
     * @param query function
     * @return array of PurchaseBillVo
     */
    @Override
    public PurchaseBillVo[] queryPurchaseBillVo(PurchaseBillQueryVo query) {
        List<PurchaseBillVo> list = commonBillBlController.query(query);
        return list.toArray(new PurchaseBillVo[list.size()]);
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
    public PurchaseBillPo fromVoToPo(PurchaseBillVo vo) {
        return new PurchaseBillPo(vo.getId(), vo.getDate(), vo.getState(), vo.getClientId(), vo.getRepository(), vo.getOperatorId(), vo.getComment(), vo.getTotal(), vo.getCommodityList());
    }

    /**
     * Convert po to vo.
     *
     * @param po po
     * @return vo
     */
    @Override
    public PurchaseBillVo fromPoToVo(PurchaseBillPo po) {
        return new PurchaseBillVo(po.getId(), po.getDate(), po.getState(), po.getClientId(), po.getRepository(), po.getOperatorId(), po.getComment(), po.getTotal(), po.getCommodityList());
    }
}


