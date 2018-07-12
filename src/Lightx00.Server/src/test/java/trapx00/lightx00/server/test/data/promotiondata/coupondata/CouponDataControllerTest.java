package trapx00.lightx00.server.test.data.promotiondata.coupondata;

import com.j256.ormlite.dao.Dao;
import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.server.data.promotiondata.coupondata.factory.CouponDataDaoFactory;
import trapx00.lightx00.server.data.promotiondata.coupondata.factory.CouponDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.promotiondataservice.coupondataservice.CouponDataService;
import trapx00.lightx00.shared.po.manager.CouponPo;
import trapx00.lightx00.shared.queryvo.promotion.UsedCouponQueryVo;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CouponDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Dao<CouponPo, Integer> dao = CouponDataDaoFactory.getUsedCouponDao();
    private CouponDataService service = CouponDataFactory.getService();
    private final CouponPo usedCoupon = new CouponPo(200,new Date());

    @Before
    public void setUp() throws Exception {
        dao.deleteById(usedCoupon.getId());
    }
    @Test
    public void add() throws Exception {
        try {
            service.add(usedCoupon);
            assertTrue(dao.idExists(usedCoupon.getId()));
        } finally {
            dao.deleteById(usedCoupon.getId());
        }
    }

    @Test
    public void query() throws Exception {
        service.add(usedCoupon);
        assertEquals(1, service.query(new UsedCouponQueryVo().eq("id",usedCoupon.getId())).length);
    }
}