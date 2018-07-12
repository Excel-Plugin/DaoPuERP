package trapx00.lightx00.client.presentation.mainui;

import javafx.event.ActionEvent;
import trapx00.lightx00.client.presentation.approvalui.AuditUiController;
import trapx00.lightx00.client.presentation.approvalui.ManagerHomeUiController;
import trapx00.lightx00.client.presentation.financeui.SaleDetailUiController;
import trapx00.lightx00.client.presentation.financeui.TradeHistoryUiController;
import trapx00.lightx00.client.presentation.financeui.TradeSituationUiController;
import trapx00.lightx00.client.presentation.promotionui.PromotionManagementUiController;
import trapx00.lightx00.client.presentation.promotionui.detail.ClientPromotionUiController;
import trapx00.lightx00.client.presentation.promotionui.detail.ComSalePromotionUiController;
import trapx00.lightx00.client.presentation.promotionui.detail.TotalPricePromotionUiController;

public class ManagerUiController extends FrameworkUiController {

    public void initialize() {
        // 必须有。调用基类初始化来初始化基类的元素。
        super.initialize();
        // 如果还需要初始化自己的元素请接着写。
    }

    /**
     * 调用父类切换界面方法实现切换界面。
     * 第一个参数是功能Controller（需要实现ExternalLoadableUiController），第二个是标题文字。
     * @see #switchFunction(Class, String, boolean)
     */
    public void onAuditFunctionClicked(ActionEvent actionEvent) {
        switchFunction(AuditUiController.class, "审批单据", true);
    }

    public void onClientPromotionFunctionClicked(ActionEvent actionEvent) {
        switchFunction(ClientPromotionUiController.class, "制定客户类促销策略",true);
    }



    public void onComSalePromotionFunctionClicked(ActionEvent actionEvent) {
        switchFunction(ComSalePromotionUiController.class, "制定组合商品促销策略", true);
    }

    public void onTotalPricePromotionFunctionClicked(ActionEvent actionEvent) {
        switchFunction(TotalPricePromotionUiController.class, "制定满额类促销策略", true);

    }

    public void onPromotionManagementFunctionClicked(ActionEvent actionEvent) {
        switchFunction(PromotionManagementUiController.class, "管理促销策略", true);

    }

   public void onSaleDetailFunctionClicked(ActionEvent actionEvent) {
        switchFunction(SaleDetailUiController.class, "查看销售明细表", true);

    }

    public void onTradeSituationFunctionClicked(ActionEvent actionEvent) {
        switchFunction(TradeSituationUiController.class, "查看经营情况表", true);

    }

    public void onTradeHistoryFunctionClicked(ActionEvent actionEvent) {
        switchFunction(TradeHistoryUiController.class, "查看经营历程表", true);

    }


    /*
    * 增加一个HomeUiController后，重写这个方法做到退回主界面。
    */
    @Override
    public void switchBackToHome() {
        switchFunction(ManagerHomeUiController.class, "主界面",true);
    }


}
