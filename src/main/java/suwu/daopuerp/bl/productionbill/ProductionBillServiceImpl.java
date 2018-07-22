package suwu.daopuerp.bl.productionbill;

import suwu.daopuerp.blservice.productionbill.ProductionBillService;
import suwu.daopuerp.dataservice.factory.ProductionBillDataServiceFactory;
import suwu.daopuerp.dataservice.productionbill.ProductionBillDataService;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;
import suwu.daopuerp.dto.productionbill.ProductionBillLiquidDto;
import suwu.daopuerp.dto.productionbill.ProductionBillOilDto;
import suwu.daopuerp.entity.productionbill.ProductionBill;
import suwu.daopuerp.entity.productionbill.ProductionBillLiquid;
import suwu.daopuerp.entity.productionbill.ProductionBillOil;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.ArrayList;
import java.util.List;

public class ProductionBillServiceImpl implements ProductionBillService {
    private ProductionBillDataService productionBillDataService = ProductionBillDataServiceFactory.getProductionBillDataService();

    @Override
    public List<ProductionBillItem> getAllProductionBillItems() {
        List<ProductionBillItem> productionBillItems = productionBillDataService.getAllProductionBills().stream().collect(ArrayList::new, (list, item) -> list.add(new ProductionBillItem(item.getBillType(), item.getBillId(), item.getProductionName(), item.getBillDate(), item.getProductionType(), item.getProductionId())), ArrayList::addAll);
        return productionBillItems;
    }

    @Override
    public ProductionBillDto getProductionBillDtoById(String billId) throws IdDoesNotExistException {
        ProductionBill productionBill = productionBillDataService.getProductionBillById(billId);
        switch (productionBill.getBillType()) {
            case OIL:
                ProductionBillOil productionBillOil = (ProductionBillOil) productionBill;
                return new ProductionBillOilDto(productionBillOil.getBillId(), productionBillOil.getProductionDate(), productionBillOil.getProductionName(), productionBillOil.getBillDate(), productionBillOil.getClient(), productionBillOil.getProductionType(), productionBillOil.getMachineId(), productionBillOil.getProductionId(), productionBillOil.getTotalQuantity(), productionBillOil.getModifyRecord(), productionBillOil.getComment(), productionBillOil.getStableAttr1(), productionBillOil.getStableAttr2(), productionBillOil.getStockItems(), productionBillOil.getOutLooking(), productionBillOil.getFlashPoint(), productionBillOil.getViscosity());
            case LIQUID:
                ProductionBillLiquid productionBillLiquid = (ProductionBillLiquid) productionBill;
                return new ProductionBillLiquidDto(productionBillLiquid.getBillId(), productionBillLiquid.getProductionDate(), productionBillLiquid.getProductionName(), productionBillLiquid.getBillDate(), productionBillLiquid.getClient(), productionBillLiquid.getProductionType(), productionBillLiquid.getMachineId(), productionBillLiquid.getProductionId(), productionBillLiquid.getTotalQuantity(), productionBillLiquid.getModifyRecord(), productionBillLiquid.getComment(), productionBillLiquid.getStableAttr1(), productionBillLiquid.getStableAttr2(), productionBillStockItems, productionBillLiquid.getLiquidLooking(), productionBillLiquid.getPhValue(), productionBillLiquid.getLightValue());
        }
        throw new IdDoesNotExistException();
    }

    @Override
    public void submit(ProductionBillDto productionBillDto) {
        List<String> stockIds = productionBillDto.getProductionBillStockItems().stream().collect(ArrayList::new, (list, item) -> list.add(item.getStockId()), ArrayList::addAll);
        switch (productionBillDto.getBillType()) {
            case LIQUID:
                ProductionBillLiquidDto productionBillLiquidDto = (ProductionBillLiquidDto) productionBillDto;
                productionBillDataService.saveProductionBill(new ProductionBillLiquid(productionBillLiquidDto.getBillId(), productionBillLiquidDto.getProductionDate(), productionBillLiquidDto.getProductionName(), productionBillLiquidDto.getBillDate(), productionBillLiquidDto.getClient(), productionBillLiquidDto.getProductionType(), productionBillLiquidDto.getMachineId(), productionBillLiquidDto.getProductionId(), productionBillLiquidDto.getTotalQuantity(), productionBillLiquidDto.getModifyRecord(), productionBillLiquidDto.getComment(), productionBillLiquidDto.getStableAttr1(), productionBillLiquidDto.getStableAttr2(), stockIds, productionBillLiquidDto.getLiquidLooking(), productionBillLiquidDto.getPhValue(), productionBillLiquidDto.getLightValue()));
                break;
            case OIL:
                ProductionBillOilDto productionBillOilDto = (ProductionBillOilDto) productionBillDto;
                productionBillDataService.saveProductionBill(new ProductionBillOil(productionBillOilDto.getBillId(), productionBillOilDto.getProductionDate(), productionBillOilDto.getProductionName(), productionBillOilDto.getBillDate(), productionBillOilDto.getClient(), productionBillOilDto.getProductionType(), productionBillOilDto.getMachineId(), productionBillOilDto.getProductionId(), productionBillOilDto.getTotalQuantity(), productionBillOilDto.getModifyRecord(), productionBillOilDto.getComment(), productionBillOilDto.getStableAttr1(), productionBillOilDto.getStableAttr2(), stockIds, productionBillOilDto.getOutLooking(), productionBillOilDto.getFlashPoint(), productionBillOilDto.getViscosity()));
                break;
        }
    }

    @Override
    public void delete(String billId) throws IdDoesNotExistException {
        productionBillDataService.deleteProductionBill(billId);
    }
}
