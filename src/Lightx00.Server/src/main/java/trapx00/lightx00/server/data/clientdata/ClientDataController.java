package trapx00.lightx00.server.data.clientdata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.clientdata.factory.ClientDataDaoFactory;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.dataservice.clientdataservice.ClientDataService;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.client.ClientPo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class ClientDataController extends UnicastRemoteObject implements ClientDataService {
    private Dao<ClientPo, String> clientDao = ClientDataDaoFactory.getClientDao();
    private Object delegate = this;
    private ServerLogService logService = ServerLogServiceFactory.getService();

    /**
     * @throws RemoteException
     */
    public ClientDataController() throws RemoteException {
        super();
    }

    private ClientPo assertIdExistence(String id, boolean assertExists) {
        try {
            ClientPo clientPo = clientDao.queryForId(id);
            boolean actualExistence = clientPo != null;
            if (actualExistence && !assertExists) {
                throw new IdExistsException(id);
            }
            if (!actualExistence && assertExists) {
                throw new IdNotExistsException(id);
            }
            return clientPo;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    /**
     * Query clients which includes the words
     *
     * @param query condition
     * @return the clients which corresponds to the query
     */
    @Override
    public ClientPo[] query(String query) {
        ArrayList<ClientPo> result = new ArrayList<ClientPo>();
        try {
            List<ClientPo> clientPos = clientDao.queryBuilder().query();
            for (ClientPo clientPo : clientPos) {
                if (clientPo.getId().contains(query)
                        || clientPo.getAddress().contains(query)
                        || (clientPo.getClientLevel() + "").contains(query)
                        || clientPo.getClientType().toString().contains(query)
                        || clientPo.getPhone().contains(query)
                        || clientPo.getName().contains(query)) {
                    result.add(clientPo);
                }
            }
            logService.printLog(delegate, "query a client who contains " + query);
        } catch (SQLException e) {
            result.add(null);
            e.printStackTrace();
            handleSQLException(e);
        }
        ClientPo[] arrayResult = new ClientPo[result.size()];
        for (int i = 0; i < result.size(); i++) {
            arrayResult[i] = result.get(i);
        }
        return arrayResult;
    }

    /**
     * Query clients who has the id
     *
     * @param id id
     * @return the clients with its id
     */
    @Override
    public ClientPo queryById(String id) throws RemoteException {
        ClientPo result;
        try {
            result = clientDao.queryForId(id);
            logService.printLog(delegate, "query a client whose id is " + id);
        } catch (SQLException e) {
            result = null;
            e.printStackTrace();
            handleSQLException(e);
        }
        return result;
    }

    /**
     * modify a client
     *
     * @param client to be modified
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(ClientPo client) {
        try {
            clientDao.update(client);
            logService.printLog(delegate, "modify a client " + client);
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            handleSQLException(e);
            return ResultMessage.Failure;
        }
    }

    /**
     * Gets the id for the next client
     *
     * @return id for the next client
     */
    @Override
    public String getId() {
        try {
            String newId;
            OptionalInt maxId = clientDao.queryBuilder().selectColumns("id").query().stream()
                    .map(ClientPo::getId)
                    .mapToInt(Integer::parseInt)
                    .max();
            if (maxId.equals(OptionalInt.empty())) {
                newId = "0";
            } else {
                newId = (maxId.orElse(0) + 1) + "";
            }
            logService.printLog(delegate, "got a new id " + newId);
            return newId;
        } catch (SQLException e) {
            e.printStackTrace();
            handleSQLException(e);
            return "";
        }
    }

    /**
     * addQueryVoForOneEmployeePosition a client
     *
     * @param client to be added
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(ClientPo client) {
        try {
            clientDao.createIfNotExists(client);
            logService.printLog(delegate, "addQueryVoForOneEmployeePosition a new client " + client);
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            handleSQLException(e);
            return ResultMessage.Failure;
        }
    }

    /**
     * delete some clients
     *
     * @param ids of the client to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(String[] ids) {
        try {
            for (String id : ids) {
                ClientPo clientPo = assertIdExistence(id, true);
                clientDao.delete(clientPo);
                logService.printLog(delegate, "delete a client whose id is " + id);
            }
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            handleSQLException(e);
            return ResultMessage.Failure;
        }
    }

    private void handleSQLException(SQLException e) {
        logService.printLog(delegate, "failed at a database operation. Error message: " + e.getMessage());
        throw new DbSqlException(e);
    }
}