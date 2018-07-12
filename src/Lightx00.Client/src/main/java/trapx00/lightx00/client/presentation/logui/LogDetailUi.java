package trapx00.lightx00.client.presentation.logui;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.log.LogVo;
import trapx00.lightx00.shared.util.DateHelper;

public class LogDetailUi implements ContentDisplayUi<LogVo>, ExternalLoadableUiController {
    public JFXTextField tfId;
    public JFXTextField tfDate;
    public JFXTextField tfType;
    public JFXTextArea taLogContent;

    @Override
    public ExternalLoadedUiPackage showContent(LogVo arg) {
        ExternalLoadedUiPackage uiPackage =  load();
        LogDetailUi controller = uiPackage.getController();
        controller.tfId.setText(String.valueOf(arg.getId()));
        controller.tfDate.setText(DateHelper.fromDate(arg.getDate()));
        controller.tfType.setText(arg.getSeverity().toString());
        controller.taLogContent.setText(arg.getContent());
        return uiPackage;
    }

    public void onBtnOkClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/logui/LogDetailUi.fxml").loadAndGetPackageWithoutException();
    }
}
