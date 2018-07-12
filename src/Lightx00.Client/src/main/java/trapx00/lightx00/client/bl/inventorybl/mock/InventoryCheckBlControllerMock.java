package trapx00.lightx00.client.bl.inventorybl.mock;

import trapx00.lightx00.client.bl.inventorybl.InventoryCheckBlController;
import trapx00.lightx00.client.vo.inventorystaff.InventoryPictureVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryViewVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

public class InventoryCheckBlControllerMock extends InventoryCheckBlController {
    /**
     * Checks the invenntory change between the begintime and endtime
     *
     * @param beginTime
     * @param endTime
     * @return The inventoryView during specified time rangeget
     */
    @Override
    public InventoryViewVo getInventoryView(Date beginTime, Date endTime) {
        return super.getInventoryView(beginTime, endTime);
    }

    /**
     * Gets the inventory snapshot
     *
     * @return The inventory snapshot during that day
     */
    @Override
    public InventoryPictureVo getInventoryPicture() {
        return super.getInventoryPicture();
    }

    /**
     * Exports the inventory snapshot to the required file by excel
     *
     * @param path
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage export(String path,String name) {
        return super.export(path,name);
    }
}
