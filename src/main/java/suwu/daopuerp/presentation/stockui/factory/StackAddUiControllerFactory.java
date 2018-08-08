package suwu.daopuerp.presentation.stockui.factory;

import suwu.daopuerp.presentation.stockui.formulastock.StockAddUiController;

public class StackAddUiControllerFactory {
    private static StockAddUiController stackAddUiController = new StockAddUiController();

    public static StockAddUiController getStackAddUiController() {
        return stackAddUiController;
    }
}
