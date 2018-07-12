package trapx00.lightx00.shared.bldriver.clientdataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.clientdataservice.ClientDataService;
import trapx00.lightx00.shared.dataservicestub.clientdataservice.ClientDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.client.ClientPo;

import static org.junit.Assert.assertEquals;

public class ClientDataServiceDriver {

    ClientDataService service=new ClientDataServiceStub();
    ClientPo clientPo;

    @Test
    public void query() throws Exception {
        assertEquals("12345678",service.query("xiaoming")[0].getPhone());
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success,service.modify(clientPo));
    }

    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success,service.modify(clientPo));
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,service.modify(clientPo));
    }

    @Test
    public void getId() throws Exception {
        assertEquals("0",service.getId());
    }

}