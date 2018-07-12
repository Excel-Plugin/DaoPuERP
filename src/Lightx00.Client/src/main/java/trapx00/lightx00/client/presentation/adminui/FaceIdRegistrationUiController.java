package trapx00.lightx00.client.presentation.adminui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import trapx00.lightx00.client.blservice.adminblservice.FaceIdRegistrationBlService;
import trapx00.lightx00.client.blservice.adminblservice.FaceIdRegistrationBlServiceFactory;
import trapx00.lightx00.client.presentation.adminui.factory.UserManagementUiFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.presentation.helpui.webcam.WebCamView;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.exception.faceid.MultipleFacesException;
import trapx00.lightx00.shared.exception.faceid.NetworkException;
import trapx00.lightx00.shared.exception.faceid.NoFaceDetectedException;
import trapx00.lightx00.shared.po.ResultMessage;

public class FaceIdRegistrationUiController implements ExternalLoadableUiController {
    public JFXButton btnCancel;
    public JFXButton btnRegister;
    public JFXTextField tfEmployeeId;
    public WebCamView webCamView;
    private FaceIdRegistrationBlService blService = FaceIdRegistrationBlServiceFactory.getService();
    private EmployeeSelection employeeSelection = UserManagementUiFactory.getEmployeeSelectionUi();

    private ObjectProperty<EmployeeVo> employee = new SimpleObjectProperty<>();

    public void onBtnRegisterClicked(ActionEvent actionEvent) {
        if (employee.get() == null) {
            showPromptDialog("请先选择一个用户！","未选择用户");
            return;
        }
        JFXDialog dialog = PromptDialogHelper.start("注册中","注册中").create();
        Task task = new Task() {
            @Override
            protected Object call() {
                try {
                    byte[] img = webCamView.acquireImage();
                    ResultMessage rm = blService.register(employee.get().getId(), img);
                    Platform.runLater(dialog::close);
                    if (rm.equals(ResultMessage.Success)) {
                        showPromptDialog("注册成功",String.format("您已给ID为%s的职员注册Face ID。", employee.get().getId()));
                    } else {
                        showPromptDialog("注册失败！", "未知错误");
                    }
                } catch (MultipleFacesException e) {
                    Platform.runLater(dialog::close);
                    showPromptDialog("注册失败！", String.format("检测到%d张脸。只允许一张脸！", e.getNumOfFaces()));
                } catch (NoFaceDetectedException e) {
                    Platform.runLater(dialog::close);
                    showPromptDialog("注册失败！", "未检测到脸！");
                } catch (NetworkException e) {
                    Platform.runLater(dialog::close);
                    showPromptDialog("注册失败！", "请联系技术人员！HTTP响应码：" + e.getStatusCode());
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(dialog::close);
                    showPromptDialog("注册失败！", "请联系技术人员！\n" + e.getMessage());
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        dialog.show();
        th.setDaemon(true);
        th.start();


    }

    public void showPromptDialog(String title, String content) {
        Platform.runLater(() -> PromptDialogHelper.start(title, content)
            .addCloseButton("好","CHECK",e -> webCamView.startCamera())
            .createAndShow());
    }


    @FXML
    public void initialize() {
        btnRegister.setDisable(true);
        webCamView.setOnCameraInitialized(() -> btnRegister.setDisable(false));
        employee.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                 tfEmployeeId.setText("");
            } else {
                tfEmployeeId.setText(String.format("%s(id: %s)", newValue.getName(), newValue.getId()));
            }
        });
    }


    public void onBtnCancelClicked(ActionEvent actionEvent) {
        StageManager.closeStage();
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/adminui/FaceIdRegistration.fxml").loadAndGetPackageWithoutException();
    }

    public void onTfEmployeeIdClicked(MouseEvent mouseEvent) {
        employeeSelection.showEmployeeSelectDialog(x -> employee.set(x.size() == 0 ? null : x.get(0)));
    }

    @Override
    public void onClose() {
        webCamView.closeCamera();
    }
}
