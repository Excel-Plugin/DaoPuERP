package trapx00.lightx00.client.blservicestub.promotionblservice.couponblservice;

import trapx00.lightx00.client.blservice.promotionblservice.couponblservice.CouponBlService;
import trapx00.lightx00.client.vo.manager.CouponVo;
import trapx00.lightx00.shared.po.ResultMessage;

public class CouponBlServiceStub implements CouponBlService {
    /**
     * Add a coupon.
     * @param coupon couponVo
     * @return whether the operation is done successfully
     */
    public ResultMessage add(CouponVo coupon) {
        return ResultMessage.Success;
    }
}
