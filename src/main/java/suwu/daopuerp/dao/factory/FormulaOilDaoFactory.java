package suwu.daopuerp.dao.factory;

import suwu.daopuerp.dao.formula.FormulaOilDao;
import suwu.daopuerp.daoimpl.formula.FormulaOilDaoImpl;

public class FormulaOilDaoFactory {
    private static FormulaOilDao formulaOilDao = new FormulaOilDaoImpl();

    public static FormulaOilDao getFormulaOilDao() {
        return formulaOilDao;
    }
}
