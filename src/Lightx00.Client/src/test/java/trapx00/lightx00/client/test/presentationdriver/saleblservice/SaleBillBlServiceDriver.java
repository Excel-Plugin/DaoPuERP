package trapx00.lightx00.client.test.presentationdriver.saleblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.saleblservice.SaleBillBlService;
import trapx00.lightx00.client.blservice.saleblservice.SaleBillBlServiceFactory;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.shared.po.ResultMessage;

import static org.junit.Assert.assertEquals;

public class SaleBillBlServiceDriver {

    SaleBillBlService service= SaleBillBlServiceFactory.getInstance();
    SaleBillVo saleBillVo;

    @Test
    public void submit() throws Exception {
        assertEquals(ResultMessage.Success,service.submit(saleBillVo));
    }

    @Test
    public void saveAsDraft() throws Exception {
        assertEquals(ResultMessage.Success,service.saveAsDraft(saleBillVo));
    }

    @Test
    public void getId() throws Exception{
        assertEquals("0",service.getId());
    }

    @Test
    public void querySaleBill() throws Exception{
        assertEquals("0",service.querySaleBill(null)[0].getId());
    }

}