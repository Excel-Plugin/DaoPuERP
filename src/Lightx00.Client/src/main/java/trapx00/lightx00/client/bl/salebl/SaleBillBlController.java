package trapx00.lightx00.client.bl.salebl;

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
import trapx00.lightx00.client.bl.notificationbl.NotificationService;
import trapx00.lightx00.client.bl.notificationbl.factory.NotificationServiceFactory;
import trapx00.lightx00.client.bl.promotionbl.PromotionInfo;
import trapx00.lightx00.client.bl.promotionbl.couponbl.SendCouponInfo;
import trapx00.lightx00.client.bl.promotionbl.couponbl.UseCouponInfo;
import trapx00.lightx00.client.bl.promotionbl.couponbl.factory.CouponFactory;
import trapx00.lightx00.client.bl.promotionbl.factory.PromotionInfoFactory;
import trapx00.lightx00.client.bl.util.BillPoVoConverter;
import trapx00.lightx00.client.bl.util.CommonBillBlController;
import trapx00.lightx00.client.blservice.saleblservice.SaleBillBlService;
import trapx00.lightx00.client.datafactory.saledataservicefactory.SaleBillDataServiceFactory;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.client.vo.notification.others.OtherNotificationVo;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.shared.dataservice.saledataservice.SaleBillDataService;
import trapx00.lightx00.shared.po.ClientModificationFlag;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.inventorystaff.InventoryModificationFlag;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.po.salestaff.SaleBillPo;
import trapx00.lightx00.shared.queryvo.SaleBillQueryVo;
import trapx00.lightx00.shared.queryvo.SpecificUserAccountQueryVo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public class SaleBillBlController implements SaleBillBlService, NotificationActivateService, NotificationAbandonService, DraftDeleteService, BillApprovalCompleteService, BillPoVoConverter<SaleBillPo, SaleBillVo> {
    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
    private SaleBillDataService dataService = SaleBillDataServiceFactory.getInstance();
    private CommonBillBlController<SaleBillVo, SaleBillPo, SaleBillQueryVo> commonBillBlController
            = new CommonBillBlController<>(dataService, "销售单", this);
    private NotificationService notificationService = NotificationServiceFactory.getNotificationService();
    private SendCouponInfo sendCouponInfo = CouponFactory.getSendCouponInfo();
    private UseCouponInfo useCouponInfo = CouponFactory.getUseCouponInfo();
    private PromotionInfo promotionInfo = PromotionInfoFactory.getPromotionInfo();
    private ClientModificationService clientModificationService = ClientModificationServiceFactory.getInstance();
    private InventoryModificationService inventoryModificationService = InventoryModificationServiceFactory.getService();

    private String generateSaleBillMessage(String id) {
        try {
            SaleBillPo saleBillPo = dataService.query(new SaleBillQueryVo().eq("id", id))[0];
            StringBuilder result = new StringBuilder();
            if (saleBillPo.getGiftList() != null) {
                for (CommodityItem commodityItem : saleBillPo.getGiftList()) {
                    result.append(commodityItem.getCommodityId());
                    result.append("|");
                    result.append(commodityItem.getNumber());
                    result.append(System.lineSeparator());
                }
            } else {
                result.append("none");
            }
            return new String(result);
        } catch (RemoteException e) {
            e.printStackTrace();
            return "error";
        }
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
            SaleBillPo saleBillPo = dataService.query(new SaleBillQueryVo().idEq(id))[0];
            EmployeeVo[] employeeVos = employeeInfo.queryEmployee(new UserAccountQueryVo().addQueryVoForOneEmployeePosition(EmployeePosition.InventoryStaff, new SpecificUserAccountQueryVo()));
            notificationService.addNotification(new OtherNotificationVo(new Date(), employeeInfo.queryById(saleBillPo.getOperatorId()), employeeVos, generateSaleBillMessage(id)));
            clientModificationService.modifyClient(saleBillPo.getClientId(), ClientModificationFlag.RECEIVABLE, saleBillPo.getUltiTotal());
            sendCouponInfo.sendCoupon(saleBillPo.getGiftToken());
            useCouponInfo.useCoupon(saleBillPo.getToken());
            for (CommodityItem commodityItem : saleBillPo.getCommodityList()) {
                inventoryModificationService.modifyInventory(commodityItem.getCommodityId(), InventoryModificationFlag.Low, commodityItem.getNumber());
            }
            return commonBillBlController.activate(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * submit a saleBill
     *
     * @param saleBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(SaleBillVo saleBill) {
        return commonBillBlController.submit(saleBill);
    }

    /**
     * saves a half-completed saleBill as draft.
     *
     * @param saleBill SaleBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(SaleBillVo saleBill) {
        return commonBillBlController.saveAsDraft(saleBill);
    }

    /**
     * Gets the id for the next saleBill
     *
     * @return id for the next saleBill
     */
    @Override
    public String getId() {
        return commonBillBlController.getId();
    }

    /**
     * query for SaleBills
     *
     * @param query function
     * @return array of SaleBillVo
     */
    @Override
    public SaleBillVo[] querySaleBill(SaleBillQueryVo query) {
        List<SaleBillVo> list = commonBillBlController.query(query);
        return list.toArray(new SaleBillVo[list.size()]);
    }

    /**
     * query for sale promotion
     *
     * @param saleBillVo current sale bill
     * @return promotions
     */
    @Override
    public PromotionVoBase[] queryPromotion(SaleBillVo saleBillVo) {
        return promotionInfo.queryPromotion(saleBillVo);
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
    public SaleBillPo fromVoToPo(SaleBillVo vo) {
        return new SaleBillPo(vo.getId(), vo.getDate(), vo.getState(), vo.getClientId(), vo.getSalesmanId(), vo.getOperatorId(), vo.getRepository(), vo.getCommodityList(), vo.getOriginTotal(), vo.getMinusProfits(), vo.getToken(), vo.getUltiTotal(), vo.getComment(), vo.getClientLevel(), vo.getPromotionId(), vo.getGiftList(), vo.getGiftToken());
    }

    /**
     * Convert po to vo.
     *
     * @param po po
     * @return vo
     */
    @Override
    public SaleBillVo fromPoToVo(SaleBillPo po) {
        return new SaleBillVo(po.getId(), po.getDate(), po.getState(), po.getClientId(), po.getSalesmanId(), po.getOperatorId(), po.getRepository(), po.getCommodityList(), po.getOriginTotal(), po.getMinusProfits(), po.getToken(), po.getUltiTotal(), po.getComment(), po.getClientLevel(), po.getPromotionId(), po.getGiftList(), po.getGiftToken());
    }
}
