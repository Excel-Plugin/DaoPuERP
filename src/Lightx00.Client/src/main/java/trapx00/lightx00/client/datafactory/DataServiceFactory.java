package trapx00.lightx00.client.datafactory;

import trapx00.lightx00.shared.util.RmiHelper;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

@SuppressWarnings("unchecked")
public abstract class DataServiceFactory {

    public static <T extends Remote> T lookupService(Class<T> clazz) {
        try {
            return (T) Naming.lookup(RmiHelper.generateRmiUrl(clazz));
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
