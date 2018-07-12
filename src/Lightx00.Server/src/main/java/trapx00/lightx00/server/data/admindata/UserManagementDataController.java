package trapx00.lightx00.server.data.admindata;

import trapx00.lightx00.server.data.admindata.datacontroller.*;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.dataservice.admindataservice.UserManagementDataService;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.queryvo.SpecificUserAccountQueryVo;
import trapx00.lightx00.shared.queryvo.UserAccountQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings("unchecked")
public class UserManagementDataController extends UnicastRemoteObject implements UserManagementDataService, LoginService {

    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     *
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public UserManagementDataController() throws RemoteException {
        positionDaoMap.put(EmployeePosition.Admin, new AdminStaffDataController());
        positionDaoMap.put(EmployeePosition.FinanceStaff, new FinanceStaffDataController());
        positionDaoMap.put(EmployeePosition.SaleStaff, new SaleStaffDataController());
        positionDaoMap.put(EmployeePosition.InventoryStaff, new InventoryStaffDataController());
        positionDaoMap.put(EmployeePosition.Manager, new ManagerDataController());
    }

    private HashMap<EmployeePosition, SpecificEmployeeDataController> positionDaoMap = new HashMap<>();
    private ServerLogService logService = ServerLogServiceFactory.getService();

    private void handleSqlException(SQLException e) {
        logService.printLog(this,"遇到了数据库操作失败。");
        throw new DbSqlException(e);
    }

    /**
     * filter some user accounts.
     * @param query the filter conditions
     * @return array of user accounts which match the conditions
     */
    @Override
    public EmployeePo[] query(UserAccountQueryVo query) {
        List<EmployeePo> list = new ArrayList<>();
        for (Map.Entry<EmployeePosition, SpecificEmployeeDataController> entry : positionDaoMap.entrySet()) {
            if (query.getQueryVoForPosition(entry.getKey()) != null) {
                list.addAll(entry.getValue().query(query.getQueryVoForPosition(entry.getKey())));
            }
        }
        return list.toArray(new EmployeePo[list.size()]);
    }

    /**
     * create a user account for a new employee.
     * @param account the account to be created
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(EmployeePo account) {
        boolean existAnywhere = positionDaoMap.values().stream()
            .anyMatch(x -> x.idExists(account.getId()));
        if (existAnywhere) {
            throw new IdExistsException(account.getId());
        }
        return positionDaoMap.get(account.getPosition()).add(account);
    }

    /**
     * modify a user account.
     * @param account the account to be modified
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(EmployeePo account) {
        return positionDaoMap.get(account.getPosition()).modify(account);
    }

    /**
     * delete a user account.
     * @param account the account to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(EmployeePo account) {
        return positionDaoMap.get(account.getPosition()).delete(account.getId());
    }

    /**
     * New id for a new employee.
     * @return new id
     */
    @Override
    public String getId(){
        List<EmployeePo> employeePos = null;
        for (Map.Entry<EmployeePosition, SpecificEmployeeDataController> controller : positionDaoMap.entrySet()) {
            if(employeePos == null)
                employeePos = (controller.getValue().query(new SpecificUserAccountQueryVo()));
            else
                employeePos.addAll(controller.getValue().query(new SpecificUserAccountQueryVo()));
        }
            OptionalInt maxId = employeePos.stream().map(EmployeePo::getId)
                                .mapToInt(Integer::parseInt).max();
            return String.valueOf(maxId.getAsInt() + 1);
    }
    /**
     * Login.
     *
     * @param name name
     * @param password password
     * @return EmployeeId if login is successful. Null otherwise
     */
    @Override
    public String login(String name, String password) {
        for (Map.Entry<EmployeePosition, SpecificEmployeeDataController> controller : positionDaoMap.entrySet()) {
            List<EmployeePo> employeePo = controller.getValue().query((SpecificUserAccountQueryVo) new SpecificUserAccountQueryVo()
                .eq("name", name).and().eq("password", password));
            if (employeePo.size() != 0) {
                return employeePo.get(0).getId();
            }
        }
        return null;
    }

}
