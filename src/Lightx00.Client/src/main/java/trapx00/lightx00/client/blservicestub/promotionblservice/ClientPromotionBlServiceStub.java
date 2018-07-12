package trapx00.lightx00.client.blservicestub.promotionblservice;

import trapx00.lightx00.client.blservice.promotionblservice.ClientPromotionBlService;
import trapx00.lightx00.client.vo.manager.promotion.ClientPromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.queryvo.promotion.ClientPromotionQueryVo;

import java.util.Date;

public class ClientPromotionBlServiceStub implements ClientPromotionBlService {
    @Override
    public ResultMessage submit(ClientPromotionVo promotion) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage saveAsDraft(ClientPromotionVo promotion) {
        return ResultMessage.Success;
    }

    @Override
    public ResultMessage delete(String id) {
        return ResultMessage.Success;
    }

    @Override
    public ClientPromotionVo[] queryPromotion(ClientPromotionQueryVo query) {
        return new ClientPromotionVo[]{new ClientPromotionVo("0001",new Date(),new Date(), PromotionState.Waiting,1,200,null,0)};
    }

    @Override
    public String getId() {
        return "0001";
    }
}
