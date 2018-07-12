package trapx00.lightx00.server.data.inventorydata.factory;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.po.salestaff.PurchaseRefundBillPo;

public class PurchaseRefundBillDataDaoFactory extends BaseDatabaseFactory {
    private static Dao<PurchaseRefundBillPo, String> purchaseRefundBillDao;


    public static Dao<PurchaseRefundBillPo, String> getPurchaseRefundBillDao() {
        if (purchaseRefundBillDao == null) {
            purchaseRefundBillDao = createDao(PurchaseRefundBillPo.class);
        }
        return purchaseRefundBillDao;
    }
}
