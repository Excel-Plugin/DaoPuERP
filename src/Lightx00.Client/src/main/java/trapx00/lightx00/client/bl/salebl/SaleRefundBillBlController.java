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
import trapx00.lightx00.client.bl.util.BillPoVoConverter;
import trapx00.lightx00.client.bl.util.CommonBillBlController;
import trapx00.lightx00.client.blservice.notificationblservice.NotificationBlService;
import trapx00.lightx00.client.blservice.notificationblservice.NotificationBlServiceFactory;
import trapx00.lightx00.client.blservice.saleblservice.SaleRefundBillBlService;
import trapx00.lightx00.client.datafactory.saledataservicefactory.SaleRefundBillDataServiceFactory;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.salestaff.SaleRefundBillVo;
import trapx00.lightx00.shared.dataservice.saledataservice.SaleRefundBillDataService;
import trapx00.lightx00.shared.po.ClientModificationFlag;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.inventorystaff.InventoryModificationFlag;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.po.salestaff.SaleRefundBillPo;
import trapx00.lightx00.shared.queryvo.SaleRefundBillQueryVo;
import trapx00.lightx00.shared.queryvo.SpecificUserAccountQueryVo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.rmi.RemoteException;
import java.util.List;

public class SaleRefundBillBlController implements SaleRefundBillBlService, NotificationActivateService, NotificationAbandonService, DraftDeleteService, BillApprovalCompleteService, BillPoVoConverter<SaleRefundBillPo, SaleRefundBillVo> {
    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
    private SaleRefundBillDataService dataService = SaleRefundBillDataServiceFactory.getInstance();
    private CommonBillBlController<SaleRefundBillVo, SaleRefundBillPo, SaleRefundBillQueryVo> commonBillBlController
            = new CommonBillBlController<>(dataService, "销售退货单", this);
    private NotificationBlService notificationService = NotificationBlServiceFactory.getInstance();
    private ClientModificationService clientModificationService = ClientModificationServiceFactory.getInstance();
    private InventoryModificationService inventoryModificationService = InventoryModificationServiceFactory.getService();

    private String generateSaleRefundBillMessage(String id) {
        String separator = " | ";
        String result = id + " up:" + System.lineSeparator();
        try {
            SaleRefundBillPo saleRefundBillPo = dataService.query(new SaleRefundBillQueryVo().idEq(id))[0];
            CommodityItem[] commodityList = saleRefundBillPo.getCommodityList();
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
            SaleRefundBillPo saleRefundBillPo = dataService.query(new SaleRefundBillQueryVo().idEq(id))[0];
            EmployeeVo[] employeeVos = employeeInfo.queryEmployee(new UserAccountQueryVo().addQueryVoForOneEmployeePosition(EmployeePosition.InventoryStaff, new SpecificUserAccountQueryVo()));
            clientModificationService.modifyClient(saleRefundBillPo.getClientId(), ClientModificationFlag.RECEIVABLE, -saleRefundBillPo.getUltiTotal());
            for (CommodityItem commodityItem : saleRefundBillPo.getCommodityList()) {
                inventoryModificationService.modifyInventory(commodityItem.getCommodityId(), InventoryModificationFlag.Up, commodityItem.getNumber());
            }
            return commonBillBlController.activate(id);
        } catch (RemoteException e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * submit a saleRefundBill
     *
     * @param saleRefundBill to be submitted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(SaleRefundBillVo saleRefundBill) {
        return commonBillBlController.submit(saleRefundBill);
    }

    /**
     * saves a half-completed saleRefundBill as draft.
     *
     * @param saleRefundBill SaleRefundBillVo to be saved as a draft
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(SaleRefundBillVo saleRefundBill) {
        return commonBillBlController.saveAsDraft(saleRefundBill);
    }

    /**
     * Gets the id for the next saleRefundBill
     *
     * @return id for the next saleRefundBill
     */
    @Override
    public String getId() {
        return commonBillBlController.getId();
    }

    /**
     * query for SaleRefundBills
     *
     * @param query function
     * @return array of SaleRefundBillVo
     */
    @Override
    public SaleRefundBillVo[] querySaleRefundBill(SaleRefundBillQueryVo query) {
        List<SaleRefundBillVo> list = commonBillBlController.query(query);
        return list.toArray(new SaleRefundBillVo[list.size()]);
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
    public SaleRefundBillPo fromVoToPo(SaleRefundBillVo vo) {
        return new SaleRefundBillPo(vo.getId(), vo.getDate(), vo.getState(), vo.getClientId(), vo.getSalesmanId(), vo.getOperatorId(), vo.getRepository(), vo.getCommodityList(), vo.getOriginTotal(), vo.getMinusProfits(), vo.getToken(), vo.getUltiTotal(), vo.getComment());
    }

    /**
     * Convert po to vo.
     *
     * @param po po
     * @return vo
     */
    @Override
    public SaleRefundBillVo fromPoToVo(SaleRefundBillPo po) {
        return new SaleRefundBillVo(po.getId(), po.getDate(), po.getState(), po.getClientId(), po.getSalesmanId(), po.getOperatorId(), po.getRepository(), po.getCommodityList(), po.getOriginTotal(), po.getMinusProfits(), po.getToken(), po.getUltiTotal(), po.getComment());
    }
}
