package trapx00.lightx00.shared.dataservice.logdataservice;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.log.LogPo;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.LogQueryVo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LogDataService extends Remote {

    /**
     * Writes log.
     *
     * @param severity Log severity
     * @param content  content
     * @return whether the operation is done successfully
     */
    ResultMessage log(LogSeverity severity, String content) throws RemoteException;

    /**
     * Queries log.
     *
     * @param query query
     * @return LogVos that match query condition
     */
    LogPo[] query(LogQueryVo query) throws RemoteException;
}
