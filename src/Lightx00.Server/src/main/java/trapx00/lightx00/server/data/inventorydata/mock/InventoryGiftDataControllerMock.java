package trapx00.lightx00.server.data.inventorydata.mock;

import trapx00.lightx00.shared.dataservicestub.inventorydataservice.InventoryGiftDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.InventoryGiftPo;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;

import java.rmi.RemoteException;
import java.util.Date;

public class InventoryGiftDataControllerMock extends InventoryGiftDataServiceStub {

    public InventoryGiftDataControllerMock() throws RemoteException {
    }

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
