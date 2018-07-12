package trapx00.lightx00.server.data.approvaldata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.approvaldata.factory.AuditDataDaoFactory;
import trapx00.lightx00.server.data.util.serverlogservice.ServerLogService;
import trapx00.lightx00.server.data.util.serverlogservice.factory.ServerLogServiceFactory;
import trapx00.lightx00.shared.dataservice.approvaldataservice.AuditDataService;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.IdNotExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.AuditIdPo;
import trapx00.lightx00.shared.queryvo.AuditIdQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AuditDataController extends UnicastRemoteObject implements AuditDataService {
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
    public AuditDataController() throws RemoteException {
    }

    private Dao<AuditIdPo, String> dao = AuditDataDaoFactory.getAuditDao();
    private ServerLogService logService = ServerLogServiceFactory.getService();
    private Object delegate = this;

    /**
     * Filter some AuditIdPo.
     * @param query the filter conditions
     * @return array of AuditIdPo which match the conditions
     */
    public AuditIdPo[] query(AuditIdQueryVo query) {
        try {
            List<AuditIdPo> results = dao.query(query.prepareQuery(dao));
            logService.printLog(delegate,String.format("查询待审批单据得到%d条结果", results.size()));
            return results.toArray(new AuditIdPo[results.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    /**
     * Delete AuditIdPo after approving the bill.
     * @param billInfo the corresponding AuditIdPo to the BillPo
     * @return whether the operation is done successfully
     */
    public ResultMessage pass(AuditIdPo billInfo) {
        assertExists(billInfo.getId(), true);
        try {
            logService.printLog(delegate, String.format("通过审批(id: %s)",billInfo.getId()));
            dao.deleteById(billInfo.getId());
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    /**
     * Delete AuditIdPo after rejecting the approval.
     * @param billInfo the corresponding AuditIdPo to the BillPo
     * @return whether the operation is done successfully
     */
    public ResultMessage reject(AuditIdPo billInfo) {
        assertExists(billInfo.getId(), true);
        try {
            dao.deleteById(billInfo.getId());
            logService.printLog(delegate, String.format("拒绝通过审批(id: %s)",billInfo.getId()));
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    /**
     * Save the submitted BillPo as AuditIdPo.
     * @param id id of the bill has been submitted
     * @return whether the operation is done successfully
     */
    //在AuditIdPo表里增加一条记录
    @Override
    public ResultMessage requestApproval(String id) {
        assertExists(id, false);
        try {
            AuditIdPo billInfo = new AuditIdPo(id,new Date());
            dao.create(billInfo);
            logService.printLog(delegate, String.format("提交单据等待审批，单据编号是(id: %s)",id));
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    private void assertExists(String id, boolean expectedExists) {
        try {
            AuditIdPo billInfo = dao.queryForId(id);
            boolean actualExists = billInfo != null;
            if (actualExists && !expectedExists) {
                throw new IdExistsException(id);
            } else if (!actualExists && expectedExists) {
                throw new IdNotExistsException(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }
}
