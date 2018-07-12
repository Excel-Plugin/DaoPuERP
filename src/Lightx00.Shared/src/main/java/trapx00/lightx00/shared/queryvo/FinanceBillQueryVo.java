package trapx00.lightx00.shared.queryvo;

import trapx00.lightx00.shared.po.financestaff.FinanceBillType;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class FinanceBillQueryVo {
    private HashMap<FinanceBillType, SpecificFinanceBillQueryVo> hashMap = new HashMap<>();

    public FinanceBillQueryVo addQueryVoForSomeTypes(SpecificFinanceBillQueryVo specificFinanceBillQueryVo, FinanceBillType... types) {
        for (FinanceBillType type: types) {
            hashMap.put(type, specificFinanceBillQueryVo);
        }

        return this;
    }

    public FinanceBillQueryVo addQueryVOForAllType(SpecificFinanceBillQueryVo specificFinanceBillQueryVo) {
        return addQueryVoForSomeTypes(specificFinanceBillQueryVo, FinanceBillType.values());
    }

    public <T extends SpecificFinanceBillQueryVo> T getSpecificFinanceBillQueryVo(FinanceBillType type) {
        return (T) hashMap.get(type);
    }

    public HashMap<FinanceBillType, SpecificFinanceBillQueryVo> getHashMap() {
        return hashMap;
    }
}
