package trapx00.lightx00.client.test.bl.approvalbl;

import org.junit.Test;
import trapx00.lightx00.client.bl.approvalbl.AuditBlController;
import trapx00.lightx00.client.bl.approvalbl.factory.AuditBlFactory;
import trapx00.lightx00.client.vo.financestaff.CashBillVo;
import trapx00.lightx00.client.vo.manager.AuditIdVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AuditBlControllerTest {
    private AuditBlController controller = AuditBlFactory.getController();
    private CashBillVo bill = new CashBillVo("XJFYD-20171112-00001",new Date(), BillState.WaitingForApproval,"0001",1,null);
    private AuditIdVo auditId = new AuditIdVo("XJFYD-20171112-00001",new Date());

    @Test
    public void reject() throws Exception {
        assertEquals(ResultMessage.Success,controller.reject(auditId));
    }

    @Test
    public void pass() throws Exception {
        assertEquals(ResultMessage.Success,controller.pass(auditId));
    }

    @Test
    public void query() throws Exception {
        assertEquals("XJFYD-20171112-00001",auditId.getId());
    }

}