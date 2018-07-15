package suwu.daopuerp.presentation.mainui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.effects.JFXDepthManager;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import suwu.daopuerp.presentation.helpui.*;

public class FrameworkUiController {
    public static final int DEPTH = 3;
    public StackPane dialogContainer;
    public GridPane drawer;
    public GridPane titleBar;
    public GridPane bottomBar;
    public Pane contentPane;
    public JFXButton closeButton;
    public Stage stage;
    public JFXButton maximizeButton;
    public JFXButton minimizeButton;
    public MaterialIconView maximizeButtonGlyph;
    public Label promptLabel;
    public Text titleText;
    public JFXButton btnNotification;
    public JFXButton btnDraft;
    private ExternalLoadableUiController subController;
    private DialogStack dialogStack = new DialogStack();
//    private ExternalLoadedUiPackage notificationUi;
//    private ExternalLoadedUiPackage draftUi;

    public void setStage(Stage stage) {
        this.stage = stage;
        StageManager.setStage(stage);
        BorderlessStageHelper.makeResizeable(stage);
        BorderlessStageHelper.makeDraggable(stage, titleBar);
    }

    public void initialize() {
        JFXDepthManager.setDepth(drawer, DEPTH);
        JFXDepthManager.setDepth(titleBar, DEPTH);
        JFXDepthManager.setDepth(bottomBar, DEPTH);
        JFXDepthManager.setDepth(contentPane, DEPTH);
        closeButton.setOnMouseClicked(e -> stage.close());
        minimizeButton.setOnMouseClicked(e -> stage.setIconified(true));
        maximizeButton.setOnMouseClicked(e -> {
            if (stage.isMaximized()) {
                stage.setMaximized(false);
                maximizeButtonGlyph.setGlyphName("KEYBOARD_ARROW_UP");
            } else {
                stage.setMaximized(true);
                maximizeButtonGlyph.setGlyphName("KEYBOARD_ARROW_DOWN");
            }

        });
        switchBackToHome();

        promptLabel.setText("欢迎你！" + FrameworkUiManager.getCurrentUser().getUsername());

    }


    public void setContent(Node content) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(content);
    }

    public void showLoadingAnimation() {
        JFXSpinner spinner = new JFXSpinner();
        spinner.setStyle("-jfx-radius: 70px");

        this.contentPane.getChildren().clear();
        this.contentPane.getChildren().add(spinner);
    }

    /**
     * 切换功能界面的方法。这个重载方法每次都会重新加载一个新的功能界面，以前的功能界面的状态将会被丢弃。
     *
     * @param clazz   对应功能界面的类对象
     * @param title   标题名称
     * @param refresh 如果新的UI界面和原来的界面是同一个界面的话，是否需要刷新。
     */

    public void switchFunction(Class<? extends ExternalLoadableUiController> clazz, String title, boolean refresh) {


        Thread thread = new Thread(() -> {
            Platform.runLater(this::showLoadingAnimation);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                try {
                    switchFunction(clazz.newInstance().load(), title, refresh);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        });
        thread.setUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler());
        thread.start();


    }

    /**
     * 另一种通过package切换界面的方法。这个方法允许用户自己维护原来的功能界面的状态。
     *
     * @param uiPackage package
     * @param refresh   如果新的UI界面和原来的界面是同一个界面的话（通过controller是否一致来判断），是否需要刷新。
     * @see #switchFunction(Parent, ExternalLoadableUiController, String, boolean)  和这个等价
     */

    public void switchFunction(ExternalLoadedUiPackage uiPackage, String title, boolean refresh) {
        switchFunction(uiPackage.getComponent(), uiPackage.getController(), title, refresh);
    }

    /**
     * 另一种通过package切换界面的方法。这个方法允许用户自己维护原来的功能界面的状态。
     *
     * @param parent     功能对象UI元素
     * @param controller 控制器
     * @see #switchFunction(ExternalLoadedUiPackage, String, boolean) 和这个等价
     */
    public void switchFunction(Parent parent, ExternalLoadableUiController controller, String title, boolean refresh) {
        if (refresh || (!(subController.getClass().isAssignableFrom(controller.getClass())))) {
            if (subController != null) {
                subController.onClose();
            }
            subController = controller;
            this.contentPane.getChildren().clear();
            this.contentPane.getChildren().add(parent);
            this.titleText.setText(title);
        }
    }

    /**
     * 增加一个HomeUiController后，重写这个方法做到退回主界面。
     */
    public void switchBackToHome() {

    }


    public DialogStack getDialogStack() {
        return dialogStack;
    }
}
