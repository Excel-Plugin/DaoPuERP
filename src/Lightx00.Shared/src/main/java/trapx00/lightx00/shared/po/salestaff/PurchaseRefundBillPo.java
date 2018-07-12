package trapx00.lightx00.shared.po.salestaff;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import trapx00.lightx00.shared.po.bill.BillState;

import java.util.Date;

@DatabaseTable(tableName = "PurchaseRefundBill")
public class PurchaseRefundBillPo extends PurchaseBillBasePo {
    @DatabaseField
    private String clientId;
    @DatabaseField
    private int repository;
    @DatabaseField
    private String comment;
    @DatabaseField
    private double total;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private CommodityItem[] commodityList;

    public PurchaseRefundBillPo() {
    }

    public PurchaseRefundBillPo(String id, Date date, BillState state, String clientId, int repository, String operatorId, String comment, double total, CommodityItem[] commodityList) {
        super(id, date, state, PurchaseBillType.PurchaseRefund,operatorId);
        this.clientId = clientId;
        this.repository = repository;
        this.comment = comment;
        this.total = total;
        this.commodityList = commodityList;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getRepository() {
        return repository;
    }

    public void setRepository(int repository) {
        this.repository = repository;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public CommodityItem[] getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(CommodityItem[] commodityList) {
        this.commodityList = commodityList;
    }
}
