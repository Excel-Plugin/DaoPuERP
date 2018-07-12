package trapx00.lightx00.server.data.admindata.datacontroller;
import trapx00.lightx00.server.data.admindata.SpecificEmployeeDataController;
import trapx00.lightx00.server.data.admindata.factory.AdminDataDaoFactory;
import trapx00.lightx00.shared.po.financestaff.FinanceStaffPo;

public class FinanceStaffDataController extends SpecificEmployeeDataController<FinanceStaffPo> {

    public FinanceStaffDataController() {
        super(AdminDataDaoFactory.getFinanceStaffDao());
    }
}
