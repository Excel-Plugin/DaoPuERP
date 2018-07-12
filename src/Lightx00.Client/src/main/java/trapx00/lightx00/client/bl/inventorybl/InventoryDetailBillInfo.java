package trapx00.lightx00.client.bl.inventorybl;


import trapx00.lightx00.client.vo.inventorystaff.InventoryDetailBillVo;
import trapx00.lightx00.shared.queryvo.InventoryBillQueryVo;

public interface InventoryDetailBillInfo {
    /**
     * Querys inventoryBill(Loss,Warning...)
     * @param inventoryBillQueryVo
     * @return  the list of inventoryBIlls that match to the requirement
     */
    InventoryDetailBillVo[] queryInventoryWarningBill(InventoryBillQueryVo inventoryBillQueryVo);



}
