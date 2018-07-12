package trapx00.lightx00.client.presentation.inventoryui.factory;

import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;
import trapx00.lightx00.client.presentation.inventoryui.mock.InventoryWarningUiControllerMock;
import trapx00.lightx00.client.presentation.inventoryui.warning.InventoryWarningUiController;

public class InventoryWarningUiFactory {
    private static InventoryWarningUiController inventoryWarningUiController = new InventoryWarningUiControllerMock();

    public static DraftContinueWritableUiController getInventoryWarningContinueWritable() {
        return inventoryWarningUiController;
    }

}
