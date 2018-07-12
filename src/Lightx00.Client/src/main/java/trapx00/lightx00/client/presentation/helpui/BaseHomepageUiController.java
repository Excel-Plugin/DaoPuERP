package trapx00.lightx00.client.presentation.helpui;

import com.jfoenix.controls.JFXButton;
import javafx.scene.text.Text;
import trapx00.lightx00.client.presentation.mainui.FrameworkUiController;
import trapx00.lightx00.shared.util.DateHelper;

public abstract class BaseHomepageUiController implements ExternalLoadableUiController {
    private FrameworkUiController frameworkUiController = FrameworkUiManager.getFrameworkUiController();
    public JFXButton btnNotification;
    public JFXButton btnDraft;
    public Text textLoginTime;
    public Text textWelcome;

    public void refresh() {
        int draftCount = frameworkUiController.refreshDraftStatus();
        int notificationCount = frameworkUiController.refreshNotificationStatus();

        btnNotification.setText(String.valueOf(notificationCount));
        btnDraft.setText(String.valueOf(draftCount));
        textLoginTime.setText("登录时间：" + DateHelper.fromDate(FrameworkUiManager.getLoginDate()));

    }
}
