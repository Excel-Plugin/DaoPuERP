package trapx00.lightx00.client.test.bl.notificationbl;

import org.junit.Test;
import trapx00.lightx00.client.bl.notificationbl.NotificationBlController;
import trapx00.lightx00.client.bl.notificationbl.factory.NotificationBlFactory;
import trapx00.lightx00.client.datafactory.notificationdataservicefactory.NotificationDataServiceFactory;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.admin.AdminVo;
import trapx00.lightx00.client.vo.financestaff.CashBillVo;
import trapx00.lightx00.client.vo.financestaff.FinanceStaffVo;
import trapx00.lightx00.client.vo.notification.NotificationConvertRegistry;
import trapx00.lightx00.client.vo.notification.billapproval.BillApprovalNotificationVo;
import trapx00.lightx00.client.vo.notification.others.OtherNotificationVo;
import trapx00.lightx00.shared.dataservice.notificationdataservice.NotificationDataService;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.po.financestaff.CashBillItem;
import trapx00.lightx00.shared.queryvo.NotificationQueryVo;
import trapx00.lightx00.shared.util.BillHelper;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class NotificationBlControllerTest {
    private NotificationBlController controller = NotificationBlFactory.getController();
    private NotificationDataService dataService = NotificationDataServiceFactory.getService();
    private EmployeeVo sender = new AdminVo("10002","管理员",new Date(),"123456", EmployeeState.Active);
    private EmployeeVo[] receivers = new EmployeeVo[]{new FinanceStaffVo("10001","财务经理",new Date(),"123456", EmployeeState.Active,true), sender};
    private CashBillVo cashBill = new CashBillVo(String.format("XJFKD-%s-00001", BillHelper.currentDateStringForBill()),
        new Date(), BillState.Draft,"123",1,
        new CashBillItem[] {
        new CashBillItem("123",1,"123")
    });
    private BillApprovalNotificationVo commonUsedBillNotification = new BillApprovalNotificationVo(9,new Date(),sender,receivers,cashBill);
    private OtherNotificationVo otherNotificationVo = new OtherNotificationVo(10,new Date(), sender, receivers, "other");

    @Test
    public void addNotification() throws Exception {
        long previous = dataService.query(new NotificationQueryVo()).length;
        controller.addNotification(commonUsedBillNotification);
        int id = dataService.query(new NotificationQueryVo().eq("senderId", sender.getId()))[0].getId();
        try {
            assertEquals(previous + 1, dataService.query(new NotificationQueryVo()).length);
        } finally {
            dataService.acknowledge(id);
        }

    }

    @Test
    public void acknowledge() throws Exception {
        dataService.add(NotificationConvertRegistry.convertToPo(otherNotificationVo));
        int id = dataService.query(new NotificationQueryVo().eq("senderId", sender.getId()))[0].getId();
        otherNotificationVo.setId(id);
        controller.acknowledge(otherNotificationVo);
        try {
            assertEquals(0, dataService.query(new NotificationQueryVo().idEq(9)).length);
        } finally {
            try {
                dataService.acknowledge(id);
            } catch (Exception ignored) { }

        }
    }

    @Test
    public void abandon() throws Exception {
        dataService.add(NotificationConvertRegistry.convertToPo(otherNotificationVo));
        int id = dataService.query(new NotificationQueryVo().eq("senderId", sender.getId()))[0].getId();
        otherNotificationVo.setId(id);
        controller.abandon(otherNotificationVo);

        try {
            assertEquals(0, dataService.query(new NotificationQueryVo().idEq(10)).length);
        } finally {
            try {
                dataService.acknowledge(id);
            } catch (Exception ignored) { }
        }
    }

}