package trapx00.lightx00.client.bl.promotionbl.couponbl;

import trapx00.lightx00.shared.po.ResultMessage;

public interface SendCouponInfo {
    /**
     * Send coupon.
     * @param couponPrice price of the coupon
     * @return whether the operation is done successfully
     */
    ResultMessage sendCoupon(double couponPrice);
}
