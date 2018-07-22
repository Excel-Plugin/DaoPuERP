package suwu.daopuerp.entity.formula;

import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.entity.annotation.Column;
import suwu.daopuerp.entity.annotation.Table;
import suwu.daopuerp.publicdata.BillType;

import java.util.List;

@Table(name = "FormulaLiquid")
public class FormulaLiquid extends Formula {
    @Column(name = "liquidLooking")
    private String liquidLooking;
    @Column(name = "phValue")
    private String phValue;
    @Column(name = "lightValue")
    private String lightValue;

    public FormulaLiquid() {
    }

    public FormulaLiquid(String formulaId, String formulaCode, String formulaName, String formulaType, List<StockItem> stockItems, String stableAttr1, String stableAttr2, String liquidLooking, String phValue, String lightValue) {
        super(formulaId, BillType.LIQUID, formulaCode, formulaName, formulaType, stockItems, stableAttr1, stableAttr2);
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
