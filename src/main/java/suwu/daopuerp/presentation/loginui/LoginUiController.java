package suwu.daopuerp.presentation.loginui;

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
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import suwu.daopuerp.bl.account.factory.AccountBlServiceFactory;
import suwu.daopuerp.blservice.account.AccountBlService;
import suwu.daopuerp.dto.UserDto;
import suwu.daopuerp.exception.PasswordWrongException;
import suwu.daopuerp.exception.UserDoesNotExistException;
import suwu.daopuerp.presentation.helpui.*;
import suwu.daopuerp.presentation.mainui.FrameworkUiController;

import java.io.IOException;

@Component
public class LoginUiController implements ExternalLoadableUiController {
    public JFXPasswordField passwordField;
    public JFXTextField usernameField;
    public JFXButton cancelButton;
    public StackPane dialogContainer;
    public BorderPane rootPane;
    public JFXButton loginButton;
    private AccountBlService accountBlService = AccountBlServiceFactory.getAccountBlService();

    public LoginUiController() {
    }

    @Autowired
    public LoginUiController(AccountBlService accountBlService) {
        this.accountBlService = accountBlService;
    }

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

            try {
                UserDto userDto = accountBlService.login(usernameField.getText(), passwordField.getText());
                finishLogin(userDto);
            } catch (PasswordWrongException exception) {
                exception.printStackTrace();
                loginButton.setDisable(false);
                loginButton.setText("登录");
                JFXDialogLayout layout = new JFXDialogLayout();
                JFXButton button = new JFXButton("好", new MaterialIconView(MaterialIcon.CHECK));
                layout.setBody(new Label("登录失败！请检查用户名！或者寻找支持人员！"));
                layout.setHeading(new Label("登录失败！"));
                layout.setActions(button);
                JFXDialog dialog = new JFXDialog(dialogContainer, layout, JFXDialog.DialogTransition.CENTER);
                button.setOnAction(e -> dialog.close());
                dialog.show();
            } catch (UserDoesNotExistException e) {
                e.printStackTrace();
            }


        }));

        thread.start();
    }

    private void finishLogin(UserDto userDto) {
        try {
            FrameworkUiManager.setCurrentEmployee(userDto);

            Stage newStage = new Stage();

            UiLoader loader = new UiLoader("/fxml/mainui/EmployeeUi.fxml");

            Scene scene = new Scene(loader.load());


            FrameworkUiController controller = loader.getController();

            FrameworkUiManager.setFrameworkUiController(controller);

            newStage.initStyle(StageStyle.UNDECORATED);

            newStage.setScene(scene);
            newStage.setHeight(900);
            newStage.setWidth(1500);
            StageManager.closeStage();
            controller.setStage(newStage);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return new UiLoader("/fxml/loginui/LoginUi.fxml").loadAndGetPackageWithoutException();
    }
}
