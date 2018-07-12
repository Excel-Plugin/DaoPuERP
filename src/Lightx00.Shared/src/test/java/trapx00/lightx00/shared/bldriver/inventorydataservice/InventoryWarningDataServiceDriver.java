package trapx00.lightx00.shared.bldriver.inventorydataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.inventorydataservice.InventoryWarningDataService;
import trapx00.lightx00.shared.dataservicestub.inventorydataservice.InventoryWarningDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.CommodityPo;
import trapx00.lightx00.shared.po.inventorystaff.InventoryDetailBillPo;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class InventoryWarningDataServiceDriver {
    InventoryWarningDataService service=new InventoryWarningDataServiceStub();
    CommodityPo commodityPo=null;
    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(new InventoryDetailBillPo("S0001",new Date(), BillState.Approved,null,null, "!23")));
    }

    @Test
    public void modify() throws Exception {
        assertEquals(ResultMessage.Success,service.modify("C0001",100));

    }


}