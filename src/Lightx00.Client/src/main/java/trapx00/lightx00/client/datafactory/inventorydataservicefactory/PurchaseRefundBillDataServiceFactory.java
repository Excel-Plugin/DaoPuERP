package trapx00.lightx00.client.datafactory.inventorydataservicefactory;

import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseRefundBillDataService;
import trapx00.lightx00.shared.dataservicestub.inventorydataservice.PurchaseRefundBillDataServiceStub;
import trapx00.lightx00.shared.util.RmiHelper;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class PurchaseRefundBillDataServiceFactory {

    private static PurchaseRefundBillDataService service=new PurchaseRefundBillDataServiceStub();

    public static void initRmi(){
        try {
            service =(PurchaseRefundBillDataService)Naming.lookup(RmiHelper.generateRmiUrl(PurchaseRefundBillDataService.class));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static PurchaseRefundBillDataService getInstance() {
        initRmi();
        return service;
    }
}
