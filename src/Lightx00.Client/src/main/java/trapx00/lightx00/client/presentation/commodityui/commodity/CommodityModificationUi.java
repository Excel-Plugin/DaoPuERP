package trapx00.lightx00.client.presentation.commodityui.commodity;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlService;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;

public class CommodityModificationUi implements ExternalLoadableUiController {
    public JFXTextField tfId;
    public JFXTextField tfName;
    public JFXTextField tfSort;
    public JFXTextField tfAmount;
    public JFXTextField tfpurchasePrice;
    public JFXTextField tfretailPrice;
    public JFXTextField tfwarningValue;

    private CommodityBlService blService = CommodityBlServiceFactory.getInstance();
    private Runnable runnable;
    private CommodityVo commodityVo;
    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/commodity/CommodityModificationUi.fxml").loadAndGetPackageWithoutException();
    }

    public void show(CommodityVo oldcommodityVo, Runnable runnable) {
        ExternalLoadedUiPackage uiPackage = load();
        CommodityModificationUi ui = uiPackage.getController();
        ui.commodityVo = oldcommodityVo;
        ui.tfId.setText(String.valueOf(oldcommodityVo.getId()));
        ui.tfName.setText(oldcommodityVo.getName());
        ui.tfSort.setText(oldcommodityVo.getType());
        ui.tfAmount.setText(String.valueOf(oldcommodityVo.getAmount()));
        ui.tfpurchasePrice.setText(String.valueOf(oldcommodityVo.getPurchasePrice()));
        ui.tfretailPrice.setText(String.valueOf(oldcommodityVo.getRetailPrice()));
        ui.tfwarningValue.setText(String.valueOf(oldcommodityVo.getWarningValue()));
        PromptDialogHelper.start("","").setContent(uiPackage.getComponent()).createAndShow();
        ((CommodityModificationUi)uiPackage.getController()).runnable = runnable;
    }

    public void initialize() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("");
        tfwarningValue.getValidators().add(validator);
        tfwarningValue.focusedProperty().addListener((observable, oldValue, newValue) -> {
        if (!newValue) {
            tfwarningValue.validate(); }
        });
        tfName.getValidators().add(validator);
        tfName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                tfName.validate(); }
        });
        tfretailPrice.getValidators().add(validator);
        tfretailPrice.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                tfretailPrice.validate(); }
        });
        tfpurchasePrice.getValidators().add(validator);
        tfpurchasePrice.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                tfpurchasePrice.validate(); }
        });

}



    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        if (tfretailPrice.validate()&&tfpurchasePrice.validate()&&tfName.validate()&&
                tfwarningValue.validate()&&tfAmount.validate()) {
            commodityVo.setName(tfName.getText());
            commodityVo.setAmount(Double.valueOf(tfAmount.getText()));
            commodityVo.setWarningValue(Double.valueOf(tfwarningValue.getText()));
            commodityVo.setPurchasePrice(Double.valueOf(tfpurchasePrice.getText()));
            commodityVo.setRetailPrice(Double.valueOf(tfretailPrice.getText()));
            blService.modify(commodityVo);
            PromptDialogHelper.start("修改成功！","商品已经修改成功。")
                    .addCloseButton("好","CHECK",e -> close())
                    .createAndShow();
        }
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        close();
    }

    private void close() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
        runnable.run();
    }
}
