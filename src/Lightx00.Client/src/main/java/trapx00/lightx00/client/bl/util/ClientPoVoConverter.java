package trapx00.lightx00.client.bl.util;

import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.po.client.ClientPo;
import trapx00.lightx00.shared.po.client.ClientState;

public interface ClientPoVoConverter extends PoVoConverter<ClientPo, ClientVo> {
    /**
     * /**
     * Convert vo to po, with default clientState=Real.
     *
     * @param clientVo vo
     * @return po
     */
    ClientPo fromVoToPo(ClientVo clientVo);

    /**
     * Convert vo to po.
     *
     * @param clientVo vo
     * @return po
     */
    ClientPo fromVoToPo(ClientVo clientVo, ClientState clientState);

    /**
     * Convert po to vo.
     *
     * @param clientPo po
     * @return vo
     */
    ClientVo fromPoToVo(ClientPo clientPo);
}
