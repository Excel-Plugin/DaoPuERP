package trapx00.lightx00.shared.bldriver.promotiondataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ClientPromotionDataService;
import trapx00.lightx00.shared.dataservicestub.promotiondataservice.ClientPromotionDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.ClientPromotionPo;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class ClientPromotionDataServiceDriver {
    private ClientPromotionDataService service = new ClientPromotionDataServiceStub();
    private ClientPromotionPo promotion = new ClientPromotionPo("KHCXCL-20171112-00001",new Date(),new Date(), PromotionState.Waiting,1,200,null,0);

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(promotion));
    }

   @Test
    public void queryPromotion() throws Exception {
        assertEquals("KHCXCL-20171112-00001",promotion.getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,service.delete(promotion.getId()));
    }

}
