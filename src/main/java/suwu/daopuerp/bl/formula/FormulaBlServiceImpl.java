package suwu.daopuerp.bl.formula;

import suwu.daopuerp.blservice.formula.FormulaBlService;
import suwu.daopuerp.dataservice.factory.FormulaDataServiceFactory;
import suwu.daopuerp.dataservice.formula.FormulaDataService;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.formula.FormulaItem;
import suwu.daopuerp.dto.formula.FormulaLiquidDto;
import suwu.daopuerp.dto.formula.FormulaOilDto;
import suwu.daopuerp.entity.formula.Formula;
import suwu.daopuerp.entity.formula.FormulaLiquid;
import suwu.daopuerp.entity.formula.FormulaOil;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FormulaBlServiceImpl implements FormulaBlService {
    private FormulaDataService formulaDataService = FormulaDataServiceFactory.getFormulaDataService();

    @Override
    public List<FormulaItem> getAllFormulas() {
        List<Formula> formulas = formulaDataService.getAllFormulas();
        List<FormulaItem> formulaItems = formulas.stream().collect(ArrayList::new, (list, item) -> list.add(new FormulaItem(item.getFormulaId(), item.getFormulaCode(), item.getFormulaName(), item.getFormulaType())), ArrayList::addAll);
        return formulaItems;
    }

    @Override
    public FormulaDto getFormulaById(String formulaId) throws IdDoesNotExistException {
        Formula formula = formulaDataService.getFormulaById(formulaId);
        switch (formula.getBillType()) {
            case LIQUID:
                FormulaLiquid formulaLiquid = (FormulaLiquid) formula;
                return new FormulaLiquidDto(formulaLiquid.getFormulaId(), formulaLiquid.getFormulaCode(), formulaLiquid.getFormulaName(), formulaLiquid.getFormulaType(), formulaLiquid.getStockItems(), formulaLiquid.getStableAttr1(), formulaLiquid.getStableAttr2(), formulaLiquid.getLiquidLooking(), formulaLiquid.getPhValue(), formulaLiquid.getLightValue());
            case OIL:
                FormulaOil formulaOil = (FormulaOil) formula;
                return new FormulaOilDto(formulaOil.getFormulaId(), formulaOil.getFormulaCode(), formulaOil.getFormulaName(), formulaOil.getFormulaType(), formulaOil.getStockItems(), formulaOil.getStableAttr1(), formulaOil.getStableAttr2(), formulaOil.getOutLooking(), formulaOil.getFlashPoint(), formulaOil.getViscosity());
        }
        throw new IdDoesNotExistException();
    }

    @Override
    public void submit(FormulaDto formulaDto) {
        switch (formulaDto.getBillType()) {
            case LIQUID:
                FormulaLiquidDto formulaLiquidDto = (FormulaLiquidDto) formulaDto;
                formulaDataService.saveFormula(new FormulaLiquid(formulaLiquidDto.getFormulaId(), formulaLiquidDto.getFormulaCode(), formulaLiquidDto.getFormulaName(), formulaLiquidDto.getFormulaType(), formulaLiquidDto.getStockItems(), formulaLiquidDto.getStableAttr1(), formulaLiquidDto.getStableAttr2(), formulaLiquidDto.getLiquidLooking(), formulaLiquidDto.getPhValue(), formulaLiquidDto.getLightValue()));
                break;
            case OIL:
                FormulaOilDto formulaOilDto = (FormulaOilDto) formulaDto;
                formulaDataService.saveFormula(new FormulaOil(formulaOilDto.getFormulaId(), formulaOilDto.getFormulaCode(), formulaOilDto.getFormulaName(), formulaOilDto.getFormulaType(), formulaOilDto.getStockItems(), formulaOilDto.getStableAttr1(), formulaOilDto.getStableAttr2(), formulaOilDto.getOutLooking(), formulaOilDto.getFlashPoint(), formulaOilDto.getViscosity()));
                break;
        }
    }

    /**
     * 获得下一个测试配方单Id
     *
     * @return
     */
    @Override
    public String getNextTestId() {
        return UUID.randomUUID().toString();
    }
}
