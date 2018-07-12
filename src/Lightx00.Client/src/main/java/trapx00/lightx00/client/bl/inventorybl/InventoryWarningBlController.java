package trapx00.lightx00.client.bl.inventorybl;

import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.EmployeeInfoFactory;
import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.commoditybl.CommodityInfo;
import trapx00.lightx00.client.bl.commoditybl.InventoryModificationService;
import trapx00.lightx00.client.bl.commoditybl.factory.CommodityServiceFactory;
import trapx00.lightx00.client.bl.commoditybl.factory.InventoryModificationServiceFactory;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.bl.util.BillPoVoConverter;
import trapx00.lightx00.client.bl.util.CommonBillBlController;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryWarningBlService;
import trapx00.lightx00.client.blservice.notificationblservice.NotificationBlService;
import trapx00.lightx00.client.blservice.notificationblservice.NotificationBlServiceFactory;
import trapx00.lightx00.client.datafactory.inventorydataservicefactory.InventoryWarningDataServiceFactory;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryDetailBillVo;
import trapx00.lightx00.client.vo.notification.others.OtherNotificationVo;
import trapx00.lightx00.shared.dataservice.inventorydataservice.InventoryWarningDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.inventorystaff.InventoryBillType;
import trapx00.lightx00.shared.po.inventorystaff.InventoryDetailBillPo;
import trapx00.lightx00.shared.po.inventorystaff.InventoryModificationFlag;
import trapx00.lightx00.shared.po.inventorystaff.InventoryWarningItem;
import trapx00.lightx00.shared.queryvo.InventoryBillQueryVo;
import trapx00.lightx00.shared.queryvo.SpecificUserAccountQueryVo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;


public class InventoryWarningBlController implements InventoryDetailBillInfo,BillApprovalCompleteService, InventoryWarningBlService,DraftDeleteService,NotificationAbandonService,NotificationActivateService ,BillPoVoConverter<InventoryDetailBillPo, InventoryDetailBillVo> {

    private InventoryWarningDataService dataService= InventoryWarningDataServiceFactory.getService();
    private CommodityInfo commodityInfo= CommodityServiceFactory.getController();
    private InventoryModificationService inventoryModificationService= InventoryModificationServiceFactory.getService();
    private NotificationBlService notificationService = NotificationBlServiceFactory.getInstance();
    EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();

    private CommonBillBlController<InventoryDetailBillVo, InventoryDetailBillPo, InventoryBillQueryVo> commonBillBlController
            = new CommonBillBlController<>(dataService, "库存监控单", this);

    public InventoryDetailBillVo fromPoToVo(InventoryDetailBillPo po) {
        return new InventoryDetailBillVo(po.getId(), po.getDate(), po.getState(), po.getOperatorId(), po.getCommodityList(), po.getInventoryBillType());

    }

    public InventoryDetailBillPo fromVoToPo(InventoryDetailBillVo vo) {
        return new InventoryDetailBillPo(vo.getId(), vo.getDate(), vo.getState(), vo.getInventoryBillType(),vo.getCommodities(),vo.getOperatorId());
    }

    private String generatePurchaseBillMessage(String id) {
        String separator = " | ";
        String result = id + " up:" + System.lineSeparator();
        try {
            InventoryDetailBillPo inventoryDetailBillPo = dataService.query(new InventoryBillQueryVo().idEq(id))[0];
            InventoryWarningItem[] commodityList = inventoryDetailBillPo.getCommodityList();
            for (InventoryWarningItem commodityItem : commodityList) {
                result += commodityItem.getId() + separator + commodityItem.getDelta() + System.lineSeparator();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Submits a Bill.
     * @param bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage submit(InventoryDetailBillVo bill) {
        return commonBillBlController.submit(bill);
    }

    /**
     * Saves a half-completed Bill as a draft.
     * @param bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage saveAsDraft(InventoryDetailBillVo bill) {
        return  commonBillBlController.saveAsDraft(bill);
    }

    /**
     * Modifys the warning value of the commoditybl
     * @param id ,the id of the commoditybl
     * @param modifyWarning
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(String id, double modifyWarning) {
        return commodityInfo.update(id,modifyWarning);
    }



    /**
     *  Querys a bill
     * @param query
     * @return InventoryDetailBillVo
     */
    @Override
    public InventoryDetailBillVo[] query(InventoryBillQueryVo query) {
        List<InventoryDetailBillVo> result = commonBillBlController.query(query);
        return result.toArray(new InventoryDetailBillVo[result.size()]);
    }

    /**
     * Gets the id for the next bill.
     * @return id for the next bill
     */
    @Override
    public String getId() {
        return commonBillBlController.getId();
    }

    /**
     *  Deletes a draft.
     * @param id id of the draft to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage deleteDraft(String id) {
        return commonBillBlController.deleteDraft(id);
    }

    /**
     * Abandons a bill.
     * @param id id for the bill
     * @return  whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(String id) {
        return commonBillBlController.abandon(id);
    }

    /**
     *  Activates a bill that has been approved of.
     * @param id id for the bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage activate(String id) {
        try {
            ResultMessage resultMessage=commonBillBlController.activate(id);
            if(resultMessage.isSuccess()){
                InventoryDetailBillPo inventoryDetailBillPo=dataService.query(new InventoryBillQueryVo().idEq(id))[0];
                int length=inventoryDetailBillPo.getCommodityList().length;
                if(inventoryDetailBillPo.getInventoryBillType()== InventoryBillType.Warning){
                    EmployeeVo[] employeeVos = employeeInfo.queryEmployee(new UserAccountQueryVo().addQueryVoForOneEmployeePosition(EmployeePosition.SaleStaff, new SpecificUserAccountQueryVo()));
                    notificationService.acknowledge(new OtherNotificationVo(new Date(), employeeInfo.queryById(inventoryDetailBillPo.getOperatorId()), employeeVos, generatePurchaseBillMessage(id)));
                    return ResultMessage.Success;
                }else{
                    for(int i=0;i<length;i++){
                        inventoryModificationService.modifyInventory(inventoryDetailBillPo.getCommodityList()[i].getId(),
                                InventoryModificationFlag.Up,inventoryDetailBillPo.getCommodityList()[i].getDelta());
                    }
                }
                return ResultMessage.Success;
            }
            return ResultMessage.Failure;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
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

    @Override
    public InventoryDetailBillVo[] queryInventoryWarningBill(InventoryBillQueryVo inventoryBillQueryVo) {
        List<InventoryDetailBillVo> result = commonBillBlController.query(inventoryBillQueryVo);
        return result.toArray(new InventoryDetailBillVo[result.size()]);
    }
}
