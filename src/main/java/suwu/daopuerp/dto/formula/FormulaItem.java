package suwu.daopuerp.dto.formula;

public class FormulaItem {
    private String formulaId;
    private String formulaCode;
    private String formulaName;
    private String formulaType;

    public FormulaItem() {
    }

    public FormulaItem(String formulaId, String formulaCode, String formulaName, String formulaType) {
        this.formulaId = formulaId;
        this.formulaCode = formulaCode;
        this.formulaName = formulaName;
        this.formulaType = formulaType;
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
}
