package trapx00.lightx00.client.blservicestub.promotionblservice;

import trapx00.lightx00.client.blservice.promotionblservice.ComSalePromotionBlService;
import trapx00.lightx00.client.vo.manager.promotion.ComSalePromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.queryvo.promotion.ComSalePromotionQueryVo;

import java.util.Date;

public class ComSalePromotionBlServiceStub implements ComSalePromotionBlService {
    @Override
    public ResultMessage submit(ComSalePromotionVo promotion) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage saveAsDraft(ComSalePromotionVo promotion) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage delete(String id) {
        return ResultMessage.Success;
    }

    @Override
    public ComSalePromotionVo[] queryPromotion(ComSalePromotionQueryVo query) {
        return new ComSalePromotionVo[]{new ComSalePromotionVo("0001",new Date(),new Date(), PromotionState.Waiting,new PromotionCommodity[0],200)};
    }

    @Override
    public String getId() {
        return "0001";
    }
}
