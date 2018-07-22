package suwu.daopuerp.dao.formula;

import suwu.daopuerp.entity.formula.Formula;

import java.util.List;

public interface FormulaDao<T extends Formula> {

    List<T> findAllFormulas();

    T findFormulaByFormulaId(String formulaId);

    T saveFormula(T formula);
}
