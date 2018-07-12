package trapx00.lightx00.client.bl.logbl;

import trapx00.lightx00.client.blservice.logblservice.LogBackupBlService;
import trapx00.lightx00.client.datafactory.logdataservicefactory.LogBackupDataServiceFactory;
import trapx00.lightx00.shared.dataservice.logdataservice.LogBackupDataService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.LogBackupVo;

import java.rmi.RemoteException;

public class LogBackupBlController implements LogBackupBlService {
    LogBackupDataService service = LogBackupDataServiceFactory.getService();

    /**
     * back up the log information on the server to oos and clear the log
     *
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage backupLog() {
        try {
            return service.backupLog();
        } catch (RemoteException e) {
            e.printStackTrace();
            return ResultMessage.Failure;
        }
    }

    /**
     * get the log backed up on the cloud
     *
     * @return the temp uri of the log resources
     */
    @Override
    public LogBackupVo[] fetchCloudLog() {
        try {
            return service.fetchCloudLog();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
