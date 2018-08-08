package suwu.daopuerp.presentation.stockui.productionstock;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import suwu.daopuerp.dto.stock.ProductionBillStockItem;
import suwu.daopuerp.presentation.helpui.*;

import java.util.function.Consumer;

public class ProductionBillStockItemAddUiController implements ExternalLoadableUiController, ProductionBillStockItemSelection {
    @FXML
    private JFXTextField stockIdColumn;
    @FXML
    private JFXTextField stockAmountColumn;
    @FXML
    private JFXTextField stockProcessColumn;

    private StringProperty stockIdProperty = new SimpleStringProperty("");
    private StringProperty stockAmountProperty = new SimpleStringProperty("");
    private StringProperty stockProcessProperty = new SimpleStringProperty("");

    private Consumer<ProductionBillStockItem> callback;

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/stockui/ProductionBillStockAddUi.fxml").loadAndGetPackageWithoutException();
    }

    public void initialize() {
        stockIdColumn.textProperty().bindBidirectional(stockIdProperty);
        stockAmountColumn.textProperty().bindBidirectional(stockAmountProperty);
        stockProcessColumn.textProperty().bindBidirectional(stockProcessProperty);
    }

    @Override
    public void onClose() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }

    public void onBtnConfirmClicked(ActionEvent actionEvent) {
        onClose();
        ProductionBillStockItem productionBillStockItem = getProductionBillStockItem();
        if (callback != null) {
            callback.accept(productionBillStockItem);
        }
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }

    @Override
    public void showStockAdd(Consumer<ProductionBillStockItem> callback) {
        ExternalLoadedUiPackage uiPackage = load();
        ProductionBillStockItemAddUiController controller = uiPackage.getController();
        controller.callback = callback;
        JFXDialog dialog = PromptDialogHelper.start("", "").create();
        dialog.setContent((Region) uiPackage.getComponent());
        FrameworkUiManager.getCurrentDialogStack().pushAndShow(dialog);
    }

    private ProductionBillStockItem getProductionBillStockItem() {
        return new ProductionBillStockItem(stockIdProperty.get(), Double.parseDouble(stockAmountProperty.get()), stockProcessProperty.get());
    }
}
