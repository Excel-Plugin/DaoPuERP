package trapx00.lightx00.client.presentation.loginui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import trapx00.lightx00.client.bl.loginbl.factory.FaceIdAuthenticationBlServiceFactory;
import trapx00.lightx00.client.blservice.loginblservice.FaceIdAuthenticationBlService;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.presentation.helpui.webcam.WebCamView;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.exception.faceid.MultipleFacesException;
import trapx00.lightx00.shared.exception.faceid.NetworkException;
import trapx00.lightx00.shared.exception.faceid.NoFaceDetectedException;
import trapx00.lightx00.shared.exception.faceid.NotRegisteredException;

public class FaceIdLoginUiController implements ExternalLoadableUiController {
    public JFXButton loginButton;
    public JFXButton cancelButton;
    public StackPane rootPane;
    public WebCamView webCamView;
    private FaceIdAuthenticationBlService blService = FaceIdAuthenticationBlServiceFactory.getService();

    public void initialize() {
        loginButton.setDisable(true);
        webCamView.setOnCameraInitialized(() -> loginButton.setDisable(false));
        BorderlessStageHelper.makeDraggable(StageManager.getStage(), rootPane);
    }

    public void onLoginButtonClicked(ActionEvent actionEvent) {
        stopCamera(actionEvent);
        JFXDialog dialog = PromptDialogHelper.start("登录中","登录中")
            .create(rootPane);
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    EmployeeVo employeeVo = blService.authenticate(webCamView.acquireImage());
                    dialog.close();
                    if (employeeVo == null) {
                        showPromptDialog("登录失败！","未知错误！", () -> startCamera());
                    } else {
                        Platform.runLater(() -> {
                            JFXDialogLayout layout = new JFXDialogLayout();
                            JFXButton btnConfirm = new JFXButton("好", new MaterialIconView(MaterialIcon.CHECK));
                            JFXButton btnClose = new JFXButton("不对，这不是我", new MaterialIconView(MaterialIcon.CLOSE));
                            layout.setBody(new javafx.scene.control.Label(String.format("登录成功！确认将以%s（id: %s）的身份登录！", employeeVo.getName(), employeeVo.getId())));
                            layout.setHeading(new Label("登录成功"));
                            layout.setActions(btnConfirm, btnClose);
                            JFXDialog dialog = new JFXDialog(rootPane, layout, JFXDialog.DialogTransition.CENTER);
                            btnConfirm.setOnAction(e -> {
                                closeCamera();
                                disposeCamera(actionEvent);
                                dialog.close();
                                FinishLoginLogic.finishLogin(employeeVo);
                            });
                            btnClose.setOnAction(e -> {
                                startCamera();
                                dialog.close();
                            });
                            dialog.show();
                        });

                    }
                } catch (MultipleFacesException e) {
                    dialog.close();
                    Platform.runLater(() -> showPromptDialog("登录失败！", String.format("检测到%d张脸。只允许一张脸！", e.getNumOfFaces()), () -> startCamera()));
                } catch (NoFaceDetectedException e) {
                    dialog.close();
                    Platform.runLater(() -> showPromptDialog("登录失败！", "未检测到脸！", () -> startCamera()));
                } catch (NotRegisteredException e) {
                    dialog.close();
                    Platform.runLater(() -> showPromptDialog("登录失败！", "未注册Face ID。请联系管理员注册Face ID。", () -> startCamera()));
                }
                catch (NetworkException e) {
                    dialog.close();
                    Platform.runLater(() -> showPromptDialog("网络错误！", "请联系技术人员。HTTP响应码：" + e.getStatusCode(), () -> startCamera()));
                } catch (Exception e) {
                    dialog.close();
                    Platform.runLater(() -> showPromptDialog("登录失败！",String.format("出现了异常。\n%s", e.toString()), () -> startCamera()));
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        dialog.show();
        th.start();

    }

    public void showPromptDialog(String title, String content, Runnable callback) {
        JFXDialogLayout layout = new JFXDialogLayout();
        JFXButton button = new JFXButton("好", new MaterialIconView(MaterialIcon.CHECK));
        layout.setBody(new javafx.scene.control.Label(content));
        layout.setHeading(new Label(title));
        layout.setActions(button);
        JFXDialog dialog = new JFXDialog(rootPane, layout, JFXDialog.DialogTransition.CENTER);
        button.setOnAction(e -> {
            dialog.close();
            if (callback != null) {
                callback.run();
            }
        });
        dialog.show();

    }

    private void closeCamera() {
        webCamView.closeCamera();
    }

    public void stopCamera(ActionEvent event) {
        webCamView.stopCamera();
    }

    public void startCamera() {
        webCamView.startCamera();
    }

    public void disposeCamera(ActionEvent event) {
        webCamView.disposeCamera();
    }

    public void onCancelButtonClicked(ActionEvent actionEvent) {
        StageManager.closeStage();
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/loginui/FaceIdLogin.fxml").loadAndGetPackageWithoutException();
    }

    public void onBtnJumpBackClicked(ActionEvent actionEvent) {
        ExternalLoadedUiPackage externalLoadedUiPackage = new LoginUiController().load();
        StageManager.changeScene(new Scene(externalLoadedUiPackage.getComponent()));

    }

    @Override
    public void onClose() {
        webCamView.closeCamera();
    }
}
