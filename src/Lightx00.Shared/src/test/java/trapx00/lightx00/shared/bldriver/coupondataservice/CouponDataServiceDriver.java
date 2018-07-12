package trapx00.lightx00.shared.bldriver.coupondataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.promotiondataservice.coupondataservice.CouponDataService;
import trapx00.lightx00.shared.dataservicestub.promotiondataservice.coupondataservice.CouponDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.CouponPo;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CouponDataServiceDriver {
    private CouponDataService service = new CouponDataServiceStub();
    private CouponPo couponPo = new CouponPo(200,new Date());

    @Test
    public void add() throws Exception {
        assertEquals(ResultMessage.Success,service.add(couponPo));
    }
}