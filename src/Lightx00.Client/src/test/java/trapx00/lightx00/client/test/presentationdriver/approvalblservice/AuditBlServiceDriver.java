package trapx00.lightx00.client.test.presentationdriver.approvalblservice;

import org.junit.Test;
import trapx00.lightx00.client.blservice.approvalblservice.AuditBlService;
import trapx00.lightx00.client.blservicestub.approvalblservice.AuditBlServiceStub;
import trapx00.lightx00.client.vo.manager.AuditIdVo;
import trapx00.lightx00.shared.po.ResultMessage;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AuditBlServiceDriver {
    private AuditBlService service = new AuditBlServiceStub();
    private AuditIdVo auditId = new AuditIdVo("XJFYD-20171112-00001", new Date());

    @Test
    public void query() throws Exception {
        assertEquals("XJFYD-20171112-00001",auditId.getId());
    }

    @Test
    public void reject() throws Exception {
        assertEquals(ResultMessage.Success,service.reject(auditId));
    }

    @Test
    public void pass() throws Exception {
        assertEquals(ResultMessage.Success,service.pass(auditId));
    }

}