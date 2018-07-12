package trapx00.lightx00.client.presentation.mainui;

import javafx.event.ActionEvent;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommodityUiController;
import trapx00.lightx00.client.presentation.commodityui.commoditySort.CommoditySortUiController;
import trapx00.lightx00.client.presentation.inventoryui.InventoryStaffHomeUiController;
import trapx00.lightx00.client.presentation.inventoryui.check.InventoryCheckUiController;
import trapx00.lightx00.client.presentation.inventoryui.check.InventoryPictureUiController;
import trapx00.lightx00.client.presentation.inventoryui.gift.InventoryGiftUiController;
import trapx00.lightx00.client.presentation.inventoryui.warning.InventoryWarningUiController;

public class InventoryStaffUiController extends FrameworkUiController {

    public void initialize() {
        // 必须有。调用基类初始化来初始化基类的元素。
        super.initialize();
        // 如果还需要初始化自己的元素请接着写。
    }

    public void onCommodityFunctionClicked(ActionEvent actionEvent) {
        /**
         * 调用父类切换界面方法实现切换界面。
         * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
         * @see #switchFunction(Class, String, boolean)
         */
        switchFunction(CommodityUiController.class, "商品管理",true);
    }

    public void onCommoditySortFunctionClicked(ActionEvent actionEvent) {
        /**
         * 调用父类切换界面方法实现切换界面。
         * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
         * @see #switchFunction(Class, String, boolean)
         */
        switchFunction(CommoditySortUiController.class, "商品分类管理",true);
    }

    public void onInventoryCheckFunctionClicked(ActionEvent actionEvent) {
        /**
         * 调用父类切换界面方法实现切换界面。
         * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
         * @see #switchFunction(Class, String, boolean)
         */
        switchFunction(InventoryCheckUiController.class, "库存查看",true);
    }

    public void onInventoryPictureFunctionClicked(ActionEvent actionEvent){
        switchFunction(InventoryPictureUiController.class,"库存快照",true);
    }

    public void onInventoryGiftFunctionClicked(ActionEvent actionEvent) {
        /**
         * 调用父类切换界面方法实现切换界面。
         * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
         * @see #switchFunction(Class, String, boolean)
         */
        switchFunction(InventoryGiftUiController.class, "制定库存赠送单",true);
    }

    public void onInventoryWarningFunctionClicked(ActionEvent actionEvent) {
        /**
         * 调用父类切换界面方法实现切换界面。
         * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
         * @see #switchFunction(Class, String, boolean)
         */
        switchFunction(InventoryWarningUiController.class, "库存监控制定单据",true);
    }

    @Override
    public void switchBackToHome() {
        switchFunction(InventoryStaffHomeUiController.class, "主界面",true);
    }
}
