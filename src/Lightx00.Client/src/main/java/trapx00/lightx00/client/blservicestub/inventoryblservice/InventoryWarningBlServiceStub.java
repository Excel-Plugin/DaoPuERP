package trapx00.lightx00.client.blservicestub.inventoryblservice;

import trapx00.lightx00.client.blservice.inventoryblservice.InventoryWarningBlService;
import trapx00.lightx00.client.vo.inventorystaff.InventoryDetailBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.InventoryBillQueryVo;

public class InventoryWarningBlServiceStub implements InventoryWarningBlService {
    @Override
    public ResultMessage submit(InventoryDetailBillVo bill) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage saveAsDraft(InventoryDetailBillVo bill) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage modify(String id, double modifyWarning) {
        return ResultMessage.Success;
    }


    @Override
    public InventoryDetailBillVo[] query(InventoryBillQueryVo inventoryBillQueryVo) {
        return new InventoryDetailBillVo[0];
    }

    @Override
    public String getId() {
        return null;
    }
}
