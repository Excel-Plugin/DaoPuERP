package suwu.daopuerp.presentation.formulaui;

import suwu.daopuerp.dto.formula.FormulaDto;

import java.util.function.Consumer;

public interface FormulaSelectUi {
    /**
     * show the select formula dialog
     *
     * @param callback call back function
     */
    void showFormulaSelectDialog(Consumer<FormulaDto> callback);

    /**
     * query the whole formulaDto by id
     *
     * @param id
     * @return the whole formulaDto
     */
    FormulaDto queryById(String id);
}
