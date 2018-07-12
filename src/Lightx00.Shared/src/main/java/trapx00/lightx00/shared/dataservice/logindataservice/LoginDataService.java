package trapx00.lightx00.shared.dataservice.logindataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginDataService extends Remote {
    /**
     * Login.
     * @param username username
     * @param password password
     * @return Employee ID if login is successful
     */
    String login(String username, String password) throws RemoteException;
}
