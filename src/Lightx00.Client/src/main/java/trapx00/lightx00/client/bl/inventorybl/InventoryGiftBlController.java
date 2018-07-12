package trapx00.lightx00.client.bl.inventorybl;

import trapx00.lightx00.client.bl.approvalbl.BillApprovalCompleteService;
import trapx00.lightx00.client.bl.commoditybl.InventoryModificationService;
import trapx00.lightx00.client.bl.commoditybl.factory.InventoryModificationServiceFactory;
import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.notificationbl.NotificationAbandonService;
import trapx00.lightx00.client.bl.notificationbl.NotificationActivateService;
import trapx00.lightx00.client.bl.salebl.SaleBillBlInfo;
import trapx00.lightx00.client.bl.salebl.factory.SaleBillBlInfoFactory;
import trapx00.lightx00.client.bl.util.BillPoVoConverter;
import trapx00.lightx00.client.bl.util.CommonBillBlController;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryGiftBlService;
import trapx00.lightx00.client.datafactory.inventorydataservicefactory.InventoryGiftDataServiceFactory;
import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.shared.dataservice.inventorydataservice.InventoryGiftDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.bill.BillType;
import trapx00.lightx00.shared.po.inventorystaff.InventoryGiftPo;
import trapx00.lightx00.shared.po.inventorystaff.InventoryModificationFlag;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.queryvo.InventoryGiftQueryVo;
import trapx00.lightx00.shared.queryvo.SaleBillQueryVo;

import java.util.List;

public  class InventoryGiftBlController implements InventoryGiftInfo, BillApprovalCompleteService,InventoryGiftBlService,NotificationAbandonService,NotificationActivateService,DraftDeleteService,BillPoVoConverter<InventoryGiftPo, InventoryGiftVo> {


    private InventoryGiftDataService dataService= InventoryGiftDataServiceFactory.getService();
    private SaleBillBlInfo saleBillBlInfo= SaleBillBlInfoFactory.getSaleBillBlInfo();
    private InventoryModificationService inventoryModificationService= InventoryModificationServiceFactory.getService();


    private CommonBillBlController<InventoryGiftVo, InventoryGiftPo, InventoryGiftQueryVo> commonBillBlController
            = new CommonBillBlController<>(dataService, "库存赠送单", this);

    public InventoryGiftVo fromPoToVo(InventoryGiftPo po) {
        return new InventoryGiftVo(po.getId(), po.getDate(), po.getState(), po.getGifts(),po.getOperatorId());

    }

    public InventoryGiftPo fromVoToPo(InventoryGiftVo vo) {
        return new InventoryGiftPo(BillType.InventoryBill,vo.getId(), vo.getDate(), vo.getState(),vo.getInventoryBillType(),vo.getGifts(),vo.getOperatorId());
    }

    public CommodityItem[] getPromotionCommodity(String id){
        SaleBillVo saleBillVos=saleBillBlInfo.querySaleBill(new SaleBillQueryVo().idEq(id))[0];
        return saleBillVos.getGiftList();
    }
    /**
     * Submits a GiftBill.
     * @param inventoryGiftVo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage sumbit(InventoryGiftVo inventoryGiftVo) {
        return commonBillBlController.submit(inventoryGiftVo);
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
        //通过后修改库存,完成赠送操作
        try {
            ResultMessage resultMessage=commonBillBlController.activate(id);
            if(resultMessage.isSuccess()){
                InventoryGiftPo inventoryGiftPo=dataService.query(new InventoryGiftQueryVo().idEq(id))[0];
                int length=inventoryGiftPo.getGifts().length;
                for(int i=0;i<length;i++){
                    inventoryModificationService.modifyInventory(inventoryGiftPo.getGifts()[i].getCommodityId(), InventoryModificationFlag.Low,
                            inventoryGiftPo.getGifts()[i].getAmount());
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
    public InventoryGiftVo[] query(InventoryGiftQueryVo inventoryBillQueryVo) {
        List<InventoryGiftVo> result = commonBillBlController.query(inventoryBillQueryVo);
        return result.toArray(new InventoryGiftVo[result.size()]);
    }



    @Override
    public ResultMessage saveAsDraft(InventoryGiftVo bill) {
        return  commonBillBlController.saveAsDraft(bill);
    }

    @Override
    public InventoryGiftVo[] queryInventoryGiftBill(InventoryGiftQueryVo inventoryBillQueryVo) {
        List<InventoryGiftVo> result = commonBillBlController.query(inventoryBillQueryVo);
        return result.toArray(new InventoryGiftVo[result.size()]);
    }
}
