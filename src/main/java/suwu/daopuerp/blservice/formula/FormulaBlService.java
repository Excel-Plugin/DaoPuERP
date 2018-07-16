package suwu.daopuerp.blservice.formula;

import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.formula.FormulaItem;

import java.util.List;

public interface FormulaBlService {
    List<FormulaItem> getAllFormulas();

    FormulaDto getFormulaById(String formulaId);
}
