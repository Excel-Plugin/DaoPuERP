package suwu.daopuerp.dto.productionbill;

import suwu.daopuerp.publicdata.BillType;

public class ProductionBillItem {
    private BillType billType;
    private String billId;
    private String productionName;
    private String billDate;
    private String productionType;
    private String productionId;

    public ProductionBillItem() {
    }

    public ProductionBillItem(BillType billType, String billId, String productionName, String billDate, String productionType, String productionId) {
        this.billType = billType;
        this.billId = billId;
        this.productionName = productionName;
        this.billDate = billDate;
        this.productionType = productionType;
        this.productionId = productionId;
    }

    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getProductionType() {
        return productionType;
    }

    public void setProductionType(String productionType) {
        this.productionType = productionType;
    }

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }
}
