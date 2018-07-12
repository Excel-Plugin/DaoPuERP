package trapx00.lightx00.shared.dataservicestub.promotiondataservice.coupondataservice;

import trapx00.lightx00.shared.dataservice.promotiondataservice.coupondataservice.CouponDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.CouponPo;
import trapx00.lightx00.shared.queryvo.promotion.UsedCouponQueryVo;

import java.rmi.RemoteException;
import java.util.Date;

public class CouponDataServiceStub implements CouponDataService {
    @Override
    public ResultMessage add(CouponPo usedCoupon) throws RemoteException {
        return ResultMessage.Success;
    }

    @Override
    public CouponPo[] query(UsedCouponQueryVo query) throws RemoteException {
        return new CouponPo[]{
                new CouponPo(200,new Date())
        };
    }
}
