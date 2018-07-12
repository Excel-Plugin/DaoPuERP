package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.bl.financebl.factory.InitialEstablishmentBlFactory;

public class InitialEstablishmentServiceFactory {


    public static InitialEstablishmentBlService getInstance() {
        return InitialEstablishmentBlFactory.getController();
    }
}
