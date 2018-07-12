package trapx00.lightx00.server.data.admindata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.employee.EmployeePo;
import trapx00.lightx00.shared.queryvo.SpecificUserAccountQueryVo;

import java.sql.SQLException;
import java.util.List;

public abstract class SpecificEmployeeDataController<T extends EmployeePo> {
    private Dao<T, String> dao;
    private ServerLogService logService = ServerLogServiceFactory.getService();

    private void assertIdExistence(String id, boolean exist) {
        try {
            boolean actual = dao.idExists(id);
            if (!actual && exist) {
                throw new IdNotExistsException(id);
            } else if (actual && !exist) {
                throw new IdExistsException(id);
            }
        } catch (SQLException e) {
            handleSqlException(e);
        }
    }

    public SpecificEmployeeDataController(Dao<T, String> dao) {
        this.dao = dao;
    }

    public ResultMessage add(T account) {
        try {
            assertIdExistence(account.getId(), false);
            dao.create(account);
            return ResultMessage.Success;
        } catch (SQLException e) {
            handleSqlException(e);
            return ResultMessage.Failure;
        }
    }

    public ResultMessage modify(T account) {
        try {
            assertIdExistence(account.getId(), true);
            dao.update(account);
            return ResultMessage.Success;
        } catch (SQLException e) {
            handleSqlException(e);
            return ResultMessage.Failure;
        }
    }

    public ResultMessage delete(String id) {
        try {
            assertIdExistence(id, true);
            dao.deleteById(id);
            return ResultMessage.Success;
        } catch (SQLException e) {
            handleSqlException(e);
            return ResultMessage.Failure;
        }
    }

    public List<T> query(SpecificUserAccountQueryVo<T> queryVo) {
        try {
            return dao.query(queryVo.prepareQuery(dao));
        } catch (SQLException e) {
            handleSqlException(e);
            return null;
        }
    }

    private void handleSqlException(SQLException e) {
        logService.printLog(this,"遇到了数据库操作失败。");
        throw new DbSqlException(e);
    }

    boolean idExists(String id) {
        try {
            return dao.idExists(id);
        } catch (SQLException e) {
            handleSqlException(e);
            return false;
        }
    }

}
