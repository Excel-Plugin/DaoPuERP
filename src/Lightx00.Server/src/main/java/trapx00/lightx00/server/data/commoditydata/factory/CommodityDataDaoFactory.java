package trapx00.lightx00.server.data.commoditydata.factory;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.po.inventorystaff.CommodityPo;
import trapx00.lightx00.shared.po.inventorystaff.CommoditySortPo;

public class CommodityDataDaoFactory extends BaseDatabaseFactory {
    private static Dao<CommodityPo, String> commodityDao;
    private static Dao<CommoditySortPo, String> commoditySortDao;

    public static Dao<CommodityPo, String> getCommodityDao() {
        if (commodityDao == null) {
            commodityDao = createDao(CommodityPo.class);
        }
        return commodityDao;
    }

    public static Dao<CommoditySortPo, String> getCommoditySortDao() {
        if (commoditySortDao == null) {
            commoditySortDao = createDao(CommoditySortPo.class);
        }
        return commoditySortDao;
    }

}
