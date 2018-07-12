package trapx00.lightx00.client.bl.adminbl;

import trapx00.lightx00.client.bl.adminbl.factory.AdminEmployeeConverterFactory;
import trapx00.lightx00.client.bl.logbl.LogService;
import trapx00.lightx00.client.bl.logbl.factory.LogServiceFactory;
import trapx00.lightx00.client.blservice.adminblservice.UserManagementBlService;
import trapx00.lightx00.client.datafactory.admindataservicefactory.UserManagementDataServiceFactory;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.dataservice.admindataservice.UserManagementDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.SpecificUserAccountQueryVo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class UserManagementBlController implements UserManagementBlService, EmployeeInfo {
    private static HashMap<EmployeePosition, String> employeeName = new HashMap<>();
    private LogService logService = LogServiceFactory.getLogService();
    private UserManagementDataService dataService = UserManagementDataServiceFactory.getService();
    private AdminEmployeeConverter converter = AdminEmployeeConverterFactory.getAdminEmployeeConverter();

    public static void init() {
        employeeName.put(EmployeePosition.Admin, "系统管理职员");
        employeeName.put(EmployeePosition.Manager, "总经理");
        employeeName.put(EmployeePosition.FinanceStaff, "财务管理职员");
        employeeName.put(EmployeePosition.InventoryStaff, "库存管理职员");
        employeeName.put(EmployeePosition.SaleStaff, "进货销售职员");
    }

    /**
     * Create a user account for a new employee.
     *
     * @param account a user account to be created
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(EmployeeVo account) {
        try {
            ResultMessage opResult = dataService.add(converter.fromVoToPo(account));
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("创建一名%s，内容是%s。", employeeName.get(account.getPosition()), account.toString()));
            } else {
                logService.log(LogSeverity.Failure, String.format("创建一名%s失败，原因不明。内容是%s。", employeeName.get(account.getPosition()), account.toString()));
            }
            return opResult;
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Modify some information of a user account.
     *
     * @param account a user account to be modified
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(EmployeeVo account) {
        try {
            ResultMessage opResult = dataService.modify(converter.fromVoToPo(account));
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("修改一名%s，内容是%s。", employeeName.get(account.getPosition()), account.toString()));
            } else {
                logService.log(LogSeverity.Failure, String.format("修改一名%s失败，原因不明。内容是%s。", employeeName.get(account.getPosition()), account.toString()));
            }
            return opResult;
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Filter a user account.
     *
     * @param query the filter conditions
     * @return array of EmployeeVo which match the conditions
     */
    @Override
    public EmployeeVo[] query(UserAccountQueryVo query) {
        try {
            EmployeePo[] poList = dataService.query(query);
            List<EmployeeVo> temp = Arrays.stream(poList).map(x -> converter.fromPoToVo(x)).collect(Collectors.toList());
            return temp.toArray(new EmployeeVo[temp.size()]);
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Delete an needless user account.
     *
     * @param account the user account to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(EmployeeVo account) {
        try {
            ResultMessage opResult = dataService.delete(converter.fromVoToPo(account));
            if (opResult.isSuccess()) {
                logService.log(LogSeverity.Success, String.format("删除一名%s，内容是%s。", employeeName.get(account.getPosition()), account.toString()));
            } else {
                logService.log(LogSeverity.Failure, String.format("删除一名%s失败，原因不明。内容是%s。", employeeName.get(account.getPosition()), account.toString()));
            }
            return opResult;
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Queries EmployeeVo with queryvo.
     *
     * @param queryVo query vo
     * @return all employeevos that match condition. if 0 return new EmployeeVo[]
     */
    @Override
    public EmployeeVo[] queryEmployee(UserAccountQueryVo queryVo) {
        return query(queryVo);
    }

    @Override
    public EmployeeVo queryById(String id) {
        try {
            EmployeePo[] poList = dataService.query(
                new UserAccountQueryVo().addQueryVoForAllEmployeePosition(
                    (SpecificUserAccountQueryVo) new SpecificUserAccountQueryVo().idEq(id)));
            return poList.length == 0 ? null : converter.fromPoToVo(poList[0]);
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }
    @Override
    public String getId(){
        try {
            return dataService.getId();
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

}
