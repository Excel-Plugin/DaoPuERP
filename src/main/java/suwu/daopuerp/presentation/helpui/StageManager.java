package suwu.daopuerp.presentation.helpui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {
    private static Stage stage;

    public static void setStage(Stage stage) {
        StageManager.stage = stage;
    }

    public static void closeStage() {
        stage.close();
    }

    public static void changeScene(Scene newScene) {
        stage.setScene(newScene);
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    public static Stage getStage() {
        return stage;
    }

}
