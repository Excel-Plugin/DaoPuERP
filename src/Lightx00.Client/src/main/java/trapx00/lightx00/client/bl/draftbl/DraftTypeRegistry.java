package trapx00.lightx00.client.bl.draftbl;

import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.po.draft.DraftType;

import java.util.HashMap;
import java.util.Map;

public class DraftTypeRegistry {
    private static HashMap<Class<? extends Draftable>, DraftType> draftTypeHashMap = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        register(BillVo.class, DraftType.Bill);
        register(ClientVo.class, DraftType.Client);
        register(PromotionVoBase.class, DraftType.Promotion);

    }

    private static void register(Class<? extends Draftable> clazz, DraftType draftType) {
        draftTypeHashMap.put(clazz, draftType);
    }

    public static DraftType getTypeFromClass(Class<? extends Draftable> clazz) {
        for (Map.Entry<Class<? extends Draftable>, DraftType> entry : draftTypeHashMap.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
