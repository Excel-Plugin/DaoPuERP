package trapx00.lightx00.client.presentation.mainui;

import javafx.event.ActionEvent;
import trapx00.lightx00.client.blservice.logblservice.LogBackupBlService;
import trapx00.lightx00.client.blservice.logblservice.LogBackupBlServiceFactory;
import trapx00.lightx00.client.presentation.adminui.AdminHomeUiController;
import trapx00.lightx00.client.presentation.adminui.FaceIdRegistrationUiController;
import trapx00.lightx00.client.presentation.adminui.UserManagementUiController;
import trapx00.lightx00.client.presentation.helpui.PromptDialogHelper;
import trapx00.lightx00.shared.po.ResultMessage;

public class AdminStaffUiController extends FrameworkUiController {
    private LogBackupBlService logBackupBlService= LogBackupBlServiceFactory.getInstance();

    public void initialize() {
        // 必须有。调用基类初始化来初始化基类的元素。
        super.initialize();
        // 如果还需要初始化自己的元素请接着写。
    }

    public void onUserManagementFunctionClicked(ActionEvent actionEvent) {
        switchFunction(UserManagementUiController.class, "管理职员信息",true);
    }

    public void onFaceIdRegistrationFunctionClicked(ActionEvent actionEvent) {
        switchFunction(FaceIdRegistrationUiController.class, "注册Face ID",true);
    }
    public void onLogBackUpFunctionClicked(ActionEvent actionEvent) {
        if(logBackupBlService.backupLog()== ResultMessage.Success){
            PromptDialogHelper.start("备份日志成功", "本地日志已经成功存储在云上。")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        }else{
            PromptDialogHelper.start("备份日志失败", "请稍后重试。")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        }
    }

    /**
     * 增加一个HomeUiController后，重写这个方法做到退回主界面。
     */
    @Override
    public void switchBackToHome() {
        switchFunction(AdminHomeUiController.class, "主界面",true);
    }
}
