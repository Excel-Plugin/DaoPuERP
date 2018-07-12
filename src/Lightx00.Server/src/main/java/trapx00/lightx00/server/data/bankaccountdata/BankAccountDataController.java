package trapx00.lightx00.server.data.bankaccountdata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.bankaccountdata.factory.BankAccountDataDaoFactory;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.dataservice.bankaccountdataservice.BankAccountDataService;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.financestaff.BankAccountPo;
import trapx00.lightx00.shared.queryvo.BankAccountQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class BankAccountDataController extends UnicastRemoteObject implements BankAccountDataService {

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
    public BankAccountDataController() throws RemoteException {
    }

    private Dao<BankAccountPo, Integer> dao = BankAccountDataDaoFactory.getBankAccountDao();
    private ServerLogService serverLogService = ServerLogServiceFactory.getService();
    /**
     * Queries bank account.
     *
     * @param query query
     * @return Bank accounts that match query
     */
    @Override
    public BankAccountPo[] query(BankAccountQueryVo query) {
        try {
            List<BankAccountPo> results = dao.query(query.prepareQuery(dao));
            serverLogService.printLog(this,String.format("queried bankaccounts and got %d results", results.size()));
            return results.toArray(new BankAccountPo[results.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    private BankAccountPo assertExists(int id, boolean expectedExists) {
        try {
            BankAccountPo bankAccountPo = dao.queryForId(id);
            boolean actualExists = bankAccountPo != null;
            if (actualExists && !expectedExists) {
                throw new IdExistsException(String.valueOf(id));
            } else if (!actualExists && expectedExists) {
                throw new IdNotExistsException(String.valueOf(id));
            }
            return bankAccountPo;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    /**
     * Adds a account.
     *
     * @param account new account
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(BankAccountPo account) {
        assertExists(account.getId(), false);
        try {
            dao.create(account);
            serverLogService.printLog(this,"created a BankAccountPo " + account.toString());
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    /**
     * Modifies an account with the id.
     *
     * @param account new account info
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage modify(BankAccountPo account) {
        try {
            BankAccountPo existence = assertExists(account.getId(), true);
            account.setId(existence.getId());
            serverLogService.printLog(this, String.format("modified BankAccountPo (id: %d)",account.getId()));
            dao.update(account);
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    /**
     * Deletes an account.
     *
     * @param id id for the account to be deleted
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage delete(int id) {
        assertExists(id, true);
        try {
            dao.deleteById(id);
            serverLogService.printLog(this, String.format("deleted BankAccountPo (id: %d)",id));
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }
}
