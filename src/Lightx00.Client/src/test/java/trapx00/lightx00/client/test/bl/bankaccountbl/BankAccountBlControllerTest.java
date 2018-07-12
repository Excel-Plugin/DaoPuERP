package trapx00.lightx00.client.test.bl.bankaccountbl;

import org.junit.Test;
import trapx00.lightx00.client.bl.bankaccountbl.BankAccountBlController;
import trapx00.lightx00.client.bl.bankaccountbl.factory.BankAccountFactory;
import trapx00.lightx00.client.vo.financestaff.BankAccountVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.BankAccountQueryVo;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BankAccountBlControllerTest {
    private BankAccountBlController controller = BankAccountFactory.getController();
    @Test
    public void modifyBankAccount() throws Exception {
        assertEquals(ResultMessage.Success, controller.modifyBankAccount(1,-10));
    }

    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success, controller.add(new BankAccountVo(1,"1",10, new Date())));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success, controller.modify(new BankAccountVo(1,"1",10, new Date())));
    }

    @Test
    public void query() throws Exception {
        assertEquals(0, controller.query(new BankAccountQueryVo().eq("id",1))[0].getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success, controller.delete(1));
    }

}