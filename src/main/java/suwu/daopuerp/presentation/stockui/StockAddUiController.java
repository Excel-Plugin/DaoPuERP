package suwu.daopuerp.presentation.stockui;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import suwu.daopuerp.dto.stock.StockItem;
import suwu.daopuerp.presentation.helpui.*;

import java.util.function.Consumer;

public class StockAddUiController implements ExternalLoadableUiController, StockSelection {
    @FXML
    private JFXTextField stockIdColumn;
    @FXML
    private JFXTextField stockNameColumn;
    @FXML
    private JFXTextField stockPercentColumn;
    @FXML
    private JFXTextField stockPriceColumn;
    @FXML
    private JFXTextField stockProcessColumn;

    private StringProperty stockIdProperty = new SimpleStringProperty("");
    private StringProperty stockNameProperty = new SimpleStringProperty("");
    private StringProperty stockPercentProperty = new SimpleStringProperty("");
    private StringProperty stockPriceProperty = new SimpleStringProperty("");
    private StringProperty stockProcessProperty = new SimpleStringProperty("");

    private Consumer<StockItem> callback;

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/stockui/StockAddUi.fxml").loadAndGetPackageWithoutException();
    }

    public void initialize() {
        stockIdColumn.textProperty().bindBidirectional(stockIdProperty);
        stockNameColumn.textProperty().bindBidirectional(stockNameProperty);
        stockPercentColumn.textProperty().bindBidirectional(stockPercentProperty);
        stockPriceColumn.textProperty().bindBidirectional(stockPriceProperty);
        stockProcessColumn.textProperty().bindBidirectional(stockProcessProperty);
    }

    @Override
    public void onClose() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }

    public void onBtnConfirmClicked(ActionEvent actionEvent) {
        onClose();
        StockItem stockItem = getStockItem();
        if (callback != null) {
            callback.accept(stockItem);
        }
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }

    @Override
    public void showStockAdd(Consumer<StockItem> callback) {
        ExternalLoadedUiPackage uiPackage = load();
        StockAddUiController controller = uiPackage.getController();
        controller.callback = callback;
        JFXDialog dialog = PromptDialogHelper.start("", "").create();
        dialog.setContent((Region) uiPackage.getComponent());
        FrameworkUiManager.getCurrentDialogStack().pushAndShow(dialog);
    }

    private StockItem getStockItem() {
        return new StockItem(stockIdProperty.get(), stockNameProperty.get(), Double.parseDouble(stockPercentProperty.get()), Double.parseDouble(stockPriceProperty.get()), stockProcessProperty.get());
    }
}
