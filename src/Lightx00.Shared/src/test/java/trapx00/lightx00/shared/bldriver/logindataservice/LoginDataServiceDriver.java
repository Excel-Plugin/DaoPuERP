package trapx00.lightx00.shared.bldriver.logindataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.logindataservice.LoginDataService;
import trapx00.lightx00.shared.dataservicestub.logindataservice.LoginDataServiceStub;

import static org.junit.Assert.assertEquals;

public class LoginDataServiceDriver {
    private LoginDataService service = new LoginDataServiceStub();
    @Test
    public void login() throws Exception {
        assertEquals("1", service.login("123","123"));
    }

}