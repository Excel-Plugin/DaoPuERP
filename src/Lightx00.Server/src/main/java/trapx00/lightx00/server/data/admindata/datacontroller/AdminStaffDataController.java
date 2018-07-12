package trapx00.lightx00.server.data.admindata.datacontroller;

import trapx00.lightx00.server.data.admindata.SpecificEmployeeDataController;
import trapx00.lightx00.server.data.admindata.factory.AdminDataDaoFactory;
import trapx00.lightx00.shared.po.admin.AdminPo;

public class AdminStaffDataController extends SpecificEmployeeDataController<AdminPo> {

    public AdminStaffDataController() {
        super(AdminDataDaoFactory.getAdminDao());
    }
}
