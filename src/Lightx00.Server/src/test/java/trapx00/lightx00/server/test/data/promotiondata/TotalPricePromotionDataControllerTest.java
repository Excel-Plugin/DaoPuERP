package trapx00.lightx00.server.test.data.promotiondata;

import com.j256.ormlite.dao.Dao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import trapx00.lightx00.server.data.promotiondata.factory.PromotionDataDaoFactory;
import trapx00.lightx00.server.data.promotiondata.factory.TotalPricePromotionDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.promotiondataservice.TotalPricePromotionDataService;
import trapx00.lightx00.shared.exception.database.PromotionInvalidStateException;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.po.manager.promotion.TotalPricePromotionPo;
import trapx00.lightx00.shared.queryvo.promotion.TotalPricePromotionQueryVo;
import trapx00.lightx00.shared.util.BillHelper;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TotalPricePromotionDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Dao<TotalPricePromotionPo, String> dao = PromotionDataDaoFactory.getTotalPricePromotionDao();
    private TotalPricePromotionDataService service = TotalPricePromotionDataFactory.getService();
    private final TotalPricePromotionPo promotion = new TotalPricePromotionPo("ZJCXCL-20171112-00001",new Date(),new Date(), PromotionState.Draft,200,1000,null);

    @Before
    public void setUp() throws Exception {
        dao.deleteById(promotion.getId());
    }

    @Test
    public void submitOne() throws Exception {
        try {
            service.submit(promotion);
            assertTrue(dao.idExists(promotion.getId()));
        } finally {
            dao.deleteById(promotion.getId());
        }

    }

    @Test
    public void deleteWaitingPromotion() throws Exception {
        try {
            promotion.setState(PromotionState.Waiting);
            dao.create(promotion);
            service.delete(promotion.getId());
            assertEquals(PromotionState.Abandoned, dao.queryForEq("id", promotion.getId()).get(0).getState());
        } finally {
            dao.deleteById(promotion.getId());
        }
    }

    @Test (expected = PromotionInvalidStateException.class)
    public void deleteActivePromotion() throws Exception {
        try {
            promotion.setState(PromotionState.Active);
            dao.create(promotion);
            service.delete(promotion.getId());
        } finally {
            dao.deleteById(promotion.getId());
        }
    }

    @Test
    public void deleteOverduePromotion() throws Exception {
        try {
            promotion.setState(PromotionState.Overdue);
            dao.create(promotion);
            service.delete(promotion.getId());
            assertEquals(PromotionState.Abandoned, dao.queryForEq("id", promotion.getId()).get(0).getState());
        } finally {
            dao.deleteById(promotion.getId());
        }
    }

    @Test
    public void query() throws Exception {
        try {
        dao.create(promotion);
        Assert.assertEquals(1, service.queryPromotion(new TotalPricePromotionQueryVo().eq("id",promotion.getId())).length);
        Assert.assertEquals(0, service.queryPromotion(new TotalPricePromotionQueryVo().eq("id","12")).length);
        } finally {
            dao.deleteById(promotion.getId());
        }
    }

    @Test
    public void getId() throws Exception {
        try {
            promotion.setId(String.format("KHCXCL-%s-00001", BillHelper.currentDateStringForBill()));
            service.submit(promotion);
            assertEquals("00002", service.getId().split("-")[2]);
        } finally {
            dao.deleteById(promotion.getId());
        }
    }

}