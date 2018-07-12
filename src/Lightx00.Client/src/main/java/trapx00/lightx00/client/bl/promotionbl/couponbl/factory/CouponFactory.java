package trapx00.lightx00.client.bl.promotionbl.couponbl.factory;

import trapx00.lightx00.client.bl.promotionbl.couponbl.CouponBlController;
import trapx00.lightx00.client.bl.promotionbl.couponbl.CouponInfo;
import trapx00.lightx00.client.bl.promotionbl.couponbl.SendCouponInfo;
import trapx00.lightx00.client.bl.promotionbl.couponbl.UseCouponInfo;

public class CouponFactory {
    private static CouponBlController controller = new CouponBlController();
    private static CouponInfo couponInfo = controller;
    private static SendCouponInfo sendCouponInfo = controller;
    private static UseCouponInfo useCouponInfo = controller;

    public static CouponBlController getController() {
        return controller;
    }
    public static CouponInfo getCouponInfo() {
        return couponInfo;
    }
    public static SendCouponInfo getSendCouponInfo() {
        return sendCouponInfo;
    }
    public static UseCouponInfo getUseCouponInfo() {
        return useCouponInfo;
    }
}
