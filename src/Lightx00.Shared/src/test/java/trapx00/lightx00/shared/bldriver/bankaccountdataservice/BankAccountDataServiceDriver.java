package trapx00.lightx00.shared.bldriver.bankaccountdataservice;

import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.shared.dataservice.bankaccountdataservice.BankAccountDataService;
import trapx00.lightx00.shared.dataservicestub.bankaccountdataservice.BankAccountDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.financestaff.BankAccountPo;
import trapx00.lightx00.shared.queryvo.BankAccountQueryVo;

import static org.junit.Assert.assertEquals;

public class BankAccountDataServiceDriver {
    private BankAccountDataService service = new BankAccountDataServiceStub();
    private BankAccountPo account;

    @Before
    public void setUp() throws Exception {
        account = service.query(new BankAccountQueryVo())[0];
    }

    @Test
    public void query() throws Exception {
        assertEquals(0, account.getId());
    }

    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success, service.add(account));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success, service.add(account));
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success, service.add(account));
    }

}