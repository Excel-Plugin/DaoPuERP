package trapx00.lightx00.client.test.bl.clientbl;

import org.junit.Test;
import trapx00.lightx00.client.bl.clientbl.ClientBlController;
import trapx00.lightx00.client.bl.clientbl.factory.ClientBlFactory;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.po.ClientModificationFlag;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.client.ClientType;

import static org.junit.Assert.assertEquals;

public class ClientBlControllerTest {
    ClientBlController clientBlController= ClientBlFactory.getClientBlController();
    ClientVo clientVo=new ClientVo("0", ClientType.Retailer, 5, "xiaoming", "123456789", "123456789", "215000", "12345679@qq.com", 50,43.7, 37.5, "1");

    @Test
    public void deleteDraft() throws Exception {
        clientBlController.add(clientVo);
        assertEquals(ResultMessage.Success,clientBlController.delete(new String[]{"0"}));
    }

    @Test
    public void query() throws Exception {
        clientBlController.add(clientVo);
        assertEquals("0",clientBlController.query("")[0].getId());
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success,clientBlController.saveAsDraft(clientVo));
        clientBlController.deleteDraft("0");
    }

    @Test
    public void getId() throws Exception {
        assertEquals("0",clientBlController.getId());
    }

    @Test
    public void modify() throws Exception {
        clientBlController.add(clientVo);
        clientVo.setAddress("123");
        clientBlController.modify(clientVo);
        assertEquals("123",clientBlController.query("0")[0].getAddress());
        clientBlController.delete(new String[]{"0"});
    }

    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success,clientBlController.add(clientVo));
        clientBlController.delete(new String[]{"0"});
    }

    @Test
    public void delete() throws Exception {
        clientBlController.add(clientVo);
        assertEquals(ResultMessage.Success,clientBlController.delete(new String[]{"0","1"}));
    }

    @Test
    public void modifyClient() throws Exception {
        assertEquals(ResultMessage.Success,clientBlController.modifyClient("0", ClientModificationFlag.PAYABLE,100));
    }

}