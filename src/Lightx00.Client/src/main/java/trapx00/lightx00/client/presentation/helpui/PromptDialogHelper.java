package trapx00.lightx00.client.presentation.helpui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;


public class PromptDialogHelper {
    private Text titleText;
    private Node contentNode;
    private ArrayList<JFXButton> buttonList = new ArrayList<>();
    private JFXDialog dialog = new JFXDialog(null,null, JFXDialog.DialogTransition.CENTER);

    public PromptDialogHelper(String title, String content) {
        Text titleText = new Text(title);
        titleText.setFont(Font.font(20));
        this.titleText = titleText;
        this.contentNode  = new Text(content);
    }

    public static PromptDialogHelper start(String title, String content) {
        return new PromptDialogHelper(title, content);
    }

    public PromptDialogHelper addTable(JFXTreeTableView table) {
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(table);
        AnchorPane.setTopAnchor(table,24.0);
        AnchorPane.setBottomAnchor(table, 24.0);
        AnchorPane.setLeftAnchor(table, 24.0);
        AnchorPane.setRightAnchor(table, 24.0);
        contentNode = table;
        return this;
    }

    public Text getTitleText() {
        return titleText;
    }

    public PromptDialogHelper setTitleText(Text titleText) {
        this.titleText = titleText;
        return this;
    }

    public Node getContentNode() {
        return contentNode;
    }

    public PromptDialogHelper setContentText(String contentText) {
        this.contentNode = new Text(contentText);
        return this;
    }

    public PromptDialogHelper addCloseButton(String content, Node icon,  EventHandler<? super MouseEvent> e) {
        JFXButton button = new JFXButton(content, icon);
        button.setOnMouseClicked(event -> {
            FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
            if (e != null) {
                e.handle(event);
            }
        });
        buttonList.add(button);
        return this;
    }

    public PromptDialogHelper addCloseButton(String content, String glyphName, EventHandler<? super MouseEvent> e) {
        MaterialIconView icon = new MaterialIconView();
        icon.setGlyphName(glyphName);
        icon.setGlyphSize(24);
        return addCloseButton(content,icon, e);
    }

    public PromptDialogHelper addButton(String content, Node icon, EventHandler<? super MouseEvent> e) {
        JFXButton button = new JFXButton(content, icon);
        button.setOnMouseClicked(e);
        buttonList.add(button);
        return this;
    }

    public PromptDialogHelper setContent(Node node) {
        contentNode = node;
        return this;
    }

    public PromptDialogHelper addButton(String content, String glyphName, EventHandler<? super MouseEvent> e) {
        MaterialIconView icon = new MaterialIconView();
        icon.setGlyphName(glyphName);
        icon.setGlyphSize(24);
        return addButton(content, icon, e);
    }

    public PromptDialogHelper addButtons(JFXButton... buttons) {
        Collections.addAll(buttonList, buttons);
        return this;
    }

    public JFXDialog create(StackPane container) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(titleText);
        content.setBody(contentNode);
        content.setActions(buttonList);
        dialog.setContent(content);
        dialog.setDialogContainer(container);
        return dialog;
    }

    public JFXDialog create() {
        return create(FrameworkUiManager.getDialogContainer());
    }

    public void createAndShow() {
        FrameworkUiManager.getCurrentDialogStack().pushAndShow(create());
    }
}
