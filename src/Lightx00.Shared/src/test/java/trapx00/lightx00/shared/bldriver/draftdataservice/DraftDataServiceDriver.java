package trapx00.lightx00.shared.bldriver.draftdataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.draftdataservice.DraftDataService;
import trapx00.lightx00.shared.dataservicestub.draftdataservice.DraftDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.DraftQueryVo;

import static org.junit.Assert.assertEquals;

public class DraftDataServiceDriver {
    private DraftDataService service = new DraftDataServiceStub();
    @Test
    public void update() throws Exception {
        assertEquals("FXD-20171111-00001",service.query(new DraftQueryVo())[0].getDraftableId());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(ResultMessage.Success, service.delete(service.query(new DraftQueryVo())[0].getId()));
    }

}