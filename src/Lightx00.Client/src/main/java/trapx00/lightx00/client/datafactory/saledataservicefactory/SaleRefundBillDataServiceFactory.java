package trapx00.lightx00.client.datafactory.saledataservicefactory;

import trapx00.lightx00.shared.dataservice.saledataservice.SaleRefundBillDataService;
import trapx00.lightx00.shared.dataservicestub.saledataservice.SaleRefundBillDataServiceStub;
import trapx00.lightx00.shared.util.RmiHelper;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SaleRefundBillDataServiceFactory {

    private static SaleRefundBillDataService service=new SaleRefundBillDataServiceStub();

    public static void initRmi(){
        try {
            service =(SaleRefundBillDataService)Naming.lookup(RmiHelper.generateRmiUrl(SaleRefundBillDataService.class));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static SaleRefundBillDataService getInstance() {
        initRmi();
        return service;
    }
}
