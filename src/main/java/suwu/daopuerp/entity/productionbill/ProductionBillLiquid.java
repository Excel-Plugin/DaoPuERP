package suwu.daopuerp.entity.productionbill;

import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.entity.annotation.Column;
import suwu.daopuerp.entity.annotation.Table;
import suwu.daopuerp.publicdata.BillType;

import java.util.List;

@Table(name = "ProductionBillLiquid")
public class ProductionBillLiquid extends ProductionBill {
    @Column(name = "liquidLooking")
    private String liquidLooking;
    @Column(name = "phValue")
    private String phValue;
    @Column(name = "lightValue")
    private String lightValue;

    public ProductionBillLiquid() {
    }

    public ProductionBillLiquid(String billId, String productionDate, String productionName, String billDate, String client, String productionType, String machineId, String productionId, double totalQuantity, String modifyRecord, String comment, String stableAttr1, String stableAttr2, List<ProductionBillStockItem> productionBillStockItems, String liquidLooking, String phValue, String lightValue) {
        super(billId, BillType.LIQUID, productionDate, productionName, billDate, client, productionType, machineId, productionId, totalQuantity, modifyRecord, comment, stableAttr1, stableAttr2, productionBillStockItems);
        this.liquidLooking = liquidLooking;
        this.phValue = phValue;
        this.lightValue = lightValue;
    }

    public String getLiquidLooking() {
        return liquidLooking;
    }

    public void setLiquidLooking(String liquidLooking) {
        this.liquidLooking = liquidLooking;
    }

    public String getPhValue() {
        return phValue;
    }

    public void setPhValue(String phValue) {
        this.phValue = phValue;
    }

    public String getLightValue() {
        return lightValue;
    }

    public void setLightValue(String lightValue) {
        this.lightValue = lightValue;
    }
}
