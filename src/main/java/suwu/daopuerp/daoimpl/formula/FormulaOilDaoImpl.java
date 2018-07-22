package suwu.daopuerp.daoimpl.formula;

import suwu.daopuerp.dao.formula.FormulaOilDao;
import suwu.daopuerp.data.fileservice.FileService;
import suwu.daopuerp.data.fileservice.FileServiceImpl;
import suwu.daopuerp.entity.formula.FormulaOil;

import java.util.List;

public class FormulaOilDaoImpl implements FormulaOilDao {
    FileService<FormulaOil> fileService = new FileServiceImpl<>();

    @Override
    public List<FormulaOil> findAllFormulas() {
        return fileService.findAll(FormulaOil.class);
    }

    @Override
    public FormulaOil findFormulaByFormulaId(String formulaId) {
        return fileService.findOne(formulaId, FormulaOil.class);
    }

    @Override
    public FormulaOil saveFormula(FormulaOil formula) {
        return fileService.saveTuple(formula);
    }
}
