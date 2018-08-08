package suwu.daopuerp.bl.productionbill;

import suwu.daopuerp.blservice.productionbill.ProductionBillBlService;
import suwu.daopuerp.dataservice.factory.ProductionBillDataServiceFactory;
import suwu.daopuerp.dataservice.productionbill.ProductionBillDataService;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;
import suwu.daopuerp.dto.productionbill.ProductionBillLiquidDto;
import suwu.daopuerp.dto.productionbill.ProductionBillOilDto;
import suwu.daopuerp.entity.productionbill.ProductionBill;
import suwu.daopuerp.entity.productionbill.ProductionBillLiquid;
import suwu.daopuerp.entity.productionbill.ProductionBillOil;
import suwu.daopuerp.exception.IdDoesNotExistException;
import suwu.daopuerp.publicdata.BillType;
import suwu.daopuerp.util.FormatDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProductionBillBlServiceImpl implements ProductionBillBlService {
    private ProductionBillDataService productionBillDataService = ProductionBillDataServiceFactory.getProductionBillDataService();

    @Override
    public List<ProductionBillItem> getAllProductionBillItems() {
        List<ProductionBillItem> productionBillItems = productionBillDataService.getAllProductionBills().stream().collect(ArrayList::new, (list, item) -> list.add(new ProductionBillItem(item.getBillType(), item.getBillId(), item.getProductionName(), FormatDateTime.toShortDateString(item.getBillDate()), item.getProductionType(), item.getProductionId())), ArrayList::addAll);
        return productionBillItems;
    }

    @Override
    public ProductionBillDto getProductionBillDtoById(String billId) throws IdDoesNotExistException {
        ProductionBill productionBill = productionBillDataService.getProductionBillById(billId);

        switch (productionBill.getBillType()) {
            case OIL:
                ProductionBillOil productionBillOil = (ProductionBillOil) productionBill;
                return new ProductionBillOilDto(productionBillOil.getBillId(), productionBillOil.getProductionDate(), productionBillOil.getProductionName(), FormatDateTime.toShortDateString(productionBillOil.getBillDate()), productionBillOil.getClient(), productionBillOil.getProductionType(), productionBillOil.getMachineId(), productionBillOil.getProductionId(), productionBillOil.getTotalQuantity(), productionBillOil.getModifyRecord(), productionBillOil.getComment(), productionBillOil.getStableAttr1(), productionBillOil.getStableAttr2(), productionBillOil.getProductionBillStockItems(), productionBillOil.getOutLooking(), productionBillOil.getFlashPoint(), productionBillOil.getViscosity());
            case LIQUID:
                ProductionBillLiquid productionBillLiquid = (ProductionBillLiquid) productionBill;
                return new ProductionBillLiquidDto(productionBillLiquid.getBillId(), productionBillLiquid.getProductionDate(), productionBillLiquid.getProductionName(), FormatDateTime.toShortDateString(productionBillLiquid.getBillDate()), productionBillLiquid.getClient(), productionBillLiquid.getProductionType(), productionBillLiquid.getMachineId(), productionBillLiquid.getProductionId(), productionBillLiquid.getTotalQuantity(), productionBillLiquid.getModifyRecord(), productionBillLiquid.getComment(), productionBillLiquid.getStableAttr1(), productionBillLiquid.getStableAttr2(), productionBillLiquid.getProductionBillStockItems(), productionBillLiquid.getLiquidLooking(), productionBillLiquid.getPhValue(), productionBillLiquid.getLightValue());
        }
        throw new IdDoesNotExistException();
    }

    @Override
    public void submit(ProductionBillDto productionBillDto) {
        List<String> stockIds = productionBillDto.getProductionBillStockItems().stream().collect(ArrayList::new, (list, item) -> list.add(item.getStockCode()), ArrayList::addAll);
        switch (productionBillDto.getBillType()) {
            case LIQUID:
                ProductionBillLiquidDto productionBillLiquidDto = (ProductionBillLiquidDto) productionBillDto;
                productionBillDataService.saveProductionBill(new ProductionBillLiquid(productionBillLiquidDto.getBillId(), productionBillLiquidDto.getProductionDate(), productionBillLiquidDto.getProductionName(), FormatDateTime.fromShortDateString(productionBillLiquidDto.getBillDate()), productionBillLiquidDto.getClient(), productionBillLiquidDto.getProductionType(), productionBillLiquidDto.getMachineId(), productionBillLiquidDto.getProductionId(), productionBillLiquidDto.getTotalQuantity(), productionBillLiquidDto.getModifyRecord(), productionBillLiquidDto.getComment(), productionBillLiquidDto.getStableAttr1(), productionBillLiquidDto.getStableAttr2(), productionBillLiquidDto.getProductionBillStockItems(), productionBillLiquidDto.getLiquidLooking(), productionBillLiquidDto.getPhValue(), productionBillLiquidDto.getLightValue()));
                break;
            case OIL:
                ProductionBillOilDto productionBillOilDto = (ProductionBillOilDto) productionBillDto;
                productionBillDataService.saveProductionBill(new ProductionBillOil(productionBillOilDto.getBillId(), productionBillOilDto.getProductionDate(), productionBillOilDto.getProductionName(), FormatDateTime.fromShortDateString(productionBillOilDto.getBillDate()), productionBillOilDto.getClient(), productionBillOilDto.getProductionType(), productionBillOilDto.getMachineId(), productionBillOilDto.getProductionId(), productionBillOilDto.getTotalQuantity(), productionBillOilDto.getModifyRecord(), productionBillOilDto.getComment(), productionBillOilDto.getStableAttr1(), productionBillOilDto.getStableAttr2(), productionBillOilDto.getProductionBillStockItems(), productionBillOilDto.getOutLooking(), productionBillOilDto.getFlashPoint(), productionBillOilDto.getViscosity()));
                break;
        }
    }

    @Override
    public void delete(String billId) throws IdDoesNotExistException {
        productionBillDataService.deleteProductionBill(billId);
    }

    /**
     * 获得下一个Id
     *
     * @param billType
     * @return
     */
    @Override
    public String getNextId(BillType billType) {
        String prefix = FormatDateTime.currentDateStringForBill();
        StringBuilder result = new StringBuilder();
        result.append(prefix);
        result.append(billType.getCode());
        List<Integer> billIds = productionBillDataService.getAllProductionBills().stream().filter((productionBill -> productionBill.getBillId().contains(new String(result)))).collect(ArrayList::new, (list, item) -> list.add(Integer.parseInt(item.getBillId().split(new String(result))[1])), ArrayList::addAll);
        if (billIds.size() != 0) {
            Collections.reverse(billIds);
            result.append(billIds.get(0) + 1);
        } else {
            result.append(1);
        }
        return new String(result);
    }

    /**
     * 获得两个时间段之间的生产单
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<ProductionBill> getBillsBetween(Date startDate, Date endDate) {
        return productionBillDataService.getAllProductionBills().stream().filter((productionBill -> productionBill.getProductionDate().getTime() >= startDate.getTime() && productionBill.getProductionDate().getTime() <= endDate.getTime())).collect(Collectors.toList());
    }

    /**
     * 获得下一个测试单据
     *
     * @return
     */
    @Override
    public FormulaDto getNextTestBill(BillType billType) {
        String id = getNextId(billType);
        return new FormulaDto(billType, id, "", "", "", new ArrayList<>(), "", "");
    }

    /**
     * @param keyword
     * @return
     */
    @Override
    public ProductionBillItem[] query(String keyword) {
        List<ProductionBillItem> productionBillItems = productionBillDataService.queryForBillId(keyword).stream().collect(ArrayList::new, (productionBillItems1, productionBill) -> productionBillItems1.add(new ProductionBillItem(productionBill.getBillType(), productionBill.getBillId(), productionBill.getProductionName(), productionBill.getBillDate() + "", productionBill.getProductionType(), productionBill.getProductionId())), ArrayList::addAll);
        return productionBillItems.toArray(new ProductionBillItem[productionBillItems.size()]);
    }
}
