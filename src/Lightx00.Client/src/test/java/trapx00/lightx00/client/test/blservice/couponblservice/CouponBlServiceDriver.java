package trapx00.lightx00.client.test.blservice.couponblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.promotionblservice.couponblservice.CouponBlService;
import trapx00.lightx00.client.blservicestub.promotionblservice.couponblservice.CouponBlServiceStub;
import trapx00.lightx00.client.vo.manager.CouponVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CouponBlServiceDriver {
    private CouponBlService service = new CouponBlServiceStub();
    private CouponVo coupon = new CouponVo(200,new Date());


    @Test
    public void add() {
        assertEquals(ResultMessage.Success,service.add(coupon));
    }
}