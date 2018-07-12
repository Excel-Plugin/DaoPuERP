package trapx00.lightx00.client.vo.notification.billapproval;

import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.EmployeeInfoFactory;
import trapx00.lightx00.client.bl.financebl.BillInfo;
import trapx00.lightx00.client.bl.financebl.factory.BillInfoBlFactory;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.notification.NotificationConverter;
import trapx00.lightx00.client.vo.notification.NotificationConverterClass;
import trapx00.lightx00.shared.po.notification.NotificationPo;
import trapx00.lightx00.shared.po.notification.NotificationType;

import java.util.Arrays;

@NotificationConverterClass(notificationType = NotificationType.BillApproval)
public class BillApprovalNotificationConverter implements NotificationConverter<BillApprovalNotificationVo> {

    private BillInfo billInfo = BillInfoBlFactory.getBillInfo();
    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();

    /**
     * Converts NotificationPo to NotificationVo.
     *
     * @param po NotificationPo
     * @return NotificationVo
     */
    @Override
    public BillApprovalNotificationVo convertToVo(NotificationPo po) {
        return new BillApprovalNotificationVo(
            po.getId(),
            po.getDate(),
            employeeInfo.queryById(po.getSenderId()),
            Arrays.stream(po.getReceiverIds()).map(x -> employeeInfo.queryById(x)).toArray(EmployeeVo[]::new),
            billInfo.queryBill(po.getContent())
        );
    }

    @Override
    public NotificationPo convertToPo(BillApprovalNotificationVo vo) {
        return new NotificationPo(
            vo.getId(),
            vo.getDate(),
            vo.getSender().getId(),
            Arrays.stream(vo.getReceivers()).map(EmployeeVo::getId).toArray(String[]::new),
            vo.getType(),
            vo.getBill().getId()
        );
    }
}
