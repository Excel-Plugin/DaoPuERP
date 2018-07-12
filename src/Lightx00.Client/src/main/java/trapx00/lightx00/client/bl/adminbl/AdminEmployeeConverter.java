package trapx00.lightx00.client.bl.adminbl;

import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.admin.AdminVo;
import trapx00.lightx00.client.vo.financestaff.FinanceStaffVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryStaffVo;
import trapx00.lightx00.client.vo.manager.ManagerVo;
import trapx00.lightx00.client.vo.salestaff.SaleStaffVo;
import trapx00.lightx00.shared.po.admin.AdminPo;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.po.financestaff.FinanceStaffPo;
import trapx00.lightx00.shared.po.inventorystaff.InventoryStaffPo;
import trapx00.lightx00.shared.po.manager.ManagerPo;
import trapx00.lightx00.shared.po.salestaff.SaleStaffPo;

public class AdminEmployeeConverter implements EmployeeConverter<EmployeeVo> {
    @Override
    public EmployeeVo fromPoToVo(EmployeePo po) {
        switch(po.getPosition()) {
            case Admin:
                return new AdminVo(po.getId(), po.getName(), po.getWorkSince(), po.getPassword(), po.getState());
            case Manager:
                return new ManagerVo(po.getId(), po.getName(), po.getWorkSince(), po.getPassword(), po.getState());
            case InventoryStaff:
                return new InventoryStaffVo(po.getId(), po.getName(), po.getWorkSince(), po.getPassword(), po.getState());
            case FinanceStaff:
                return new FinanceStaffVo(po.getId(), po.getName(), po.getWorkSince(), po.getPassword(), po.getState(), ((FinanceStaffPo)po).isRoot());
            case SaleStaff:
                return new SaleStaffVo(po.getId(), po.getName(), po.getWorkSince(), po.getPassword(), po.getState(), ((SaleStaffPo)po).isRoot(), ((SaleStaffPo)po).getSaleStaffType());
        }
        return null;
    }

    @Override
    public EmployeePo fromVoToPo(EmployeeVo vo) {
        switch(vo.getPosition()) {
            case Admin:
                return new AdminPo(vo.getId(),vo.getName(),vo.getWorkSince(),vo.getPassword(),vo.getState());
            case Manager:
                return new ManagerPo(vo.getId(),vo.getName(),vo.getWorkSince(),vo.getPassword(),vo.getState());
            case InventoryStaff:
                return new InventoryStaffPo(vo.getId(), vo.getName(), vo.getWorkSince(), vo.getPassword(),vo.getState());
            case FinanceStaff:
                return new FinanceStaffPo(vo.getId(),vo.getName(),vo.getWorkSince(),vo.getPassword(),vo.getState(),((FinanceStaffVo)vo).isRoot());
            case SaleStaff:
                return new SaleStaffPo(vo.getId(),vo.getName(),vo.getWorkSince(),vo.getPassword(),((SaleStaffVo)vo).getSaleStaffType(),vo.getState(),((SaleStaffVo)vo).isRoot());
        }
        return null;
    }

}
