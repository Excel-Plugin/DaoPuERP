package trapx00.lightx00.client.presentation.inventoryui.factory;

import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;
import trapx00.lightx00.client.presentation.inventoryui.gift.InventoryGiftSelectingDialog;
import trapx00.lightx00.client.presentation.inventoryui.gift.InventoryGiftSelection;
import trapx00.lightx00.client.presentation.inventoryui.gift.InventoryGiftUiController;
import trapx00.lightx00.client.presentation.inventoryui.mock.InventoryGiftUiControllerMock;

public class InventoryGiftUiFactory {
    private static InventoryGiftUiController inventoryGiftUiController = new InventoryGiftUiControllerMock();

    public static DraftContinueWritableUiController getInventoryGiftContinueWritable() {
        return inventoryGiftUiController;
    }

    public static InventoryGiftSelection getInventoryGiftSelectionUi(){
        return new InventoryGiftSelectingDialog();
    }

}
