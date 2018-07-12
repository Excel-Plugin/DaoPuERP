package trapx00.lightx00.client.test.presentationdriver.clientblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlService;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlServiceFactory;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.po.ResultMessage;

import static org.junit.Assert.assertEquals;

public class ClientBlServiceDriver {

    ClientBlService service = ClientBlServiceFactory.getInstance();
    ClientVo clientVo;
    ClientVo[] clientVos;

    @Test
    public void query() throws Exception {
        assertEquals("12345678", service.query("xiaoming")[0].getPhone());
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success, service.saveAsDraft(clientVo));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success, service.modify(clientVo));
    }

    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success, service.add(clientVo));
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success, service.delete(null));
    }

    @Test
    public void getId() throws Exception {
        assertEquals("0", service.getId());
    }

}