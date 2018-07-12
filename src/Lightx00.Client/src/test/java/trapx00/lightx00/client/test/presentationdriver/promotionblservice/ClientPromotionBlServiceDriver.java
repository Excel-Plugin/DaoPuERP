package trapx00.lightx00.client.test.presentationdriver.promotionblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.promotionblservice.ClientPromotionBlService;
import trapx00.lightx00.client.blservicestub.promotionblservice.ClientPromotionBlServiceStub;
import trapx00.lightx00.client.vo.manager.promotion.ClientPromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ClientPromotionBlServiceDriver {
    private ClientPromotionBlService service = new ClientPromotionBlServiceStub();
    private ClientPromotionVo promotion = new ClientPromotionVo("0001",new Date(),new Date(), PromotionState.Waiting,1,200,null,0);
    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(promotion));
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success,service.saveAsDraft(promotion));
    }

    @Test
    public void queryPromotion() throws Exception {
        assertEquals("0001",promotion.getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,service.delete("0001"));
    }

}