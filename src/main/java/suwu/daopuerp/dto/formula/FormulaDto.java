package suwu.daopuerp.dto.formula;

import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.presentation.formulaui.FormulaDetailUi;
import suwu.daopuerp.presentation.formulaui.FormulaModifyUi;
import suwu.daopuerp.presentation.formulaui.liquid.FormulaLiquidDetailUiController;
import suwu.daopuerp.presentation.formulaui.liquid.FormulaLiquidModifyUiController;
import suwu.daopuerp.presentation.formulaui.oil.FormulaOilDetailUiController;
import suwu.daopuerp.presentation.formulaui.oil.FormulaOilModifyUiController;
import suwu.daopuerp.publicdata.BillType;

import java.util.List;

public class FormulaDto {
    private BillType billType;
    private String formulaId;
    private String formulaCode;
    private String formulaName;
    private String formulaType;
    private List<StockItem> stockItems;
    private String stableAttr1;
    private String stableAttr2;

    public FormulaDto() {
    }

    public FormulaDto(BillType billType, String formulaId, String formulaCode, String formulaName, String formulaType, List<StockItem> stockItems, String stableAttr1, String stableAttr2) {
        this.billType = billType;
        this.formulaId = formulaId;
        this.formulaCode = formulaCode;
        this.formulaName = formulaName;
        this.formulaType = formulaType;
        this.stockItems = stockItems;
        this.stableAttr1 = stableAttr1;
        this.stableAttr2 = stableAttr2;
    }

    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }

    public String getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(String formulaId) {
        this.formulaId = formulaId;
    }

    public String getFormulaCode() {
        return formulaCode;
    }

    public void setFormulaCode(String formulaCode) {
        this.formulaCode = formulaCode;
    }

    public String getFormulaName() {
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    public String getFormulaType() {
        return formulaType;
    }

    public void setFormulaType(String formulaType) {
        this.formulaType = formulaType;
    }

    public List<StockItem> getStockItems() {
        return stockItems;
    }

    public void setStockItems(List<StockItem> stockItems) {
        this.stockItems = stockItems;
    }

    public FormulaModifyUi modifyUi() {
        if (billType == BillType.OIL) {
            return new FormulaOilModifyUiController();
        } else {
            return new FormulaLiquidModifyUiController();
        }
    }

    public FormulaDetailUi detailUi() {
        if (billType == BillType.OIL) {
            return new FormulaOilDetailUiController();
        } else {
            return new FormulaLiquidDetailUiController();
        }
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
}
