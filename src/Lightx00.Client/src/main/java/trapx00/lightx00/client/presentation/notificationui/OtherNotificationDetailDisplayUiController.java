package trapx00.lightx00.client.presentation.notificationui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.notification.others.OtherNotificationVo;
import trapx00.lightx00.shared.util.DateHelper;

public class OtherNotificationDetailDisplayUiController extends NotificationDetailUi<OtherNotificationVo> {

    public JFXTextField tfId;
    public JFXTextField tfSender;
    public JFXTextField tfDate;
    public JFXTextArea taContent;
    public JFXButton btnAcknowledge;
    public JFXButton btnBack;
    private OtherNotificationVo notificationVo;

    @Override
    public ExternalLoadedUiPackage showContent(OtherNotificationVo arg) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        OtherNotificationDetailDisplayUiController controller = externalLoadedUiPackage.getController();
        controller.tfId.setText(String.valueOf(arg.getId()));
        controller.tfSender.setText(arg.getSender().getName());
        controller.tfDate.setText(DateHelper.fromDate(arg.getDate()));
        controller.taContent.setText(arg.getContent());
        controller.notificationVo = arg;
        return externalLoadedUiPackage;
    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/notificationui/OtherNotificationDetailDisplayUi.fxml").loadAndGetPackageWithoutException();
    }


    public void onBtnAcknowledgeClicked(ActionEvent actionEvent) {
        if (onAcknowledgeClicked != null) {
            onAcknowledgeClicked.accept(notificationVo);
            close();
        }

    }

    public void onBtnBackClicked(ActionEvent actionEvent) {
        if (onIgnoreClicked != null) {
            onIgnoreClicked.accept(notificationVo);
        }

    }
}
