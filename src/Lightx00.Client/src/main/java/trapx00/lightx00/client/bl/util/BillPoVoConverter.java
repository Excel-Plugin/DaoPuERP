package trapx00.lightx00.client.bl.util;

import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.shared.po.bill.BillPo;

public interface BillPoVoConverter<PoType extends BillPo, VoType extends BillVo> extends PoVoConverter<PoType, VoType> {
    /**
     * Convert vo to po.
     *
     * @param vo vo
     * @return po
     */
    PoType fromVoToPo(VoType vo);

    /**
     * Convert po to vo.
     *
     * @param po po
     * @return vo
     */
    VoType fromPoToVo(PoType po);
}
