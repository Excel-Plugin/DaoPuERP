package trapx00.lightx00.client.blservicestub.promotionblservice;

import trapx00.lightx00.client.blservice.promotionblservice.TotalPricePromotionBlService;
import trapx00.lightx00.client.vo.manager.promotion.TotalPricePromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.queryvo.promotion.TotalPricePromotionQueryVo;

import java.util.Date;

public class TotalPricePromotionBlServiceStub implements TotalPricePromotionBlService {
    @Override
    public ResultMessage submit(TotalPricePromotionVo promotion) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage saveAsDraft(TotalPricePromotionVo promotion) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage delete(String id) {
        return ResultMessage.Success;
    }

    @Override
    public TotalPricePromotionVo[] queryPromotion(TotalPricePromotionQueryVo query) {
        return new TotalPricePromotionVo[]{new TotalPricePromotionVo("0001",new Date(),new Date(), PromotionState.Waiting,200,1000,null)
        };
    }

    @Override
    public String getId() {
        return "0001";
    }
}
