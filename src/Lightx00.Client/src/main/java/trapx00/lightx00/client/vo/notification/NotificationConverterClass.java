package trapx00.lightx00.client.vo.notification;

import trapx00.lightx00.shared.po.notification.NotificationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NotificationConverterClass {
    NotificationType notificationType();

}
