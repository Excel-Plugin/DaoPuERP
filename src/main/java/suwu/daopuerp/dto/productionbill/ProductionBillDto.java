package suwu.daopuerp.dto.productionbill;

import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.presentation.productionbillui.ProductionBillDetailUi;
import suwu.daopuerp.presentation.productionbillui.ProductionBillModifyUi;
import suwu.daopuerp.presentation.productionbillui.liquid.ProductionBillLiquidDetailUiController;
import suwu.daopuerp.presentation.productionbillui.liquid.ProductionBillLiquidModifyUiController;
import suwu.daopuerp.presentation.productionbillui.oil.ProductionBillOilDetailUiController;
import suwu.daopuerp.presentation.productionbillui.oil.ProductionBillOilModifyUiController;
import suwu.daopuerp.publicdata.BillType;

import java.util.List;

public class ProductionBillDto {
    private BillType billType;
    private String billId;
    private String productionDate;
    private String productionName;
    private String billDate;
    private String client;
    private String productionType;
    private String machineId;
    private String productionId;
    private double totalQuantity;
    private String modifyRecord;
    private String comment;
    private String stableAttr1;
    private String stableAttr2;
    private List<ProductionBillStockItem> productionBillStockItems;

    public ProductionBillDto() {
    }

    public ProductionBillDto(BillType billType, String billId, String productionDate, String productionName, String billDate, String client, String productionType, String machineId, String productionId, double totalQuantity, String modifyRecord, String comment, String stableAttr1, String stableAttr2, List<ProductionBillStockItem> productionBillStockItems) {
        this.billType = billType;
        this.billId = billId;
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

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
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

    public ProductionBillModifyUi modifyUi() {
        switch (billType) {
            case LIQUID:
                return new ProductionBillLiquidModifyUiController();
            case OIL:
                return new ProductionBillOilModifyUiController();
            default:
                return null;
        }
    }

    public ProductionBillDetailUi detailUi() {
        switch (billType) {
            case LIQUID:
                return new ProductionBillLiquidDetailUiController();
            case OIL:
                return new ProductionBillOilDetailUiController();
            default:
                return null;
        }
    }
}
