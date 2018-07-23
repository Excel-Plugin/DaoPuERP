package suwu.daopuerp.presentation.formulaui;

import suwu.daopuerp.dto.formula.FormulaAndAmountDto;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.exception.IdDoesNotExistException;
import suwu.daopuerp.publicdata.BillType;

import java.util.function.Consumer;

public interface FormulaSelectUi {
    /**
     * show the select formula dialog
     *
     * @param callback call back function
     * @param billType
     */
    void showFormulaSelectDialog(Consumer<FormulaAndAmountDto> callback, BillType billType);

    /**
     * query the whole formulaDto by id
     *
     * @param id
     * @return the whole formulaDto
     */
    FormulaDto queryById(String id) throws IdDoesNotExistException;
}
