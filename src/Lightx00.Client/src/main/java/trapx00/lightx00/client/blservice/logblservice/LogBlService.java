package trapx00.lightx00.client.blservice.logblservice;

import trapx00.lightx00.client.vo.log.LogVo;
import trapx00.lightx00.shared.queryvo.LogQueryVo;

public interface LogBlService {
    /**
     * Queries Log with LogQueryVo.
     *
     * @param query query
     * @return LogVos that match query
     */
    LogVo[] query(LogQueryVo query);
}
