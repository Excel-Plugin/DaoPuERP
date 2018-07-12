package trapx00.lightx00.shared.dataservicestub.inventorydataservice;

import trapx00.lightx00.shared.dataservice.inventorydataservice.InventoryGiftDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.InventoryGiftPo;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.queryvo.InventoryGiftQueryVo;

import java.rmi.RemoteException;
import java.util.Date;

public class InventoryGiftDataServiceStub implements InventoryGiftDataService {

    @Override
    public ResultMessage submit(InventoryGiftPo inventoryGiftPo) {
        Date date=new Date();
        PromotionCommodity pCommodity=new PromotionCommodity();
        PromotionCommodity[] input={null};
        return ResultMessage.Success;
    }

    @Override
    public String getId() {
        return "X0001";
    }

    @Override
    public InventoryGiftPo[] query(InventoryGiftQueryVo query) throws RemoteException {
        return new InventoryGiftPo[0];
    }


    @Override
    public ResultMessage activate(String id) throws RemoteException {
        return null;
    }

    @Override
    public ResultMessage abandon(String id) throws RemoteException {
        return null;
    }

    /**
     * Changes the state of a bill if approval is completed.
     *
     * @param billId    the id of the bill.
     * @param billState new bill state. Only Approved and Rejected is allowed.
     * @return whether the operation is done successfully.
     */
    @Override
    public ResultMessage approvalComplete(String billId, BillState billState) throws RemoteException {
        return ResultMessage.Success;
    }
}
