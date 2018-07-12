package trapx00.lightx00.client.presentation.loginui;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import trapx00.lightx00.client.bl.loginbl.factory.LoginBlFactory;
import trapx00.lightx00.client.blservice.loginblservice.LoginBlService;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.EmployeeVo;

public class LoginUiController implements ExternalLoadableUiController {
    public JFXPasswordField passwordField;
    public JFXTextField usernameField;
    public JFXButton cancelButton;
    public StackPane dialogContainer;
    public BorderPane rootPane;
    public JFXButton loginButton;
    private LoginBlService blService = LoginBlFactory.getController();

    public void initializeBorderlessStuff() {
        Stage stage = StageManager.getStage();
        BorderlessStageHelper.makeDraggable(stage, rootPane);
        BorderlessStageHelper.makeResizeable(stage);
    }

    public void initialize() {
        RequiredFieldValidator usernameValidator = new RequiredFieldValidator();
        usernameValidator.setMessage("请输入用户名");
        usernameField.getValidators().add(usernameValidator);

        RequiredFieldValidator passwordValidator = new RequiredFieldValidator();
        passwordValidator.setMessage("请输入密码");
        passwordField.getValidators().add(passwordValidator);

        usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                usernameField.validate();
            }
        });

        passwordField.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                passwordField.validate();
            }
        }));

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                loginButton.fire();
            }
        });


    }

    private boolean validate() {
        return usernameField.validate() & passwordField.validate();
    }

    public void onLoginButtonClicked() {
        if (!validate()) {
            return;
        }

        loginButton.setDisable(true);
        loginButton.setText("登录中");

        Thread thread = new Thread(() -> Platform.runLater(() -> {
            EmployeeVo employeeVo = blService.login(usernameField.getText(), passwordField.getText());
            if (employeeVo != null) {
                finishLogin(employeeVo);
            } else {
                loginButton.setDisable(false);
                loginButton.setText("登录");
                JFXDialogLayout layout = new JFXDialogLayout();
                JFXButton button = new JFXButton("好", new MaterialIconView(MaterialIcon.CHECK));
                layout.setBody(new Label("登录失败！请检查用户名！或者寻找支持人员！"));
                layout.setHeading(new Label("登录失败！"));
                layout.setActions(button);
                JFXDialog dialog = new JFXDialog(dialogContainer, layout, JFXDialog.DialogTransition.CENTER);
                button.setOnAction(e -> {
                    dialog.close();
                });
                dialog.show();

            }
        }));

        thread.start();
    }

    private void finishLogin(EmployeeVo employeeVo) {
        FinishLoginLogic.finishLogin(employeeVo);
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        StageManager.closeStage();
    }

    public void onBtnFaceIdClicked(ActionEvent actionEvent) {
        ExternalLoadedUiPackage externalLoadedUiPackage = new FaceIdLoginUiController().load();
        Scene newScene = new Scene(externalLoadedUiPackage.getComponent());
        StageManager.changeScene(newScene);
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/loginui/LoginUi.fxml").loadAndGetPackageWithoutException();
    }
}
