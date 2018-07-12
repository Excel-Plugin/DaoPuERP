package trapx00.lightx00.shared.dataservice.logdataservice;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.LogBackupVo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LogBackupDataService extends Remote {
    /**
     * back up the log information on the server to oos and clear the log
     *
     * @return whether the operation is done successfully
     */
    ResultMessage backupLog() throws RemoteException;

    /**
     * get the log backed up on the cloud
     *
     * @return the temp uri of the log resources
     */
    LogBackupVo[] fetchCloudLog() throws RemoteException;
}
