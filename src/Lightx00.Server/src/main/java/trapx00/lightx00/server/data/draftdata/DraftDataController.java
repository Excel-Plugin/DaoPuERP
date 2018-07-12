package trapx00.lightx00.server.data.draftdata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.draftdata.factory.DraftDataDaoFactory;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.dataservice.draftdataservice.DraftDataService;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.draft.DraftPo;
import trapx00.lightx00.shared.queryvo.DraftQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class DraftDataController extends UnicastRemoteObject implements DraftDataService {


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
    public DraftDataController() throws RemoteException {
    }

    private Dao<DraftPo, Integer> dao = DraftDataDaoFactory.getDao();
    private ServerLogService logService = ServerLogServiceFactory.getService();

    private void assertExistence(int id, boolean existence) {
        try {
            boolean actual = dao.idExists(id);
            if (actual && !existence) {
                throw new IdExistsException(String.valueOf(id));
            }
            if (!actual && existence) {
                throw new IdNotExistsException(String.valueOf(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    /**
     * Updates current user's drafts.
     *
     * @param query query
     * @return current user's drafts
     */
    @Override
    public DraftPo[] query(DraftQueryVo query) throws RemoteException {
        try {
            List<DraftPo> results = dao.query(query.prepareQuery(dao));
            logService.printLog(this, String.format("queried drafts and got %d results", results.size()));
            return results.toArray(new DraftPo[results.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    /**
     * Deletes a draft.
     *
     * @param draftId id for the draft to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(int draftId) throws RemoteException {
        try {
            assertExistence(draftId,true);
            dao.deleteById(draftId);
            logService.printLog(this,String.format("deleted a draft (id: %s)",draftId));
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }


    /**
     * Adds a draft.
     *
     * @param draft draft to be added
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(DraftPo draft) {
        try {
            dao.create(draft);
            logService.printLog(this, "created a draft " + draft);
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }
}
