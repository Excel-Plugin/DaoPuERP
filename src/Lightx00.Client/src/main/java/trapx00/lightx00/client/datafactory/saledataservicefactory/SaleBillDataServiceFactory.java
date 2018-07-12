package trapx00.lightx00.client.datafactory.saledataservicefactory;

import trapx00.lightx00.shared.dataservice.saledataservice.SaleBillDataService;
import trapx00.lightx00.shared.dataservicestub.saledataservice.SaleBillDataServiceStub;
import trapx00.lightx00.shared.util.RmiHelper;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SaleBillDataServiceFactory {

    private static SaleBillDataService service=new SaleBillDataServiceStub();

    public static void initRmi(){
        try {
            service =(SaleBillDataService)Naming.lookup(RmiHelper.generateRmiUrl(SaleBillDataService.class));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static SaleBillDataService getInstance() {
        initRmi();
        return service;
    }
}
