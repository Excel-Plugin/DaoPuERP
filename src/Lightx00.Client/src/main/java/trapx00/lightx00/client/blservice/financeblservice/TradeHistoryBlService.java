package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.vo.financestaff.TradeHistoryQueryVo;
import trapx00.lightx00.client.vo.financestaff.TradeHistoryVo;
import trapx00.lightx00.shared.po.ResultMessage;

public interface TradeHistoryBlService {
    /**
     * Queries a TradeHistory
     * @param query TradeHistory query condition
     * @return TradeHistory that matches query condition
     */

    TradeHistoryVo query(TradeHistoryQueryVo query);

    /**
     * Export a TradeHistory
     * @param tradeHistory TradeHistory to be exported
     * @return whether the operation is done successfully
     */

    ResultMessage export(TradeHistoryVo tradeHistory);
}
