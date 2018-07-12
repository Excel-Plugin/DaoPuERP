package trapx00.lightx00.client.presentation.promotionui;

import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;

import java.util.function.Consumer;

public interface PromotionSelection {
    void showEmployeeSelectDialog(Consumer<PromotionVoBase> callback,SaleBillVo bill);
}
