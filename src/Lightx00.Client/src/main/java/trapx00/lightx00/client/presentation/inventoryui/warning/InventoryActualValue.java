package trapx00.lightx00.client.presentation.inventoryui.warning;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlService;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;

public class InventoryActualValue implements ExternalLoadableUiController {
    public JFXTextField tfAmount;
    public JFXTextField tfoldAmount;


    private CommodityBlService blService = CommodityBlServiceFactory.getInstance();
    private Runnable runnable;
    private CommodityVo commodityVo;

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/warning/InventoryActualValue.fxml").loadAndGetPackageWithoutException();
    }

    public void show(CommodityVo oldcommodityVo, Runnable runnable) {
        ExternalLoadedUiPackage uiPackage = load();
        InventoryActualValue ui = uiPackage.getController();
        ui.commodityVo=oldcommodityVo;
        ui.tfoldAmount.setText(String.valueOf(oldcommodityVo.getAmount()));
        PromptDialogHelper.start("","").setContent(uiPackage.getComponent()).createAndShow();
        ((InventoryActualValue)uiPackage.getController()).runnable = runnable;
    }
    @FXML
    private void initialize() {
        DoubleValidator validator = new DoubleValidator();
        validator.setMessage("请输入数字");
        tfAmount.getValidators().add(validator);
        tfAmount.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfAmount.validate();
            }
        });
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        if (validate()) {
            commodityVo.setWarningValue(Double.valueOf(tfAmount.getText()));
            blService.modify(commodityVo);
            PromptDialogHelper.start("修改成功！","实际数量已经修改成功。")
                    .addCloseButton("好","CHECK",e -> close())
                    .createAndShow();
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
        runnable.run();
    }
}

