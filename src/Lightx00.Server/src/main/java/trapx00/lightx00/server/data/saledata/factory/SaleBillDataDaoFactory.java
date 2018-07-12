package trapx00.lightx00.server.data.saledata.factory;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.po.salestaff.SaleBillPo;

public class SaleBillDataDaoFactory extends BaseDatabaseFactory {
    private static Dao<SaleBillPo, String> saleBillDao;

    public static Dao<SaleBillPo, String> getSaleBillDao() {
        if (saleBillDao == null) {
            saleBillDao = createDao(SaleBillPo.class);
        }
        return saleBillDao;
    }
}
