package suwu.daopuerp.bl.formula.factory;

import suwu.daopuerp.bl.formula.FormulaBlServiceImpl;
import suwu.daopuerp.blservice.formula.FormulaBlService;

public class FormulaBlServiceFactory {
    private static FormulaBlService formulaBlService = new FormulaBlServiceImpl();

    public static FormulaBlService getFormulaBlService() {
        return formulaBlService;
    }
}
