package trapx00.lightx00.client.blservicestub.notificationblservice;

import trapx00.lightx00.client.blservice.notificationblservice.NotificationBlService;
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


public class NotificationBlServiceStub implements NotificationBlService {
    @Override
    public NotificationVo[] update() {
        EmployeeVo employeeVo = new FinanceStaffVo("10001","财务经理",new Date(),"123456", EmployeeState.Active,true);
        return new NotificationVo[] {
                new OtherNotificationVo(1,new Date(), employeeVo, new EmployeeVo[] { employeeVo }, "test"),
                new BillApprovalNotificationVo(1,new Date(),employeeVo, new EmployeeVo[] { employeeVo },

                        new PaymentBillVo("123",new Date(),
                                BillState.Approved, "123", "123",new Transcation[]{ new Transcation(1,1,"123")},0))};
    }

    @Override
    public ResultMessage acknowledge(NotificationVo notification) {
        return ResultMessage.Success;
    }



    @Override
    public ResultMessage abandon(NotificationVo notification) {
        return ResultMessage.Success;
    }


}
