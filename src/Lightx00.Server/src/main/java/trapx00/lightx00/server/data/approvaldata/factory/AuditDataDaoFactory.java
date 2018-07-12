package trapx00.lightx00.server.data.approvaldata.factory;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.po.manager.AuditIdPo;

public class AuditDataDaoFactory extends BaseDatabaseFactory {

    private static Dao<AuditIdPo,String> auditDao;

    /**
     * Create a auditDao.
     * @return auditDao
     */
    public static Dao<AuditIdPo,String> getAuditDao() {
        if (auditDao == null) {
            auditDao = createDao(AuditIdPo.class);
        }
        return auditDao;
    }

}
