package trapx00.lightx00.client.blservice.loginblservice;

import trapx00.lightx00.client.bl.loginbl.factory.LoginBlFactory;

public class LoginBlServiceFactory {

    public static LoginBlService getInstance() {
        return LoginBlFactory.getController();
    }
}
