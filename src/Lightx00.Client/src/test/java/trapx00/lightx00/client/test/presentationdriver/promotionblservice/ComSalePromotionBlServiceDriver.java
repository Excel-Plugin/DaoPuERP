package trapx00.lightx00.client.test.presentationdriver.promotionblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.promotionblservice.ComSalePromotionBlService;
import trapx00.lightx00.client.blservicestub.promotionblservice.ComSalePromotionBlServiceStub;
import trapx00.lightx00.client.vo.manager.promotion.ComSalePromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ComSalePromotionBlServiceDriver {
    private ComSalePromotionBlService service = new ComSalePromotionBlServiceStub();
    private ComSalePromotionVo promotion = new ComSalePromotionVo("0001",new Date(),new Date(), PromotionState.Waiting,new PromotionCommodity[0],200);

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