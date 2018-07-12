package trapx00.lightx00.server.data.faceid.factory;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.po.employee.EmployeeFaceIdInfo;

public class FaceIdDaoFactory extends BaseDatabaseFactory {

    private static Dao<EmployeeFaceIdInfo, String> dao;

    public static Dao<EmployeeFaceIdInfo, String> getDao() {
        if (dao == null) {
            dao = createDao(EmployeeFaceIdInfo.class);
        }
        return dao;
    }
}
