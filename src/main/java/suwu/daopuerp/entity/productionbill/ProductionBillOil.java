package suwu.daopuerp.entity.productionbill;

import suwu.daopuerp.entity.annotation.Column;
import suwu.daopuerp.entity.annotation.Table;
import suwu.daopuerp.publicdata.BillType;

import java.util.List;

@Table(name = "ProductionBillOil")
public class ProductionBillOil extends ProductionBill {
    @Column(name = "outLooking")
    private String outLooking;
    @Column(name = "flashPoint")
    private String flashPoint;
    @Column(name = "viscosity")
    private String viscosity;

    public ProductionBillOil() {
    }

    public ProductionBillOil(String billId, String productionDate, String productionName, String billDate, String client, String productionType, String machineId, String productionId, double totalQuantity, String modifyRecord, String comment, String stableAttr1, String stableAttr2, List<String> stockIds, String outLooking, String flashPoint, String viscosity) {
        super(billId, BillType.OIL, productionDate, productionName, billDate, client, productionType, machineId, productionId, totalQuantity, modifyRecord, comment, stableAttr1, stableAttr2, stockIds);
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
