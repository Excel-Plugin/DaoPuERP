package trapx00.lightx00.shared.bldriver.financedataservice;

import org.junit.Test;
import trapx00.lightx00.shared.dataservice.financedataservice.InitialEstablishmentDataService;
import trapx00.lightx00.shared.dataservicestub.financedataservice.InitialEstablishmentDataServiceStub;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.financestaff.SystemSnapshotPo;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class InitialEstablishmentDataServiceDriver {
    private InitialEstablishmentDataService service = new InitialEstablishmentDataServiceStub();
    private final SystemSnapshotPo snapshot = new SystemSnapshotPo("123",new Date(), BillState.Draft,null,null,null, "123");
    @Test
    public void submit() throws Exception {
        //assertEquals(service.submit(snapshot), ResultMessage.Success);
        assertEquals(ResultMessage.Success, service.submit(snapshot));
    }

    @Test
    public void abandon() throws Exception {
        assertEquals(ResultMessage.Success, service.abandon(snapshot.getId()));
    }

}