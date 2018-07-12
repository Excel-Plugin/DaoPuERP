package trapx00.lightx00.client.test.presentationdriver.inventoryblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryCheckBlService;
import trapx00.lightx00.client.blservicestub.inventoryblservice.InventoryCheckBlServiceStub;

import java.io.File;
import java.util.Date;

public class InventoryCheckBlServiceDriver {

    InventoryCheckBlService service=new InventoryCheckBlServiceStub() ;
    File file=new File("");
    Date date=new Date();


    @Test
    public void export() throws Exception {
      //  assertEquals(ResultMessage.Success,service.export(new File("C:\\")));
    }

}