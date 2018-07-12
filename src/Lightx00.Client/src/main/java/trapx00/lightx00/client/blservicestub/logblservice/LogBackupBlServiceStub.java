package trapx00.lightx00.client.blservicestub.logblservice;

import trapx00.lightx00.client.blservice.logblservice.LogBackupBlService;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.LogBackupVo;

public class LogBackupBlServiceStub implements LogBackupBlService {
    /**
     * back up the log information on the server to oos and clear the log
     *
     * @return whether the operation is done successfully
     */
    @Override
    public ResultMessage backupLog() {
        return ResultMessage.Success;
    }

    /**
     * get the log backed up on the cloud
     *
     * @return the temp uri of the log resources
     */
    @Override
    public LogBackupVo[] fetchCloudLog() {
        return null;
    }
}
