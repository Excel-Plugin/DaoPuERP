package trapx00.lightx00.client.bl.util;

public interface PoVoConverter<PoType, VoType> {
    /**
     * Convert vo to po.
     * @param vo vo
     * @return po
     */
    PoType fromVoToPo(VoType vo);

    /**
     * Convert po to vo.
     * @param po po
     * @return vo
     */
    VoType fromPoToVo(PoType po);
}
