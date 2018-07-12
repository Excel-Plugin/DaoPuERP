package trapx00.lightx00.shared.queryvo;

import trapx00.lightx00.shared.po.log.LogPo;

public class LogQueryVo extends BaseQueryVo<LogPo, Integer, LogQueryVo> {
    public LogQueryVo() {
    }

    public LogQueryVo(BaseQueryVo another) {
        super(another);
    }
}