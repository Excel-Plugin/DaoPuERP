package suwu.daopuerp.bl.formula.mock;

import suwu.daopuerp.blservice.formula.FormulaBlService;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.formula.FormulaItem;
import suwu.daopuerp.dto.stock.StockItem;

import java.util.ArrayList;
import java.util.List;

public class FormulaBlServiceMock implements FormulaBlService {
    @Override
    public List<FormulaItem> getAllFormulas() {
        List<FormulaItem> formulaItems = new ArrayList<>();
        formulaItems.add(new FormulaItem("1", "2", "3"));
        return formulaItems;
    }

    @Override
    public FormulaDto getFormulaById(String formulaId) {
        List<StockItem> stockItems = new ArrayList<>();
        stockItems.add(new StockItem("1", "2", 0.5, 1.2, "站上蛋黄酱"));
        return new FormulaDto("4", "5", "6", stockItems);
    }
}
