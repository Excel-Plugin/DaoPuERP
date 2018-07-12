package trapx00.lightx00.shared.dataservice.promotiondataservice.coupondataservice;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.CouponPo;
import trapx00.lightx00.shared.queryvo.promotion.UsedCouponQueryVo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CouponDataService extends Remote {
    /**
     * Add a used coupon.
     * @param coupon used coupon
     * @return whether the operation is done successfully
     */
    ResultMessage add(CouponPo coupon) throws RemoteException;

    /**
     * Query all the used coupons.
     * @param query UsedCouponQueryVo
     * @return all used coupons
     * @throws RemoteException
     */
    CouponPo[] query(UsedCouponQueryVo query) throws RemoteException;
}
