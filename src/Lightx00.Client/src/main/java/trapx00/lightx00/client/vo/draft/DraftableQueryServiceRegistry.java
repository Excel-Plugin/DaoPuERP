package trapx00.lightx00.client.vo.draft;

import trapx00.lightx00.client.bl.clientbl.factory.ClientDraftQueryServiceFactory;
import trapx00.lightx00.client.bl.draftbl.DraftableQueryService;
import trapx00.lightx00.client.bl.financebl.factory.BillDraftQueryServiceFactory;
import trapx00.lightx00.client.bl.promotionbl.factory.PromotionDraftQueryServiceFactory;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.shared.po.draft.DraftType;

import java.util.HashMap;

public class DraftableQueryServiceRegistry {

    private static HashMap<DraftType, DraftableQueryService> map = new HashMap<>();
    static {
        registerAll();
    }

    private static void registerAll() {
        register(DraftType.Bill, BillDraftQueryServiceFactory.getQueryService());
        register(DraftType.Client, ClientDraftQueryServiceFactory.getQueryService());
        register(DraftType.Promotion, PromotionDraftQueryServiceFactory.getQueryService());
    }

    public static void register(DraftType draftType, DraftableQueryService service) {
        map.put(draftType, service);
    }

    public static Draftable queryDraftable(DraftType draftType, String id) {
        DraftableQueryService queryService = map.get(draftType);
        if (queryService != null) {
            return queryService.queryDraft(id);
        } else {
            return null;
        }
    }
}
