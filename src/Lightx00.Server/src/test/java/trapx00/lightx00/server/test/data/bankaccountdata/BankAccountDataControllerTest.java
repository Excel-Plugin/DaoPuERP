package trapx00.lightx00.server.test.data.bankaccountdata;

import com.j256.ormlite.dao.Dao;
import org.junit.Test;
import trapx00.lightx00.server.data.bankaccountdata.factory.BankAccountDataDaoFactory;
import trapx00.lightx00.server.data.bankaccountdata.factory.BankAccountDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.bankaccountdataservice.BankAccountDataService;
import trapx00.lightx00.shared.po.financestaff.BankAccountPo;
import trapx00.lightx00.shared.queryvo.BankAccountQueryVo;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class BankAccountDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Dao<BankAccountPo, Integer> dao = BankAccountDataDaoFactory.getBankAccountDao();
    private BankAccountDataService service = BankAccountDataFactory.getService();
    private BankAccountPo dumbAccount = new BankAccountPo("123",10,new Date());
    @Test
    public void query() throws Exception {
        int id1, id2;
        BankAccountPo bankAccountPo2 = new BankAccountPo("1234",100,new Date());
        dao.create(dumbAccount);
        dao.create(bankAccountPo2);
        id1 = dao.extractId(dumbAccount);
        id2 = dao.extractId(bankAccountPo2);
        try {
            assertEquals(2, service.query(new BankAccountQueryVo()).length);
            assertEquals(1, service.query(new BankAccountQueryVo().eq("amount",10.0)).length);
            assertEquals(0, service.query(new BankAccountQueryVo().eq("amount",1.0)).length);
        } finally {
          dao.deleteById(id1);
          dao.deleteById(id2);
        }
    }

    @Test
    public void add() throws Exception {
        long previous = dao.countOf();
        service.add(dumbAccount);
        int id = dao.extractId(dumbAccount);
        try {
            assertEquals(previous + 1, dao.countOf());
        } finally {
            dao.deleteById(id);
        }

    }

    @Test
    public void modify() throws Exception {
        dao.create(dumbAccount);
        int id = dao.extractId(dumbAccount);
        try {
            BankAccountPo accountPo = dao.queryForId(id);
            accountPo.setAmount(1000);
            service.modify(accountPo);
            accountPo = dao.queryForId(id);
            assertEquals(1000, accountPo.getAmount(),0.01);
        } finally {
            dao.deleteById(id);
        }
    }

    @Test
    public void delete() throws Exception {
        dao.create(dumbAccount);
        int id = dao.extractId(dumbAccount);
        try {
            service.delete(id);
            assertNull(dao.queryForId(id));
        } finally {
            dao.deleteById(id);
        }

    }

}