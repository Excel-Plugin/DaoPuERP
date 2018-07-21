package suwu.daopuerp.dto.formula;

import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.publicdata.BillType;

import java.util.List;

public class FormulaOilDto extends FormulaDto {
    private String outLooking;
    private String flashPoint;
    private String viscosity;

    public FormulaOilDto() {
    }

    public FormulaOilDto(String formulaId, String formulaCode, String formulaName, String formulaType, List<StockItem> stockItems, String stableAttr1, String stableAttr2, String outLooking, String flashPoint, String viscosity) {
        super(BillType.OIL, formulaId, formulaCode, formulaName, formulaType, stockItems, stableAttr1, stableAttr2);
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
