package suwu.daopuerp.presentation.formulaui;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import suwu.daopuerp.dto.formula.StockItem;
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

    private Consumer<StockItem> callback;
    private StockItem stockItem;

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/formulaui/StockAddUi.fxml").loadAndGetPackageWithoutException();
    }

    @Override
    public void onClose() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }

    public void onBtnConfirmClicked(ActionEvent actionEvent) {
        onClose();
        StockItem stockItem = getStockItem();
        if (callback != null && stockItem != null) {
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
        return stockItem;
    }
}
