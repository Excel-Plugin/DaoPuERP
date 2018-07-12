package trapx00.lightx00.client.blservicestub.inventoryblservice;

import trapx00.lightx00.client.blservice.inventoryblservice.InventoryCheckBlService;
import trapx00.lightx00.client.vo.inventorystaff.InventoryPictureVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryViewVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

public class InventoryCheckBlServiceStub implements InventoryCheckBlService {


    /**
     * Checks the invenntory change between the begintime and endtime
     *
     * @param beginTime
     * @param endTime
     * @return The inventoryView during specified time range
     */
    @Override
    public InventoryViewVo getInventoryView(Date beginTime, Date endTime) {
        return null;
    }

    /**
     * Gets the inventory snapshot
     *
     * @return The inventory snapshot during that day
     */
    @Override
    public InventoryPictureVo getInventoryPicture() {
        return null;
    }

    @Override
    public ResultMessage export(String newfile, String name) {
        return null;
    }

    /**
     * Exports the inventory snapshot to the required file by excel
     *
     * @param newfile
     * @return whether the operation is done successfully
     */

}
