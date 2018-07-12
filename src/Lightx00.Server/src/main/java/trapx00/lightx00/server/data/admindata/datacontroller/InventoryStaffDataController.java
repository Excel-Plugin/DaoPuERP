package trapx00.lightx00.server.data.admindata.datacontroller;

import trapx00.lightx00.server.data.admindata.SpecificEmployeeDataController;
import trapx00.lightx00.server.data.admindata.factory.AdminDataDaoFactory;
import trapx00.lightx00.shared.po.inventorystaff.InventoryStaffPo;

public class InventoryStaffDataController extends SpecificEmployeeDataController<InventoryStaffPo> {
    public InventoryStaffDataController() {
        super(AdminDataDaoFactory.getInventoryStaffDao());
    }
}
