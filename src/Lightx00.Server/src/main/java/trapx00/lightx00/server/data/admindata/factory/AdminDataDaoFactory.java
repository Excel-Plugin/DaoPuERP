package trapx00.lightx00.server.data.admindata.factory;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.po.admin.AdminPo;
import trapx00.lightx00.shared.po.financestaff.FinanceStaffPo;
import trapx00.lightx00.shared.po.inventorystaff.InventoryStaffPo;
import trapx00.lightx00.shared.po.manager.ManagerPo;
import trapx00.lightx00.shared.po.salestaff.SaleStaffPo;

public class AdminDataDaoFactory extends BaseDatabaseFactory {
    private static Dao<AdminPo, String> adminDao;
    private static Dao<SaleStaffPo, String> saleStaffDao;
    private static Dao<InventoryStaffPo, String> inventoryStaffDao;
    private static Dao<ManagerPo, String> managerDao;
    private static Dao<FinanceStaffPo, String> financeStaffDao;

    public static Dao<AdminPo, String> getAdminDao() {
        if (adminDao == null) {
            adminDao = createDao(AdminPo.class);
        }
        return adminDao;
    }

    public static Dao<SaleStaffPo, String> getSaleStaffDao() {
        if (saleStaffDao == null) {
            saleStaffDao = createDao(SaleStaffPo.class);
        }
        return saleStaffDao;
    }

    public static Dao<InventoryStaffPo, String> getInventoryStaffDao() {
        if (inventoryStaffDao == null) {
            inventoryStaffDao = createDao(InventoryStaffPo.class);
        }
        return inventoryStaffDao;
    }

    public static Dao<ManagerPo, String> getManagerDao() {
        if (managerDao == null) {
            managerDao = createDao(ManagerPo.class);
        }
        return managerDao;
    }

    public static Dao<FinanceStaffPo, String> getFinanceStaffDao() {
        if (financeStaffDao == null) {
            financeStaffDao = createDao(FinanceStaffPo.class);
        }
        return financeStaffDao;
    }
}
