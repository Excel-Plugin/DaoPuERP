package trapx00.lightx00.client.bl.logbl;

import trapx00.lightx00.client.blservice.logblservice.LogBlService;
import trapx00.lightx00.client.datafactory.logdataservicefactory.LogDataServiceFactory;
import trapx00.lightx00.client.vo.log.LogVo;
import trapx00.lightx00.shared.dataservice.logdataservice.LogDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.log.LogPo;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.LogQueryVo;

import java.rmi.RemoteException;
import java.util.Arrays;

public class LogBlController implements LogBlService, LogService {
    private LogDataService logDataService = LogDataServiceFactory.getService();
    /**
     * Writes log.
     *
     * @param severity Log Severity
     * @param content  Log content
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage log(LogSeverity severity, String content) {
        try {
            logDataService.log(severity, content);
            return ResultMessage.Success;
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Queries Log with LogQueryVo.
     *
     * @param query query
     * @return LogVos that match query
     */
    @Override
    public LogVo[] query(LogQueryVo query) {
        try {
            return Arrays.stream(logDataService.query(query)).map(this::fromPo).toArray(LogVo[]::new);
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    private LogVo fromPo(LogPo logPo) {
        return new LogVo(logPo.getId(), logPo.getDate(), logPo.getSeverity(), logPo.getContent());
    }
}
