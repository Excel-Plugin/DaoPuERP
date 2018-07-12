package trapx00.lightx00.server.data.promotiondata.factory;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.po.manager.promotion.ClientPromotionPo;
import trapx00.lightx00.shared.po.manager.promotion.ComSalePromotionPo;
import trapx00.lightx00.shared.po.manager.promotion.TotalPricePromotionPo;

public class PromotionDataDaoFactory extends BaseDatabaseFactory {
    private static Dao<ClientPromotionPo,String> clientPromotionDao;
    private static Dao<ComSalePromotionPo,String> comSalePromotionDao;
    private static Dao<TotalPricePromotionPo,String> totalPricePromotionDao;

    /**
     * Create a clientPromotionDao.
     * @return clientPromotionDao
     */
    public static Dao<ClientPromotionPo,String> getClientPromotionDao() {
        if (clientPromotionDao == null) {
            clientPromotionDao = createDao(ClientPromotionPo.class);
        }
        return clientPromotionDao;
    }

    /**
     * Create a comSalePromotionDao.
     * @return comSalePromotionDao
     */
    public static Dao<ComSalePromotionPo,String> getComSalePromotionDao() {
        if (comSalePromotionDao == null) {
            comSalePromotionDao = createDao(ComSalePromotionPo.class);
        }
        return comSalePromotionDao;
    }

    /**
     * Create a totalPricePromotionDao.
     * @return totalPricePromotionDao
     */
    public static Dao<TotalPricePromotionPo,String> getTotalPricePromotionDao() {
        if (totalPricePromotionDao == null) {
            totalPricePromotionDao = createDao(TotalPricePromotionPo.class);
        }
        return totalPricePromotionDao;
    }
}
