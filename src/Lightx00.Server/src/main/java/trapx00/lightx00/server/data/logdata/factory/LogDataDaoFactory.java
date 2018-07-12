package trapx00.lightx00.server.data.logdata.factory;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.po.log.LogPo;

public class LogDataDaoFactory extends BaseDatabaseFactory {

    private static Dao<LogPo, Integer> logDao;

    public static Dao<LogPo, Integer> getLogDao() {
        if (logDao == null) {
            logDao = createDao(LogPo.class);
        }
        return logDao;
    }
}
