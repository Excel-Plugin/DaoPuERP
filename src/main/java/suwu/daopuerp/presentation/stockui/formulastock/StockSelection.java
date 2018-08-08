package suwu.daopuerp.presentation.stockui.formulastock;

import suwu.daopuerp.dto.stock.StockItem;

import java.util.function.Consumer;

public interface StockSelection {
    void showStockAdd(Consumer<StockItem> callback);
}
