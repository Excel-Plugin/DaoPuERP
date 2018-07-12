package trapx00.lightx00.server.data.inventorydata.factory;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.po.inventorystaff.InventoryDetailBillPo;
import trapx00.lightx00.shared.po.inventorystaff.InventoryGiftPo;

public class InventoryDataDaoFactory extends BaseDatabaseFactory {
    private static Dao<InventoryGiftPo, String> inventoryGiftDao;
    private static Dao<InventoryDetailBillPo, String> inventoryDetailBillDao;

    public static Dao<InventoryGiftPo, String> getInventoryGiftDao() {
        if (inventoryGiftDao == null) {
            inventoryGiftDao = createDao(InventoryGiftPo.class);
        }
        return inventoryGiftDao;
    }

    public static Dao<InventoryDetailBillPo, String> getInventoryDetailBillDao() {
        if (inventoryDetailBillDao == null) {
            inventoryDetailBillDao = createDao(InventoryDetailBillPo.class);
        }
        return inventoryDetailBillDao;
    }


}
