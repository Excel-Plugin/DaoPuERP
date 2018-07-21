package suwu.daopuerp.dto.formula;

import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.publicdata.BillType;

import java.util.List;

public class FormulaLiquidDto extends FormulaDto {
    private String liquidLooking;
    private String phValue;
    private String lightValue;

    public FormulaLiquidDto() {
    }

    public FormulaLiquidDto(String formulaId, String formulaCode, String formulaName, String formulaType, List<StockItem> stockItems, String stableAttr1, String stableAttr2, String liquidLooking, String phValue, String lightValue) {
        super(BillType.LIQUID, formulaId, formulaCode, formulaName, formulaType, stockItems, stableAttr1, stableAttr2);
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
