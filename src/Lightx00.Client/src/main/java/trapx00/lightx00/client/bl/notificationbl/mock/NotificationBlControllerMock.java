package trapx00.lightx00.client.bl.notificationbl.mock;

import trapx00.lightx00.client.bl.notificationbl.NotificationBlController;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.financestaff.FinanceStaffVo;
import trapx00.lightx00.client.vo.financestaff.PaymentBillVo;
import trapx00.lightx00.client.vo.notification.NotificationVo;
import trapx00.lightx00.client.vo.notification.billapproval.BillApprovalNotificationVo;
import trapx00.lightx00.client.vo.notification.others.OtherNotificationVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.po.financestaff.Transcation;

import java.util.Date;

public class NotificationBlControllerMock extends NotificationBlController {
    /**
     * Adds a notification.
     *
     * @param notification notification to be added
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage addNotification(NotificationVo notification) {
        return ResultMessage.Success;
    }

    /**
     * Updates current user's notifications
     *
     * @return current users's notifications
     */
    public NotificationVo[] update() {
        EmployeeVo employeeVo = new FinanceStaffVo("10001","财务经理",new Date(),"123456", EmployeeState.Active,true);
        return new NotificationVo[] {
                new OtherNotificationVo(1,new Date(), employeeVo, new EmployeeVo[] {employeeVo}, "test"),
                new BillApprovalNotificationVo(2,new Date(),employeeVo, new EmployeeVo[] {employeeVo},
                        new PaymentBillVo("123",new Date(),
                                BillState.Approved, "123", "123",new Transcation[]{ new Transcation(1,1,"123")},1))};
    }

    /**
     * Acknowledges a notification. For a BillApprovalNotification with a bill that is approved of, it also activates the bill.
     *
     * @param notification notification
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage acknowledge(NotificationVo notification) {
        return ResultMessage.Success;
    }

    /**
     * Abandons a bill.
     *
     * @param notification notification with a bill
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage abandon(NotificationVo notification) {
        return ResultMessage.Success;
    }
}
