package trapx00.lightx00.client.vo.financestaff;

import trapx00.lightx00.shared.po.bill.BillType;

import java.util.Date;

public class TradeHistoryQueryVo { //经营历程表查询条件
    private Date start;
    private Date end;
    private BillType[] billTypes;
    private String[] clientIds;
    private String[] operatorIds;
    private int repository;
    private boolean empty = false;

    public static TradeHistoryQueryVo empty() {
        TradeHistoryQueryVo tradeHistoryQueryVo = new TradeHistoryQueryVo();
        tradeHistoryQueryVo.empty = true;
        return tradeHistoryQueryVo;
    }

    public boolean isEmpty() {
        return empty;
    }

    public TradeHistoryQueryVo(Date start, Date end, BillType[] billTypes, String[] clientIds, String[] operatorIds, int repository) {
        this.start = start;
        this.end = end;
        this.billTypes = billTypes;
        this.clientIds = clientIds;
        this.operatorIds = operatorIds;
        this.repository = repository;
    }

    public TradeHistoryQueryVo() {

    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public BillType[] getBillTypes() {
        return billTypes;
    }

    public void setBillTypes(BillType[] billTypes) {
        this.billTypes = billTypes;
    }

    public String[] getClientIds() {
        return clientIds;
    }

    public void setClientIds(String[] clientIds) {
        this.clientIds = clientIds;
    }

    public String[] getOperatorIds() {
        return operatorIds;
    }

    public void setOperatorIds(String[] operatorIds) {
        this.operatorIds = operatorIds;
    }

    public int getRepository() {
        return repository;
    }

    public void setRepository(int repository) {
        this.repository = repository;
    }
}
