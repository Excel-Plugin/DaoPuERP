package trapx00.lightx00.shared.bldriver.promotiondataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.promotiondataservice.TotalPricePromotionDataService;
import trapx00.lightx00.shared.dataservicestub.promotiondataservice.TotalPricePromotionDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.po.manager.promotion.TotalPricePromotionPo;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class TotalPricePromotionDataServiceDriver {
    private TotalPricePromotionDataService service = new TotalPricePromotionDataServiceStub();
    private TotalPricePromotionPo promotion = new TotalPricePromotionPo("ZJCXCL-20171112-00001",new Date(),new Date(), PromotionState.Waiting,200,1000,null);

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(promotion));
    }

    @Test
    public void queryPromotion() throws Exception {
        assertEquals("ZJCXCL-20171112-00001",promotion.getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,service.delete(promotion.getId()));
    }

}
