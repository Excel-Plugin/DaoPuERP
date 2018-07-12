package trapx00.lightx00.shared.po.salestaff;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import trapx00.lightx00.shared.po.bill.BillState;

import java.util.Date;

@DatabaseTable(tableName = "SaleBill")
public class SaleBillPo extends SaleBillBasePo {
    @DatabaseField
    String salesmanId;
    @DatabaseField
    private String clientId;
    @DatabaseField
    private int repository;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private CommodityItem[] commodityList;
    @DatabaseField
    private double originTotal;
    @DatabaseField
    private double minusProfits;
    @DatabaseField
    private double token;
    @DatabaseField
    private double ultiTotal;
    @DatabaseField
    private String comment;
    @DatabaseField
    private int clientLevel;
    @DatabaseField
    private String promotionId;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private CommodityItem[] giftList;
    @DatabaseField
    private double giftToken;

    public SaleBillPo() {
    }

    public SaleBillPo(String id, Date date, BillState state, String clientId, String salesmanId, String operatorId, int repository, CommodityItem[] commodityList, double originTotal, double minusProfits, double token, double ultiTotal, String comment, int clientLevel, String promotionId, CommodityItem[] giftList, double giftToken) {
        super(id, date, state, SaleBillType.Sale, operatorId);
        this.clientId = clientId;
        this.salesmanId = salesmanId;
        this.repository = repository;
        this.commodityList = commodityList;
        this.originTotal = originTotal;
        this.minusProfits = minusProfits;
        this.token = token;
        this.ultiTotal = ultiTotal;
        this.comment = comment;
        this.clientLevel = clientLevel;
        this.promotionId = promotionId;
        this.giftList = giftList;
        this.giftToken = giftToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String defaultOperatorId) {
        this.salesmanId = defaultOperatorId;
    }

    public int getRepository() {
        return repository;
    }

    public void setRepository(int repository) {
        this.repository = repository;
    }

    public CommodityItem[] getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(CommodityItem[] commodityList) {
        this.commodityList = commodityList;
    }

    public double getOriginTotal() {
        return originTotal;
    }

    public void setOriginTotal(double originTotal) {
        this.originTotal = originTotal;
    }

    public double getMinusProfits() {
        return minusProfits;
    }

    public void setMinusProfits(double minusProfits) {
        this.minusProfits = minusProfits;
    }

    public double getToken() {
        return token;
    }

    public void setToken(double token) {
        this.token = token;
    }

    public double getUltiTotal() {
        return ultiTotal;
    }

    public void setUltiTotal(double ultiTotal) {
        this.ultiTotal = ultiTotal;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getClientLevel() {
        return clientLevel;
    }

    public void setClientLevel(int clientLevel) {
        this.clientLevel = clientLevel;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public CommodityItem[] getGiftList() {
        return giftList;
    }

    public void setGiftList(CommodityItem[] giftList) {
        this.giftList = giftList;
    }

    public double getGiftToken() {
        return giftToken;
    }

    public void setGiftToken(double giftToken) {
        this.giftToken = giftToken;
    }
}
