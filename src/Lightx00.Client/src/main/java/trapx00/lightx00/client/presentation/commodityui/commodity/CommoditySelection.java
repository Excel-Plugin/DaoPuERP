package trapx00.lightx00.client.presentation.commodityui.commodity;

import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;

import java.util.function.Consumer;

public interface CommoditySelection {

    void showCommoditySelectDialog(Consumer<CommodityVo> callback);

    CommodityVo queryId(String id);
}
