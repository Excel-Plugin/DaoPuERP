package suwu.daopuerp.bl.formula;

import suwu.daopuerp.blservice.formula.FormulaBlService;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.formula.FormulaItem;

import java.util.List;

public class FormulaBlServiceImpl implements FormulaBlService {
    @Override
    public List<FormulaItem> getAllFormulas() {
        return null;
    }

    @Override
    public FormulaDto getFormulaById(String formulaId) {
        return null;
    }

    @Override
    public void submit(FormulaDto formulaDto) {

    }

    /**
     * 获得下一个测试配方单Id
     *
     * @return
     */
    @Override
    public String getNextTestId() {
        return null;
    }
}
