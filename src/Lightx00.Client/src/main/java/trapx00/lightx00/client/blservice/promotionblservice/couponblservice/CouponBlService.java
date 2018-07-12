package trapx00.lightx00.client.blservice.promotionblservice.couponblservice;

import trapx00.lightx00.client.vo.manager.CouponVo;
import trapx00.lightx00.shared.po.ResultMessage;

public interface CouponBlService {
    /**
     * Add a used coupon.
     * @param usedCoupon used coupon
     * @return whether the operation is done successfully
     */
    ResultMessage add(CouponVo usedCoupon);

}
