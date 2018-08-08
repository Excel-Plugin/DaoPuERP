package suwu.daopuerp.presentation.stockui.productionstock;

import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.dto.stock.StockItem;

import java.util.function.Consumer;

public interface ProductionBillStockItemSelection {
    void showStockAdd(Consumer<ProductionBillStockItem> callback);
}
