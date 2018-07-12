package trapx00.lightx00.client.bl.notificationbl;

import trapx00.lightx00.shared.po.ResultMessage;

public interface NotificationActivateService {
    /**
     * Activates a bill that has been approved of.
     * @param id id for the bill
     * @return whether the operation is done successfully
     */
    ResultMessage activate(String id);
}
