package suwu.daopuerp.dto.formula;

import suwu.daopuerp.presentation.formulaui.FormulaModifyUi;
import suwu.daopuerp.presentation.formulaui.FormulaModifyUiController;

import java.util.List;

public class FormulaDto {
    private String formulaId;
    private String formulaName;
    private String formulaType;
    private List<StockItem> stockItems;

    public FormulaDto() {
    }

    public FormulaDto(String formulaId, String formulaName, String formulaType, List<StockItem> stockItems) {
        this.formulaId = formulaId;
        this.formulaName = formulaName;
        this.formulaType = formulaType;
        this.stockItems = stockItems;
    }

    public String getFormulaId() {
        return formulaId;
    }

    public void setFormulaId(String formulaId) {
        this.formulaId = formulaId;
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
        return new FormulaModifyUiController();
    }
}
