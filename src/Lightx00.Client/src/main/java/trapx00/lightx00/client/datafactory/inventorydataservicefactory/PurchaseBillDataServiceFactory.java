package trapx00.lightx00.client.datafactory.inventorydataservicefactory;

import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseBillDataService;
import trapx00.lightx00.shared.dataservicestub.inventorydataservice.PurchaseBillDataServiceStub;
import trapx00.lightx00.shared.util.RmiHelper;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class PurchaseBillDataServiceFactory {

    private static PurchaseBillDataService service=new PurchaseBillDataServiceStub();

    public static void initRmi(){
        try {
            service =(PurchaseBillDataService)Naming.lookup(RmiHelper.generateRmiUrl(PurchaseBillDataService.class));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static PurchaseBillDataService getInstance() {
        initRmi();
        return service;
    }
}
