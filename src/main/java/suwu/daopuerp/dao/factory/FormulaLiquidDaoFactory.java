package suwu.daopuerp.dao.factory;

import suwu.daopuerp.dao.formula.FormulaLiquidDao;
import suwu.daopuerp.daoimpl.formula.FormulaLiquidDaoImpl;

public class FormulaLiquidDaoFactory {
    private static FormulaLiquidDao formulaLiquidDao = new FormulaLiquidDaoImpl();

    public static FormulaLiquidDao getFormulaLiquidDao() {
        return formulaLiquidDao;
    }
}
