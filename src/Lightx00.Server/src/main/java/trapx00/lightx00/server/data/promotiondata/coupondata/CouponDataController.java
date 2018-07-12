package trapx00.lightx00.server.data.promotiondata.coupondata;

import com.j256.ormlite.dao.Dao;
import trapx00.lightx00.server.data.promotiondata.coupondata.factory.CouponDataDaoFactory;
import trapx00.lightx00.shared.dataservice.promotiondataservice.coupondataservice.CouponDataService;
import trapx00.lightx00.shared.exception.database.DbSqlException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.CouponPo;
import trapx00.lightx00.shared.queryvo.promotion.UsedCouponQueryVo;

import java.rmi.RemoteException;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class CouponDataController extends UnicastRemoteObject implements CouponDataService {
    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     * <p>
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since JDK1.1
     */
    public CouponDataController() throws RemoteException {
    }

    private Dao<CouponPo, Integer> dao = CouponDataDaoFactory.getUsedCouponDao();
    /**
     * Add a used coupon.
     * @param usedCoupon used coupon
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage add(CouponPo usedCoupon) {
        assertExists(usedCoupon.getId());
        try {
            dao.create(usedCoupon);
            return ResultMessage.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    /**
     * Query all the used coupons.
     * @param query UsedCouponQueryVo
     * @return all used coupons
     */
    @Override
    public CouponPo[] query(UsedCouponQueryVo query) {
        try {
            List<CouponPo> results = dao.query(query.prepareQuery(dao));
            return results.toArray(new CouponPo[results.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

    private void assertExists(int id) {
        try {
            CouponPo couponPo = dao.queryForId(id);
            boolean actualExists = couponPo != null;
            if (actualExists) {
                throw new IdExistsException(String.valueOf(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbSqlException(e);
        }
    }

}
