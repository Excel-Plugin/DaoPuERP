package trapx00.lightx00.server.test.data.clientdata;

import trapx00.lightx00.server.data.clientdata.factory.ClientDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.clientdataservice.ClientDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.client.ClientPo;
import trapx00.lightx00.shared.po.client.ClientState;
import trapx00.lightx00.shared.po.client.ClientType;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ClientDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String[] ids = new String[1];
    ClientPo clientPo = new ClientPo("0", ClientType.Retailer, 5, "xiaoming", "123456789", "123456789", "215000", "12345679@qq.com", 43.7, 37.5, "0", ClientState.Real);
    ClientDataService service = ClientDataFactory.getService();

    @org.junit.Test
    public void query() throws Exception {
        ids[0] = "0";
        service.add(clientPo);
        assertEquals("0", service.query("xiaoming")[0].getId());
        service.delete(ids);
    }

    @org.junit.Test
    public void queryById() throws Exception {
        ids[0] = "0";
        service.add(clientPo);
        assertEquals("xiaoming", service.queryById("0").getName());
        service.delete(ids);
    }

    @org.junit.Test
    public void modify() throws Exception {
        ids[0] = "0";
        service.add(clientPo);
        clientPo.setClientLevel(4);
        service.modify(clientPo);
        assertEquals(4, service.query("xiaoming")[0].getClientLevel());
        service.delete(ids);
    }

    @org.junit.Test
    public void getId() throws Exception {
        ids[0] = "0";
        service.add(clientPo);
        assertEquals("1", service.getId());
        service.delete(ids);
    }

    @org.junit.Test
    public void add() throws Exception {
        ids[0] = "0";
        assertEquals(ResultMessage.Success, service.add(clientPo));
        service.delete(ids);
    }

    @org.junit.Test
    public void delete() throws Exception {
        ids[0] = "0";
        service.add(clientPo);
        assertEquals(ResultMessage.Success, service.delete(ids));
    }

}
