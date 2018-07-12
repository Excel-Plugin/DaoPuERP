package trapx00.lightx00.client.vo.notification.others;

import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.EmployeeInfoFactory;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.notification.NotificationConverter;
import trapx00.lightx00.client.vo.notification.NotificationConverterClass;
import trapx00.lightx00.shared.po.notification.NotificationPo;
import trapx00.lightx00.shared.po.notification.NotificationType;

import java.util.Arrays;

@NotificationConverterClass(notificationType = NotificationType.Others)
public class OtherNotificationConverter implements NotificationConverter<OtherNotificationVo> {

    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();

    /**
     * Converts NotificationPo to NotificationVo.
     *
     * @param po NotificationPo
     * @return NotificationVo
     */
    @Override
    public OtherNotificationVo convertToVo(NotificationPo po) {
        return new OtherNotificationVo(
            po.getId(),
            po.getDate(),
            employeeInfo.queryById(po.getSenderId()),
            Arrays.stream(po.getReceiverIds()).map(x -> employeeInfo.queryById(x)).toArray(EmployeeVo[]::new),
            po.getContent()
        );
    }

    @Override
    public NotificationPo convertToPo(OtherNotificationVo vo) {
        return new NotificationPo(
            vo.getId(),
            vo.getDate(),
            vo.getSender().getId(),
            Arrays.stream(vo.getReceivers()).map(EmployeeVo::getId).toArray(String[]::new),
            vo.getType(),
            vo.getContent()
        );
    }
}
