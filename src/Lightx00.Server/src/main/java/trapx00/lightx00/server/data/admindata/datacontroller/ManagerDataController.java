package trapx00.lightx00.server.data.admindata.datacontroller;

import trapx00.lightx00.server.data.admindata.SpecificEmployeeDataController;
import trapx00.lightx00.server.data.admindata.factory.AdminDataDaoFactory;
import trapx00.lightx00.shared.po.manager.ManagerPo;

public class ManagerDataController extends SpecificEmployeeDataController<ManagerPo> {
    public ManagerDataController() {
        super(AdminDataDaoFactory.getManagerDao());
    }
}
