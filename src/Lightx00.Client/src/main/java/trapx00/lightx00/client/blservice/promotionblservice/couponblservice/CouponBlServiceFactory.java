package trapx00.lightx00.client.blservice.promotionblservice.couponblservice;

import trapx00.lightx00.client.bl.promotionbl.couponbl.factory.CouponFactory;

public class CouponBlServiceFactory {
    public static CouponBlService getInstance() {
        return CouponFactory.getController();
    }
}
