package suwu.daopuerp.dto.formula;

public class FormulaItem {
    private String formulaId;
    private String formulaName;
    private String formulaType;

    public FormulaItem() {
    }

    public FormulaItem(String formulaId, String formulaName, String formulaType) {
        this.formulaId = formulaId;
        this.formulaName = formulaName;
        this.formulaType = formulaType;
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
}
