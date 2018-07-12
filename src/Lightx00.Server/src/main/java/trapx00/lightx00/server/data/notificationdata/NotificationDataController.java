package trapx00.lightx00.server.data.notificationdata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.notificationdata.factory.NotificationDataDaoFactory;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.dataservice.notificationdataservice.NotificationDataService;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.notification.NotificationPo;
import trapx00.lightx00.shared.queryvo.NotificationQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class NotificationDataController extends UnicastRemoteObject implements NotificationDataService {
    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     * <p>
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public NotificationDataController() throws RemoteException {
    }

    private Dao<NotificationPo, Integer> dao = NotificationDataDaoFactory.getDao();
    private ServerLogService logService = ServerLogServiceFactory.getService();

    private void handleSQLException(SQLException e) {
        logService.printLog(this,"a problem concerning database occurred.");
        throw new DbSqlException(e);
    }

    /**
     * Updates current user's notification.
     *
     * @param query query
     * @return current user's notification
     */
    @Override
    public NotificationPo[] query(NotificationQueryVo query) {
        try {
            List<NotificationPo> results = dao.query(query.prepareQuery(dao));
            logService.printLog(this, String.format("queried notifications and got %d results", results.size()));
            return results.toArray(new NotificationPo[results.size()]);
        } catch (SQLException e) {
            handleSQLException(e);
            return new NotificationPo[0];
        }
    }

    /**
     * Acknowledges a notification. It would delete a notification.
     *
     * @param id id for the notification to be acknowledged
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage acknowledge(int id) {
        try {
            if (!dao.idExists(id)) {
                logService.printLog(this,String.format("tried to acknowledge notification (id: %d) but it doesn't exist", id));
                throw new IdNotExistsException(String.valueOf(id));
            }
            dao.deleteById(id);
            logService.printLog(this, String.format("acknowledged and deleted a notification (id: %d)", id));
            return ResultMessage.Success;
        } catch (SQLException e) {
            handleSQLException(e);
            return ResultMessage.Failure;
        }
    }

    /**
     * Adds a notification.
     *
     * @param notificationPo NotificationPo
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(NotificationPo notificationPo) {
        try {
            dao.create(notificationPo);
            logService.printLog(this,"added a notification. content: " + notificationPo.toString());
            return ResultMessage.Success;
        } catch (SQLException e) {
            handleSQLException(e);
            return ResultMessage.Failure;
        }
    }
}
