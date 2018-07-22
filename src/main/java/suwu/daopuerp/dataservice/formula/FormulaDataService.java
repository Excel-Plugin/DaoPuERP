package suwu.daopuerp.dataservice.formula;

import suwu.daopuerp.entity.formula.Formula;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.List;

public interface FormulaDataService {
    List<Formula> getAllFormulas();

    Formula getFormulaById(String id) throws IdDoesNotExistException;

    void saveFormula(Formula formula);
}
