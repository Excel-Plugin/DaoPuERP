package trapx00.lightx00.client.test.bl.promotionbl;

import org.junit.Test;
import trapx00.lightx00.client.bl.promotionbl.ComSalePromotionBlController;
import trapx00.lightx00.client.bl.promotionbl.factory.ComSalePromotionBlFactory;
import trapx00.lightx00.client.vo.manager.promotion.ComSalePromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ComSalePromotionBlControllerTest {
    private ComSalePromotionBlController controller = ComSalePromotionBlFactory.getController();
    private ComSalePromotionVo promotion = new ComSalePromotionVo("0001",new Date(),new Date(), PromotionState.Waiting,null,200);

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,controller.submit(promotion));
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success,controller.saveAsDraft(promotion));
    }

    @Test
    public void queryPromotion() throws Exception {
        assertEquals("0001",promotion.getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,controller.delete("0001"));
    }

    @Test
    public void getId() throws Exception {
        assertEquals("0001",controller.getId());
    }

    @Test
    public void deleteDraft() throws Exception {
        assertEquals(ResultMessage.Success,controller.deleteDraft("0001"));
    }

}