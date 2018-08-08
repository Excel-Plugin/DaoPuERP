package suwu.daopuerp.entity.productionbill;

import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.entity.Entity;
import suwu.daopuerp.entity.annotation.*;
import suwu.daopuerp.publicdata.BillType;

import java.util.Date;
import java.util.List;

@Table(name = "ProductionBill")
public class ProductionBill extends Entity {
    @Id
    @Column(name = "billId")
    private String billId;
    @EnumTranslate(targetClass = BillType.class)
    @Column(name = "outLooking")
    private BillType billType;
    @JsonSerialize
    @Column(name = "productionDate")
    private Date productionDate;
    @Column(name = "productionName")
    private String productionName;
    @JsonSerialize
    @Column(name = "billDate")
    private Date billDate;
    @Column(name = "client")
    private String client;
    @Column(name = "productionType")
    private String productionType;
    @Column(name = "machineId")
    private String machineId;
    @Column(name = "productionId")
    private String productionId;
    @Column(name = "totalQuantity")
    private double totalQuantity;
    @Column(name = "modifyRecord")
    private String modifyRecord;
    @Column(name = "comment")
    private String comment;
    @Column(name = "stableAttr1")
    private String stableAttr1;
    @Column(name = "stableAttr2")
    private String stableAttr2;
    @JsonSerialize
    @Column(name = "productionBillStockItems")
    private List<ProductionBillStockItem> productionBillStockItems;

    public ProductionBill() {
    }

    public ProductionBill(String billId, BillType billType, Date productionDate, String productionName, Date billDate, String client, String productionType, String machineId, String productionId, double totalQuantity, String modifyRecord, String comment, String stableAttr1, String stableAttr2, List<ProductionBillStockItem> productionBillStockItems) {
        this.billId = billId;
        this.billType = billType;
        this.productionDate = productionDate;
        this.productionName = productionName;
        this.billDate = billDate;
        this.client = client;
        this.productionType = productionType;
        this.machineId = machineId;
        this.productionId = productionId;
        this.totalQuantity = totalQuantity;
        this.modifyRecord = modifyRecord;
        this.comment = comment;
        this.stableAttr1 = stableAttr1;
        this.stableAttr2 = stableAttr2;
        this.productionBillStockItems = productionBillStockItems;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getProductionName() {
        return productionName;
    }

    public void setProductionName(String productionName) {
        this.productionName = productionName;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProductionType() {
        return productionType;
    }

    public void setProductionType(String productionType) {
        this.productionType = productionType;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getModifyRecord() {
        return modifyRecord;
    }

    public void setModifyRecord(String modifyRecord) {
        this.modifyRecord = modifyRecord;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStableAttr1() {
        return stableAttr1;
    }

    public void setStableAttr1(String stableAttr1) {
        this.stableAttr1 = stableAttr1;
    }

    public String getStableAttr2() {
        return stableAttr2;
    }

    public void setStableAttr2(String stableAttr2) {
        this.stableAttr2 = stableAttr2;
    }

    public List<ProductionBillStockItem> getProductionBillStockItems() {
        return productionBillStockItems;
    }

    public void setProductionBillStockItems(List<ProductionBillStockItem> productionBillStockItems) {
        this.productionBillStockItems = productionBillStockItems;
    }
}
