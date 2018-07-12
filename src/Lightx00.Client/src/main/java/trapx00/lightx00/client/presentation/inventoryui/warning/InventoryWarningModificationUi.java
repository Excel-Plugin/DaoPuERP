package trapx00.lightx00.client.presentation.inventoryui.warning;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlService;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;

import java.util.function.Consumer;

public class InventoryWarningModificationUi implements ExternalLoadableUiController {
    public JFXTextField tfAmount;
    public JFXTextField tfAcutal;
    private Consumer<Double> callback;
    private String id;
    private CommodityBlService blService= CommodityBlServiceFactory.getInstance();
    private  double amout;
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/warning/InventoryWarningModificationUi.fxml").loadAndGetPackageWithoutException();
    }

    public void show(String limit,Consumer<Double> callback) {
        ExternalLoadedUiPackage uiPackage = load();
        InventoryWarningModificationUi controller = (InventoryWarningModificationUi) uiPackage.getController();
        controller.callback = callback;
        controller.id=limit;
        controller.tfAcutal.setText(String.valueOf(blService.query(new CommodityQueryVo().eq("id",limit))[0].getAmount()));
        PromptDialogHelper.start("","").setContent(uiPackage.getComponent()).createAndShow();
    }
    @FXML
    private void initialize() {
        DoubleValidator validator = new DoubleValidator();
        validator.setMessage("请输入数字");
        tfAmount.getValidators().add(validator);
        tfAmount.focusedProperty().addListener((o, oldVal, newVal) -> {
            if(!newVal)
                tfAmount.validate();

        });
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        close();
        if (validate()) {
            if (callback != null) {
                blService.query(new CommodityQueryVo().eq("id",id))[0].setActualAmount(Double.valueOf(tfAmount.getText()));
                callback.accept(Double.valueOf(tfAmount.getText()));
            }

        }

    }

    private boolean validate() {
        return tfAmount.validate();
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        close();
    }

    public void close() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}
