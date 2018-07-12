package trapx00.lightx00.client.blservicestub.logblservice;

import trapx00.lightx00.client.blservice.logblservice.LogBlService;
import trapx00.lightx00.client.vo.log.LogVo;
import trapx00.lightx00.shared.po.log.LogSeverity;
import trapx00.lightx00.shared.queryvo.LogQueryVo;

import java.util.Date;

public class LogBlServiceStub implements LogBlService {
    @Override
    public LogVo[] query(LogQueryVo query) {
        return new LogVo[] {
                new LogVo(new Date(), LogSeverity.Failure, "123")
        };
    }

}
