package suwu.daopuerp.dataservice.factory;

import suwu.daopuerp.data.formula.FormulaDataServiceImpl;
import suwu.daopuerp.dataservice.formula.FormulaDataService;

public class FormulaDataServiceFactory {
    private static FormulaDataService formulaDataService = new FormulaDataServiceImpl();

    public static FormulaDataService getFormulaDataService() {
        return formulaDataService;
    }
}
