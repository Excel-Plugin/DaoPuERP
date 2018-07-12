package trapx00.lightx00.client.blservice.logblservice;

import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.LogBackupVo;

public interface LogBackupBlService {
    /**
     * back up the log information on the server to oos and clear the log
     *
     * @return whether the operation is done successfully
     */
    ResultMessage backupLog();

    /**
     * get the log backed up on the cloud
     *
     * @return the temp uri of the log resources
     */
    LogBackupVo[] fetchCloudLog();
}
