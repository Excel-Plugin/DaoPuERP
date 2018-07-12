package trapx00.lightx00.shared.po.inventorystaff;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;


@DatabaseTable(tableName = "Commodity")
public class CommodityPo implements Serializable {
    //商品
    @DatabaseField(id=true)
    private String id;//型号+名称生成
    @DatabaseField
    private String name;
    @DatabaseField
    private String type;
    @DatabaseField
    private  double amount;
    @DatabaseField
    private  Date productionDate;//生产日期
    @DatabaseField
    private String batch;//批次
    @DatabaseField
    private String batchNo;//批号
    @DatabaseField
    private double purchasePrice;//进价
    @DatabaseField
    private  double recentPurchasePrice;//最近进价
    @DatabaseField
    private double retailPrice;//售价
    @DatabaseField
    private double recentRetailPrice;//最近售价
    @DatabaseField
    private double warningValue;//警戒值
    @DatabaseField
    private double inventoryNum;//报警报溢数量



    @DatabaseField
    private double actualValue;


    public CommodityPo(String id, String name, String type, double amount, Date productionDate,
                       String batch, String batchNo, double purchasePrice,
                       double recentPurchasePrice, double retailPrice, double recentRetailPrice,
                       double warningValue,double actualValue) {
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
        this.actualValue=actualValue;
    }

    public CommodityPo(){

    }
    public String getId() {
        return id;
    }

    public double getActualValue() {
        return actualValue;
    }

    public void setActualValue(double actualValue) {
        this.actualValue = actualValue;
    }
    public double getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(double inventoryNum) {
        this.inventoryNum = inventoryNum;
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


