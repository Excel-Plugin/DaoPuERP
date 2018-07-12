package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.vo.financestaff.TradeSituationVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

public interface TradeSituationBlService {

    /**
     * Queries TradeSituation during specified time range
     * @param start start time
     * @param end end time
     * @return TradeSituation during specified time range
     */

    TradeSituationVo query(Date start, Date end);

    /**
     * Exports a TradeSituation
     * @param situation TradeSituation to be exported
     * @return whether the operation is done successfully
     */

    ResultMessage export(TradeSituationVo situation);
}
