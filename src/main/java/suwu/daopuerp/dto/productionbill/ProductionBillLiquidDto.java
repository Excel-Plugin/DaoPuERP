package suwu.daopuerp.dto.productionbill;

import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.publicdata.BillType;

import java.util.Date;
import java.util.List;

public class ProductionBillLiquidDto extends ProductionBillDto {
    private String liquidLooking;
    private String phValue;
    private String lightValue;

    public ProductionBillLiquidDto() {
    }

    public ProductionBillLiquidDto(String billId, Date productionDate, String productionName, String billDate, String client, String productionType, String machineId, String productionId, double totalQuantity, String modifyRecord, String comment, String stableAttr1, String stableAttr2, List<ProductionBillStockItem> productionBillStockItems, String liquidLooking, String phValue, String lightValue) {
        super(BillType.LIQUID, billId, productionDate, productionName, billDate, client, productionType, machineId, productionId, totalQuantity, modifyRecord, comment, stableAttr1, stableAttr2, productionBillStockItems);
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
