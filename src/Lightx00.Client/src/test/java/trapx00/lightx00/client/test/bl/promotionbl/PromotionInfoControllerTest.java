package trapx00.lightx00.client.test.bl.promotionbl;

import org.junit.Test;
import trapx00.lightx00.client.bl.promotionbl.PromotionInfoController;
import trapx00.lightx00.client.bl.promotionbl.factory.PromotionInfoFactory;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PromotionInfoControllerTest {
    private PromotionInfoController controller = PromotionInfoFactory.getController();
    private SaleBillVo sale = new SaleBillVo("0001", new Date(), BillState.Approved, "123", null, null, 1, new CommodityItem[0], 100.0, 1000, 0, 0, " ", 1, "1", new CommodityItem[]{new CommodityItem("123", "1", "1",1,1,1,"")}, 10);

    @Test
    public void queryPromotion() throws Exception {
        assertEquals("0001", controller.queryPromotion(sale)[0].getId());
    }

}