package trapx00.lightx00.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import trapx00.lightx00.client.presentation.helpui.PromptDialogHelper;
import trapx00.lightx00.client.presentation.helpui.StageManager;
import trapx00.lightx00.client.presentation.loginui.LoginUiController;
import trapx00.lightx00.shared.dataservice.logindataservice.LoginDataService;
import trapx00.lightx00.shared.util.RmiHelper;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFcaX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        StageManager.setStage(primaryStage);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        if (!testConnection(primaryStage)) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/loginui/LoginUi.fxml"));
        Scene newScene = new Scene(loader.load());


        LoginUiController controller = loader.getController();


        primaryStage.setScene(newScene);
        controller.initializeBorderlessStuff();
        primaryStage.show();
    }

    public boolean testConnection(Stage primaryStage) {
        try {
            Naming.lookup(RmiHelper.generateRmiUrl(LoginDataService.class));
            return true;
        } catch (ConnectException | NotBoundException e) {
            StackPane stackPane = new StackPane();
            primaryStage.setScene(new Scene(stackPane));

            PromptDialogHelper.start("服务器未启动！","请启动服务器后重新打开客户端。")
                .addButton("好","CHECK", e2 -> primaryStage.close())
                .create(stackPane)
                .show();

            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
            primaryStage.show();

        } catch (MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }
}