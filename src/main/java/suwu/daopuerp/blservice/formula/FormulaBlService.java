package suwu.daopuerp.blservice.formula;

import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.formula.FormulaItem;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.List;

public interface FormulaBlService {
    /**
     * 获得所有的配方单
     *
     * @return
     */
    List<FormulaItem> getAllFormulas();

    /**
     * 通过Id获得配方单
     *
     * @param formulaId
     * @return
     */
    FormulaDto getFormulaById(String formulaId) throws IdDoesNotExistException;

    /**
     * 提交配方单
     *
     * @param formulaDto
     */
    void submit(FormulaDto formulaDto);

    /**
     * 获得下一个配方单Id
     *
     * @return
     */
    String getNextId();

    /**
     * 删除配方单
     *
     * @param id
     */
    void deleteFormula(String id) throws IdDoesNotExistException;
}
