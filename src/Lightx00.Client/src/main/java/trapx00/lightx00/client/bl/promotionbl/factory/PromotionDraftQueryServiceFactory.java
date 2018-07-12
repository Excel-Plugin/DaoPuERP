package trapx00.lightx00.client.bl.promotionbl.factory;

import trapx00.lightx00.client.bl.draftbl.DraftableQueryService;

public class PromotionDraftQueryServiceFactory {
    private static DraftableQueryService queryService = PromotionInfoFactory.getController();

    public static DraftableQueryService getQueryService() {
        return queryService;
    }

}
