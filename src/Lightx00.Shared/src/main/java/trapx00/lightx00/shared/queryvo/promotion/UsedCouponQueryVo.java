package trapx00.lightx00.shared.queryvo.promotion;

import trapx00.lightx00.shared.po.manager.CouponPo;
import trapx00.lightx00.shared.queryvo.BaseQueryVo;

public class UsedCouponQueryVo extends BaseQueryVo<CouponPo, Integer, UsedCouponQueryVo> {
    public UsedCouponQueryVo() {

    }

    public UsedCouponQueryVo(BaseQueryVo another) {
        super(another);
    }
}
