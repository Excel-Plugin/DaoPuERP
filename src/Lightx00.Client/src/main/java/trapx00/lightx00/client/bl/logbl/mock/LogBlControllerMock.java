package trapx00.lightx00.client.bl.logbl.mock;

import trapx00.lightx00.client.bl.logbl.LogBlController;
import trapx00.lightx00.client.vo.log.LogVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.LogQueryVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Date;

public class LogBlControllerMock extends LogBlController {
    /**
     * Writes log.
     *
     * @param severity Log Severity
     * @param content  Log content
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage log(LogSeverity severity, String content) {
        System.out.println(String.format("%s [%s] %s",DateHelper.currentDateString(), severity.toString(), content));
        return ResultMessage.Success;
    }

    /**
     * Queries Log with LogQueryVo.
     *
     * @param query query
     * @return LogVos that match query
     */
    @Override
    public LogVo[] query(LogQueryVo query) {
        return new LogVo[]{new LogVo(new Date(), LogSeverity.Success, "123")};
    }
}
