package trapx00.lightx00.client.presentation.inventoryui.gift;

import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;

import java.util.List;
import java.util.function.Consumer;

public interface InventoryGiftSelection {

    void showInventoryGiftSelectDialog(Consumer<List<InventoryGiftVo>> callback);

    InventoryGiftVo queryId(String id);
}
