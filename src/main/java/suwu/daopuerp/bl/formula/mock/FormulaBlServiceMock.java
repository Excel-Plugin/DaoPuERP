package suwu.daopuerp.bl.formula.mock;

import suwu.daopuerp.blservice.formula.FormulaBlService;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.formula.FormulaItem;
import suwu.daopuerp.dto.formula.FormulaLiquidDto;
import suwu.daopuerp.dto.stock.StockItem;

import java.util.ArrayList;
import java.util.List;

public class FormulaBlServiceMock implements FormulaBlService {
    @Override
    public List<FormulaItem> getAllFormulas() {
        List<FormulaItem> formulaItems = new ArrayList<>();
        formulaItems.add(new FormulaItem("1", "2", "3", "4"));
        return formulaItems;
    }

    @Override
    public FormulaDto getFormulaById(String formulaId) {
        List<StockItem> stockItems = new ArrayList<>();
        stockItems.add(new StockItem("1", "2", 0.5, 1.2, "站上蛋黄酱"));
        return new FormulaLiquidDto("4", "5", "6", "6", stockItems, "7", "8", "9", "10", "11");
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
    public String getNextId() {
        return "test0";
    }

    /**
     * 删除配方单
     *
     * @param id
     */
    @Override
    public void deleteFormula(String id) {

    }

    /**
     * @param keyword
     * @return
     */
    @Override
    public FormulaItem[] query(String keyword) {
        return new FormulaItem[0];
    }
}
