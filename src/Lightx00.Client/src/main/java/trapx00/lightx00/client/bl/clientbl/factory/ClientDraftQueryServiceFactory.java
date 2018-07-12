package trapx00.lightx00.client.bl.clientbl.factory;


import trapx00.lightx00.client.bl.draftbl.DraftableQueryService;

public class ClientDraftQueryServiceFactory {
    private static DraftableQueryService queryService = ClientBlFactory.getClientBlController();

    public static DraftableQueryService getQueryService() {
        return queryService;
    }
}
