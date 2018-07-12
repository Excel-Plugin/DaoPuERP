package trapx00.lightx00.client.blservice.inventoryblservice;

import trapx00.lightx00.client.vo.inventorystaff.InventoryPictureVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryViewVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;



public interface InventoryCheckBlService {
    /**
     * Checks the invenntory change between the begintime and endtime
     * @param beginTime
     * @param endTime
     * @return The inventoryView during specified time range
     */
    InventoryViewVo getInventoryView(Date beginTime, Date endTime);//库存查看

    /**
     * Gets the inventory snapshot
     * @return The inventory snapshot during that day
     */
    InventoryPictureVo getInventoryPicture();//库存盘点

    /**
     * Exports the inventory snapshot to the required file by excel
     * @param newfile
     * @return whether the operation is done successfully
     */
    ResultMessage export(String newfile,String name);//导出Excel
}