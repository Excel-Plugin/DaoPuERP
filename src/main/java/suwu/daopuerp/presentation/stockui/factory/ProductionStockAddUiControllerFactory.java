package suwu.daopuerp.presentation.stockui.factory;

import suwu.daopuerp.presentation.stockui.productionstock.ProductionBillStockItemAddUiController;

public class ProductionStockAddUiControllerFactory {
    private static ProductionBillStockItemAddUiController productionStockAddUiController = new ProductionBillStockItemAddUiController();

    public static ProductionBillStockItemAddUiController getProductionStockAddUiController() {
        return productionStockAddUiController;
    }
}
