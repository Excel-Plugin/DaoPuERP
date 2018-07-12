package trapx00.lightx00.client.bl.financebl.mock;

import trapx00.lightx00.client.bl.financebl.TradeHistoryBlController;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.financestaff.FinanceBillVo;
import trapx00.lightx00.client.vo.financestaff.PaymentBillVo;
import trapx00.lightx00.client.vo.financestaff.TradeHistoryQueryVo;
import trapx00.lightx00.client.vo.financestaff.TradeHistoryVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.Transcation;
import trapx00.lightx00.shared.queryvo.FinanceBillQueryVo;

import java.util.Date;

public class TradeHistoryBlControllerMock extends TradeHistoryBlController {
    /**
     * Queries a TradeHistory.
     *
     * @param query TradeHistory query condition
     * @return TradeHistory that matches query condition
     */
    @Override
    public TradeHistoryVo query(TradeHistoryQueryVo query) {
        return new TradeHistoryVo(
                new BillVo[]{
                        new PaymentBillVo("123", new Date(), BillState.Approved, "123", "123", new Transcation[]{new Transcation(1, 0, "123")}, 0)
                });
    }

    /**
     * Export a TradeHistory.
     *
     * @param tradeHistory TradeHistory to be exported
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage export(TradeHistoryVo tradeHistory) {
        return ResultMessage.Success;
    }

    /**
     * Queries FinanceBill.
     *
     * @param query FinanceBill query condition
     * @return Finance bills that match query condition
     */
    @Override
    public FinanceBillVo[] query(FinanceBillQueryVo query) {
        return new FinanceBillVo[]{
                new PaymentBillVo("123", new Date(), BillState.Approved, "123", "123", new Transcation[]{new Transcation(1, 0, "123")}, 0)
        };
    }

    /**
     * Queries draft with id.
     *
     * @param id id
     * @return draft
     */
    @Override
    public Draftable queryDraft(String id) {
        return super.queryDraft(id);
    }
}
