package suwu.daopuerp.dto.formula;

public class FormulaAndAmountDto {
    private double amount;
    private FormulaDto formulaDto;

    public FormulaAndAmountDto() {
    }

    public FormulaAndAmountDto(double amount, FormulaDto formulaDto) {
        this.amount = amount;
        this.formulaDto = formulaDto;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public FormulaDto getFormulaDto() {
        return formulaDto;
    }

    public void setFormulaDto(FormulaDto formulaDto) {
        this.formulaDto = formulaDto;
    }
}
