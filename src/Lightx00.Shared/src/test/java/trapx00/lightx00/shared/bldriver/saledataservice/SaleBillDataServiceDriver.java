package trapx00.lightx00.shared.bldriver.saledataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.saledataservice.SaleBillDataService;
import trapx00.lightx00.shared.dataservicestub.saledataservice.SaleBillDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.salestaff.SaleBillPo;

import static org.junit.Assert.assertEquals;

public class SaleBillDataServiceDriver {

    SaleBillDataService service=new SaleBillDataServiceStub();
    SaleBillPo saleBillPo;

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(saleBillPo));
    }

}