package trapx00.lightx00.client.bl.logbl;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.log.LogSeverity;

public interface LogService {
    /**
     * Writes log.
     *
     * @param severity Log Severity
     * @param content  Log content
     * @return whether the operation is done successfully
     */
    ResultMessage log(LogSeverity severity, String content);
}
