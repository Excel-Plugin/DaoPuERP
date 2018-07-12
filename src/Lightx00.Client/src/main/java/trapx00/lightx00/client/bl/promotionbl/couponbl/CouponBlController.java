package trapx00.lightx00.client.bl.promotionbl.couponbl;

import trapx00.lightx00.client.blservice.promotionblservice.couponblservice.CouponBlService;
import trapx00.lightx00.client.datafactory.promotiondataservicefactory.coupondataservicefactory.CouponDataServiceFactory;
import trapx00.lightx00.client.vo.manager.CouponVo;
import trapx00.lightx00.shared.dataservice.promotiondataservice.coupondataservice.CouponDataService;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.CouponPo;
import trapx00.lightx00.shared.queryvo.promotion.UsedCouponQueryVo;

import java.rmi.RemoteException;
import java.util.Date;

public class CouponBlController implements CouponBlService, CouponInfo, SendCouponInfo, UseCouponInfo {
    private CouponDataService dataService = CouponDataServiceFactory.getService();
    /**
     * Add a used coupon.
     * @param usedCoupon used coupon
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(CouponVo usedCoupon) {
        try {
            return dataService.add(usedCoupon.toPo());
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Query the value of all unused coupons.
     * @param start start date
     * @param end end date
     * @return value of all unused coupons
     */
    public double queryUnusedCouponValue(Date start, Date end) {
        try {
            CouponPo[] pos = dataService.query(new UsedCouponQueryVo());
            double unusedValue = 0.0;
            for(CouponPo po:pos){
                if(compare(po.getDate(),start,end))
                    unusedValue+=po.getCouponPrice();
            }
            return unusedValue;
        } catch (RemoteException e) {
            throw new UncheckedRemoteException(e);
        }
    }

    /**
     * Send coupon.
     * 送出代金券，记正
     * @param couponPrice price of the coupon
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage sendCoupon(double couponPrice) {
        CouponVo couponVo = new CouponVo(couponPrice,new Date());
        return add(couponVo);
    }

    /**
     * Use coupon.
     * 用掉代金券，记负
     * @param couponPrice price of the coupon
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage useCoupon(double couponPrice) {
        CouponVo couponVo = new CouponVo(-couponPrice,new Date());
        return add(couponVo);
    }

    private boolean compare(Date now,Date start,Date end){
        return now.getTime()>=start.getTime() && now.getTime()<=end.getTime();
    }

}
