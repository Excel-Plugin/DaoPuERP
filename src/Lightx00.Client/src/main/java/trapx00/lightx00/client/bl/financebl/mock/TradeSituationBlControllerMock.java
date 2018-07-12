package trapx00.lightx00.client.bl.financebl.mock;

import trapx00.lightx00.client.bl.financebl.TradeSituationBlController;
import trapx00.lightx00.client.vo.financestaff.TradeSituationVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

public class TradeSituationBlControllerMock extends TradeSituationBlController {

    /**
     * Queries TradeSituation during specified time range
     *
     * @param start start time
     * @param end   end time
     * @return TradeSituation during specified time range
     */
    @Override
    public TradeSituationVo query(Date start, Date end) {
        return new TradeSituationVo(0,0,0,0,0,0,0,0,0);
    }

    /**
     * Exports a TradeSituation
     *
     * @param situation TradeSituation to be exported
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage export(TradeSituationVo situation) {
        return ResultMessage.Success;
    }


}
