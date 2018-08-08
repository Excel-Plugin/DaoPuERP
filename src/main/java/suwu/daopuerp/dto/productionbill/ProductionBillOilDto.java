package suwu.daopuerp.dto.productionbill;

import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.publicdata.BillType;

import java.util.Date;
import java.util.List;

public class ProductionBillOilDto extends ProductionBillDto {
    private String outLooking;
    private String flashPoint;
    private String viscosity;

    public ProductionBillOilDto() {
    }

    public ProductionBillOilDto(String billId, Date productionDate, String productionName, String billDate, String client, String productionType, String machineId, String productionId, double totalQuantity, String modifyRecord, String comment, String stableAttr1, String stableAttr2, List<ProductionBillStockItem> productionBillStockItems, String outLooking, String flashPoint, String viscosity) {
        super(BillType.OIL, billId, productionDate, productionName, billDate, client, productionType, machineId, productionId, totalQuantity, modifyRecord, comment, stableAttr1, stableAttr2, productionBillStockItems);
        this.outLooking = outLooking;
        this.flashPoint = flashPoint;
        this.viscosity = viscosity;
    }

    public String getOutLooking() {
        return outLooking;
    }

    public void setOutLooking(String outLooking) {
        this.outLooking = outLooking;
    }

    public String getFlashPoint() {
        return flashPoint;
    }

    public void setFlashPoint(String flashPoint) {
        this.flashPoint = flashPoint;
    }

    public String getViscosity() {
        return viscosity;
    }

    public void setViscosity(String viscosity) {
        this.viscosity = viscosity;
    }
}
