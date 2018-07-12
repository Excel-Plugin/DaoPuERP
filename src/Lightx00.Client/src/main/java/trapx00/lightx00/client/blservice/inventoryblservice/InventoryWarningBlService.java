package trapx00.lightx00.client.blservice.inventoryblservice;

import trapx00.lightx00.client.vo.inventorystaff.InventoryDetailBillVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.InventoryBillQueryVo;

public interface InventoryWarningBlService {
    //库存监控

    /**
     * Submits a bill
     * @param bill
     * @return  whether the operation is done successfully
     */
    ResultMessage submit(InventoryDetailBillVo bill);//提交

    /**
     * Saves the current bil
     * @param bill
     * @return  whether the operation is done successfully
     */
    ResultMessage saveAsDraft(InventoryDetailBillVo bill);//保存草稿

    /**
     *  Modifys the warning value of the commoditybl
     * @param id
     * @param modifyWarning
     * @return  whether the operation is done successfully
     */
    ResultMessage modify(String id, double modifyWarning);//修改警戒值

    /**
     *  Querys a bill
     * @param inventoryBillQueryVo
     * @return InventoryDetailBillVo
     */
    InventoryDetailBillVo[]query(InventoryBillQueryVo inventoryBillQueryVo);
    /**
     * Gets the id for the next bill.
     * @return id for the next bill
     */
    String getId();

}