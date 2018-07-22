package suwu.daopuerp.entity.formula;

import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.entity.annotation.Column;
import suwu.daopuerp.entity.annotation.Table;
import suwu.daopuerp.publicdata.BillType;

import java.util.List;

@Table(name = "FormulaOil")
public class FormulaOil extends Formula {
    @Column(name = "outLooking")
    private String outLooking;
    @Column(name = "flashPoint")
    private String flashPoint;
    @Column(name = "viscosity")
    private String viscosity;

    public FormulaOil() {
    }

    public FormulaOil(String formulaId, String formulaCode, String formulaName, String formulaType, List<StockItem> stockItems, String stableAttr1, String stableAttr2, String outLooking, String flashPoint, String viscosity) {
        super(formulaId, BillType.OIL, formulaCode, formulaName, formulaType, stockItems, stableAttr1, stableAttr2);
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
