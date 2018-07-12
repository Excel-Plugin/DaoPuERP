package trapx00.lightx00.client.bl.financebl.factory;

import trapx00.lightx00.client.bl.draftbl.DraftableQueryService;

public class BillDraftQueryServiceFactory {
    private static DraftableQueryService queryService = TradeHistoryBlFactory.getController();

    public static DraftableQueryService getQueryService() {
        return queryService;
    }
}
