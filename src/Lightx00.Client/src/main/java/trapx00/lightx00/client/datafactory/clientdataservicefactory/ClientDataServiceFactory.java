package trapx00.lightx00.client.datafactory.clientdataservicefactory;

import trapx00.lightx00.shared.dataservice.clientdataservice.ClientDataService;
import trapx00.lightx00.shared.dataservicestub.clientdataservice.ClientDataServiceStub;
import trapx00.lightx00.shared.util.RmiHelper;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientDataServiceFactory {

    private static ClientDataService service = new ClientDataServiceStub();

    public static void initRmi(){
        try {
            service =(ClientDataService)Naming.lookup(RmiHelper.generateRmiUrl(ClientDataService.class));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static ClientDataService getInstance() {
        initRmi();
        return service;
    }
}
