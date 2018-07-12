package trapx00.lightx00.client.vo.inventorystaff;

import java.util.Date;

public class CommodityVo {
    //商品
    private String id;//型号+名称生成
    private String name;
    private String type;
    private double amount;
    private Date productionDate;//生产日期
    private String batch;//批次
    private String batchNo;//批号
    private double purchasePrice;//进价
    private double recentPurchasePrice;//最近进价
    private double retailPrice;//售价
    private double recentRetailPrice;//最近售价
    private double warningValue;//警戒值

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }

    private double actualAmount=0;

    public CommodityVo(String id, String name, String type, double amount, Date productionDate,
                       String batch, String batchNo, double purchasePrice,
                       double recentPurchasePrice, double retailPrice, double recentRetailPrice,
                       double warningValue,double actualAmount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.productionDate = productionDate;
        this.batch = batch;
        this.batchNo = batchNo;
        this.purchasePrice = purchasePrice;
        this.recentPurchasePrice = recentPurchasePrice;
        this.retailPrice = retailPrice;
        this.recentRetailPrice = recentRetailPrice;
        this.warningValue = warningValue;
        this.actualAmount=actualAmount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public String getBatch() {
        return batch;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getRecentPurchasePrice() {
        return recentPurchasePrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public double getRecentRetailPrice() {
        return recentRetailPrice;
    }

    public double getWarningValue() {
        return warningValue;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setRecentPurchasePrice(double recentPurchasePrice) {
        this.recentPurchasePrice = recentPurchasePrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public void setRecentRetailPrice(double recentRetailPrice) {
        this.recentRetailPrice = recentRetailPrice;
    }

    public void setWarningValue(double warningValue) {
        this.warningValue = warningValue;
    }
}
