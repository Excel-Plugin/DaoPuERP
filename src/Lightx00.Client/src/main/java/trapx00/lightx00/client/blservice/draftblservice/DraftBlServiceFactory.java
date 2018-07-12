package trapx00.lightx00.client.blservice.draftblservice;

import trapx00.lightx00.client.bl.draftbl.factory.DraftServiceFactory;

public class DraftBlServiceFactory {

    public static DraftBlService getInstance() {
        return DraftServiceFactory.getController();
    }
}
