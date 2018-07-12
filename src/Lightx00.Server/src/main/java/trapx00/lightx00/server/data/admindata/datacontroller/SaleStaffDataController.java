package trapx00.lightx00.server.data.admindata.datacontroller;

import trapx00.lightx00.server.data.admindata.SpecificEmployeeDataController;
import trapx00.lightx00.server.data.admindata.factory.AdminDataDaoFactory;
import trapx00.lightx00.shared.po.salestaff.SaleStaffPo;

public class SaleStaffDataController extends SpecificEmployeeDataController<SaleStaffPo> {
    public SaleStaffDataController() {
        super(AdminDataDaoFactory.getSaleStaffDao());
    }
}
