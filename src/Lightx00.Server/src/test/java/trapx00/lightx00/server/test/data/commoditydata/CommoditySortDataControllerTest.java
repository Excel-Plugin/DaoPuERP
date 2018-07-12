package trapx00.lightx00.server.test.data.commoditydata;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import org.junit.Test;
import trapx00.lightx00.server.data.commoditydata.factory.CommodityDataDaoFactory;
import trapx00.lightx00.server.data.commoditydata.factory.CommoditySortDataFactory;
import trapx00.lightx00.server.data.util.db.BaseDatabaseFactory;
import trapx00.lightx00.shared.dataservice.commoditydataservice.CommoditySortDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.inventorystaff.CommoditySortPo;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CommoditySortDataControllerTest {
    static {
        try {
            BaseDatabaseFactory.initTest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Dao<CommoditySortPo, String> dao = CommodityDataDaoFactory.getCommoditySortDao();
    private CommoditySortDataService service= CommoditySortDataFactory.getService();
    private  CommoditySortPo commoditySortPo = new CommoditySortPo("PRO-0001","Led",0,null,null);


    private void resetTable() throws Exception {
        TableUtils.dropTable(dao.getConnectionSource(),CommoditySortPo.class,true);
        TableUtils.createTable(dao.getConnectionSource(), CommoditySortPo.class);
    }

    @Test
    public void add() throws Exception {
       CommoditySortPo[] commoditySortPos=service.display();
       for(CommoditySortPo commoditySortPo:commoditySortPos)
           System.out.println(commoditySortPo.getName());
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success,service.modify(commoditySortPo));
        resetTable();
    }

    @Test
    public void query() throws Exception {
        dao.create(commoditySortPo);
        assertEquals(1, service.query(new CommoditySortQueryVo().eq("id","PRO-0001")).length);
        resetTable();
    }

    @Test
    public void delete() throws Exception {
        dao.create(commoditySortPo);
        String id = dao.extractId(commoditySortPo);
        try {
            service.delete(commoditySortPo);
            assertNull(dao.queryForId(id));
        } finally {
            dao.deleteById(id);
        }
    }

    @Test
    public void display() throws Exception {
        dao.create(commoditySortPo);
        assertEquals("PRO-0001",service.display()[0].getId());
        resetTable();
    }

  //  @Test
  //  public void dispalyChildSort() throws Exception {
  //      dao.create(commoditySortPo1);
  //      assertEquals("PRO-0002",service.dispaly(commoditySortPo)[0].getId());
   //     dao.delete(commoditySortPo1);
  //  }

}