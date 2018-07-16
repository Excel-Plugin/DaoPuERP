package suwu.daopuerp.bl.formula.factory;

import suwu.daopuerp.bl.formula.mock.FormulaBlServiceMock;
import suwu.daopuerp.blservice.formula.FormulaBlService;

public class FormulaBlServiceFactory {
    private static FormulaBlService formulaBlService = new FormulaBlServiceMock();

    public static FormulaBlService getFormulaBlService() {
        return formulaBlService;
    }
}
