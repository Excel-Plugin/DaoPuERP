package trapx00.lightx00.client.presentation.notificationui;

import trapx00.lightx00.client.presentation.helpui.ExternalLoadableUiController;
import trapx00.lightx00.client.presentation.helpui.FrameworkUiManager;
import trapx00.lightx00.client.vo.notification.NotificationVo;

import java.util.function.Consumer;
@SuppressWarnings("unchecked")
public abstract class NotificationDetailUi<T extends NotificationVo> implements ExternalLoadableUiController, trapx00.lightx00.client.presentation.helpui.ContentDisplayUi<T> {
    protected Consumer<T> onAcknowledgeClicked = x -> {
        x.operationService().activate(x);
        FrameworkUiManager.getFrameworkUiController().refreshNotificationStatus();
    };
    protected Consumer<T> onIgnoreClicked = x -> FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    protected Runnable onExit;

    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }

    public void close() {
        if (onExit != null) {
            onExit.run();
        }
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();

    }
}
