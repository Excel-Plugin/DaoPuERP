package trapx00.lightx00.server.data.promotiondata.coupondata.factory;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.po.manager.CouponPo;

public class CouponDataDaoFactory extends BaseDatabaseFactory {
    private static Dao<CouponPo, Integer> usedCouponDao;

    public static Dao<CouponPo, Integer> getUsedCouponDao () {
        if (usedCouponDao == null) {
            usedCouponDao = createDao(CouponPo.class);
        }
        return usedCouponDao;
    }
}
