package trapx00.lightx00.shared.bldriver.promotiondataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ComSalePromotionDataService;
import trapx00.lightx00.shared.dataservicestub.promotiondataservice.ComSalePromotionDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.ComSalePromotionPo;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class ComSalePromotionDataServiceDriver {
    private ComSalePromotionDataService service = new ComSalePromotionDataServiceStub();
    private ComSalePromotionPo promotion = new ComSalePromotionPo("SPJJCXCL-20171112-00001",new Date(),new Date(), PromotionState.Waiting,null,0);

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(promotion));
    }

   @Test
    public void queryPromotion() throws Exception {
        assertEquals("SPJJCXCL-20171112-00001",promotion.getId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success,service.delete(promotion.getId()));
    }

}
