package trapx00.lightx00.client.bl.promotionbl.couponbl.mock;

import trapx00.lightx00.client.bl.promotionbl.couponbl.CouponBlController;
import trapx00.lightx00.client.vo.manager.CouponVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

public class CouponBlControllerMock extends CouponBlController {
    /**
     * Add a used coupon.
     * @param usedCoupon used coupon
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(CouponVo usedCoupon) {
        return ResultMessage.Success;
    }

    /**
     * Query the value of all unused coupons.
     * @param start start date
     * @param end end date
     * @return value of all unused coupons
     */
    public double queryUnusedCouponValue(Date start, Date end) {
        return 200;
    }

    /**
     * Send coupon.
     * @param couponPrice price of the coupon
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage sendCoupon(double couponPrice) {
        return ResultMessage.Success;
    }

    /**
     * Use coupon.
     * @param couponPrice price of the coupon
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage useCoupon(double couponPrice) {
        return ResultMessage.Success;
    }
}
