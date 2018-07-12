package trapx00.lightx00.server.data.logdata.mock;

import trapx00.lightx00.server.data.logdata.LogDataController;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.log.LogPo;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.LogQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.util.Date;

public class LogDataControllerMock extends LogDataController {
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
    public LogDataControllerMock() throws RemoteException {
    }

    /**
     * Writes log.
     *
     * @param severity Log severity
     * @param content  content
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage log(LogSeverity severity, String content)  {
        return ResultMessage.Success;
    }

    /**
     * Queries log.
     *
     * @param query query
     * @return LogVos that match query condition
     */
    @Override
    public LogPo[] query(LogQueryVo query) {
        return new LogPo[] {
                new LogPo(new Date(), LogSeverity.Success,"123")
        };
    }
}
