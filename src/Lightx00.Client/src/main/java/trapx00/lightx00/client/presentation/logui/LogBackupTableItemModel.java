package trapx00.lightx00.client.presentation.logui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import trapx00.lightx00.shared.queryvo.LogBackupVo;

public class LogBackupTableItemModel extends RecursiveTreeObject<LogBackupTableItemModel> {
    private LogBackupVo logBackupVo;

    public LogBackupTableItemModel(LogBackupVo logBackupVo) {
        this.logBackupVo = logBackupVo;
    }

    public LogBackupVo getLogBackupVo() {
        return logBackupVo;
    }

    public void setLogBackupVo(LogBackupVo logBackupVo) {
        this.logBackupVo = logBackupVo;
    }
}
