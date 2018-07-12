package trapx00.lightx00.client.test.presentationdriver.promotionblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.promotionblservice.TotalPricePromotionBlService;
import trapx00.lightx00.client.blservicestub.promotionblservice.TotalPricePromotionBlServiceStub;
import trapx00.lightx00.client.vo.manager.promotion.TotalPricePromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TotalPricePromotionBlServiceDriver {
    private TotalPricePromotionBlService service = new TotalPricePromotionBlServiceStub();
    private TotalPricePromotionVo promotion = new TotalPricePromotionVo("0001",new Date(),new Date(), PromotionState.Waiting,200,1000,null);

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