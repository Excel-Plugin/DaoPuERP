package trapx00.lightx00.server.data.financedata.factory;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.po.financestaff.CashBillPo;
import trapx00.lightx00.shared.po.financestaff.PaymentBillPo;
import trapx00.lightx00.shared.po.financestaff.ReceivalBillPo;
import trapx00.lightx00.shared.po.financestaff.SystemSnapshotPo;

public class FinanceDataDaoFactory extends BaseDatabaseFactory {

    private static Dao<CashBillPo, String> cashBillDao;
    private static Dao<PaymentBillPo, String> paymentBillDao;
    private static Dao<ReceivalBillPo,String> receivalBillDao;
    private static Dao<SystemSnapshotPo, String> systemSnapshotDao;


    public static Dao<CashBillPo, String> getCashBillDao() {
        if (cashBillDao == null) {
            cashBillDao = createDao(CashBillPo.class);
        }
        return cashBillDao;
    }

    public static Dao<PaymentBillPo, String> getPaymentBillDao() {
        if (paymentBillDao == null) {
            paymentBillDao = createDao(PaymentBillPo.class);
        }
        return paymentBillDao;
    }

    public static Dao<ReceivalBillPo, String> getReceivalBillDao() {
        if (receivalBillDao == null) {
            receivalBillDao = createDao(ReceivalBillPo.class);
        }
        return receivalBillDao;
    }

    public static Dao<SystemSnapshotPo, String> getSystemSnapshotDao() {
        if (systemSnapshotDao == null) {
            systemSnapshotDao = createDao(SystemSnapshotPo.class);
        }
        return systemSnapshotDao;
    }
}
