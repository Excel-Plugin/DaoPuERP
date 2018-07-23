package suwu.daopuerp.data.formula;

import suwu.daopuerp.dao.factory.FormulaLiquidDaoFactory;
import suwu.daopuerp.dao.factory.FormulaOilDaoFactory;
import suwu.daopuerp.dao.formula.FormulaLiquidDao;
import suwu.daopuerp.dao.formula.FormulaOilDao;
import suwu.daopuerp.dataservice.formula.FormulaDataService;
import suwu.daopuerp.entity.formula.Formula;
import suwu.daopuerp.entity.formula.FormulaLiquid;
import suwu.daopuerp.entity.formula.FormulaOil;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.ArrayList;
import java.util.List;

public class FormulaDataServiceImpl implements FormulaDataService {
    private FormulaLiquidDao formulaLiquidDao = FormulaLiquidDaoFactory.getFormulaLiquidDao();
    private FormulaOilDao formulaOilDao = FormulaOilDaoFactory.getFormulaOilDao();

    @Override
    public List<Formula> getAllFormulas() {
        List<Formula> formulas = new ArrayList<>();
        formulas.addAll(formulaLiquidDao.findAllFormulas());
        formulas.addAll(formulaOilDao.findAllFormulas());
        return formulas;
    }

    @Override
    public Formula getFormulaById(String id) throws IdDoesNotExistException {
        FormulaLiquid formulaLiquid = formulaLiquidDao.findFormulaByFormulaId(id);
        FormulaOil formulaOil = formulaOilDao.findFormulaByFormulaId(id);
        if (formulaLiquid == null && formulaOil == null) {
            throw new IdDoesNotExistException();
        } else {
            return formulaLiquid == null ? formulaOil : formulaLiquid;
        }
    }

    @Override
    public void saveFormula(Formula formula) {
        switch (formula.getBillType()) {
            case OIL:
                formulaOilDao.saveFormula((FormulaOil) formula);
                break;
            case LIQUID:
                formulaLiquidDao.saveFormula((FormulaLiquid) formula);
                break;
        }
    }

    @Override
    public void deleteFormula(String id) throws IdDoesNotExistException {
        try {
            formulaOilDao.deleteFormulaByFormulaId(id);
        } catch (IdDoesNotExistException e) {
            e.printStackTrace();
            formulaLiquidDao.deleteFormulaByFormulaId(id);
        }
    }
}
