package trapx00.lightx00.shared.dataservice.notificationdataservice;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.notification.NotificationPo;
import trapx00.lightx00.shared.queryvo.NotificationQueryVo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotificationDataService extends Remote {

    /**
     * Updates current user's notification.
     * @return current user's notification
     */
    NotificationPo[] query(NotificationQueryVo query) throws RemoteException;

    /**
     * Acknowledges a notification. It would delete a notification.
     * @param id  id for the notification to be acknowledged
     * @return whether the operation is done successfully
     */

    ResultMessage acknowledge(int id) throws RemoteException;

    /**
     * Adds a notification.
     * @param notificationPo NotificationPo
     * @return whether the operation is done successfully
     */
    ResultMessage add(NotificationPo notificationPo) throws RemoteException;
}
