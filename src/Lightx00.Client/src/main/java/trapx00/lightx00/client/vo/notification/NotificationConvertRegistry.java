package trapx00.lightx00.client.vo.notification;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import trapx00.lightx00.shared.po.notification.NotificationPo;
import trapx00.lightx00.shared.po.notification.NotificationType;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class NotificationConvertRegistry {
    private static HashMap<NotificationType, NotificationConverter> registry = new HashMap<>();
    static {
        scanAndRegister();
    }

    private static void scanAndRegister() {
        FastClasspathScanner scanner = new FastClasspathScanner();
        scanner.matchClassesWithAnnotation(NotificationConverterClass.class, classWithAnnotation -> {
            try {
                register(classWithAnnotation.getAnnotation(NotificationConverterClass.class).notificationType(),
                    (NotificationConverter) classWithAnnotation.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }).scan();
    }

    /**
     * Registers a NotificationType-Converter pair into controller.
     * @param notificationType NotificationType
     * @param converter Converter for this NotificationType
     */
    public static void register(NotificationType notificationType, NotificationConverter converter) {
        registry.put(notificationType, converter);
    }

    /**
     * Converts NotificationPo to NotificationVo according to the controller.
     * @param notificationPo NotificationPo
     * @return NotificationVo
     */
    public static NotificationVo convertToVo(NotificationPo notificationPo) {
        return registry.get(notificationPo.getType()).convertToVo(notificationPo);
    }

    public static NotificationPo convertToPo(NotificationVo vo) {
        return registry.get(vo.getType()).convertToPo(vo);
    }
}
