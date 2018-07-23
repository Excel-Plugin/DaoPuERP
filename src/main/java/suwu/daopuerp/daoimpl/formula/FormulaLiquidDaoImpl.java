package suwu.daopuerp.daoimpl.formula;

import suwu.daopuerp.dao.formula.FormulaLiquidDao;
import suwu.daopuerp.data.fileservice.FileService;
import suwu.daopuerp.data.fileservice.FileServiceImpl;
import suwu.daopuerp.entity.formula.FormulaLiquid;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.List;

public class FormulaLiquidDaoImpl implements FormulaLiquidDao {
    private FileService<FormulaLiquid> fileService = new FileServiceImpl<>();

    @Override
    public List<FormulaLiquid> findAllFormulas() {
        return fileService.findAll(FormulaLiquid.class);
    }

    @Override
    public FormulaLiquid findFormulaByFormulaId(String formulaId) {
        return fileService.findOne(formulaId, FormulaLiquid.class);
    }

    @Override
    public FormulaLiquid saveFormula(FormulaLiquid formula) {
        return fileService.saveTuple(formula);
    }

    @Override
    public void deleteFormulaByFormulaId(String formulaId) throws IdDoesNotExistException {
        fileService.delete(formulaId, FormulaLiquid.class);
    }
}
