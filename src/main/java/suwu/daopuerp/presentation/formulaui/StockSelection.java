package suwu.daopuerp.presentation.formulaui;

import suwu.daopuerp.dto.formula.StockItem;

import java.util.function.Consumer;

public interface StockSelection {
    void showStockAdd(Consumer<StockItem> callback);
}
