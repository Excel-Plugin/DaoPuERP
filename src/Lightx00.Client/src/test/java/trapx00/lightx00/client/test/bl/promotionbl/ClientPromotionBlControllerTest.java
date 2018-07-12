package trapx00.lightx00.client.test.bl.promotionbl;

import org.junit.Test;
import trapx00.lightx00.client.bl.promotionbl.ClientPromotionBlController;
import trapx00.lightx00.client.bl.promotionbl.factory.ClientPromotionBlFactory;
import trapx00.lightx00.client.vo.manager.promotion.ClientPromotionVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ClientPromotionBlControllerTest {
    private ClientPromotionBlController controller = ClientPromotionBlFactory.getController();
    private ClientPromotionVo promotion = new ClientPromotionVo("0001",new Date(),new Date(), PromotionState.Waiting,1,200,null,0);

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