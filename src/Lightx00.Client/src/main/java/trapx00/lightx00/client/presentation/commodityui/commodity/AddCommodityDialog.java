package trapx00.lightx00.client.presentation.commodityui.commodity;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.DoubleValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlService;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;

import java.util.Date;

public class AddCommodityDialog implements ExternalLoadableUiController{
    public JFXTextField tfId;
    public JFXTextField tfName;
    public JFXTextField tfSort;
    public JFXTextField tfAmount;
    public JFXTextField tfpurchasePrice;
    public JFXTextField tfretailPrice;
    public JFXTextField tfwarningValue;

    private CommodityBlService blService= CommodityBlServiceFactory.getInstance();
    private Runnable runnable;

    public void show(String text,Runnable runnable) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        AddCommodityDialog ui = externalLoadedUiPackage.getController();
        ui.tfId.setText(blService.getId(text));
        ui.tfSort.setText(text);
        ui.tfpurchasePrice.setText("0");
        ui.tfretailPrice.setText("0");
        ui.tfAmount.setText("0");
        PromptDialogHelper.start("","").setContent(externalLoadedUiPackage.getComponent()).createAndShow();
        ((AddCommodityDialog)externalLoadedUiPackage.getController()).runnable = runnable;

    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/commodity/AddCommodityDialog.fxml").loadAndGetPackageWithoutException();
    }

    @FXML
    private void initialize() {
        RequiredFieldValidator validator1 = new RequiredFieldValidator();
        validator1.setMessage("请输入");
        tfName.getValidators().add(validator1);
        tfName.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfName.validate();
            }
        });
        DoubleValidator validator = new DoubleValidator();
        validator.setMessage("请输入数字");
        tfAmount.getValidators().add(validator);
        tfAmount.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfAmount.validate();
            }
        });
        tfpurchasePrice.getValidators().add(validator);
        tfpurchasePrice.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfpurchasePrice.validate();
            }
        });
        tfretailPrice.getValidators().add(validator);
        tfretailPrice.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfretailPrice.validate();
            }
        });
        tfwarningValue.getValidators().add(validator);
        tfwarningValue.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfwarningValue.validate();
            }
        });
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        close();
    }

    private boolean validate() {
        return tfAmount.validate()&&tfpurchasePrice.validate()
                &&tfretailPrice.validate()&&tfwarningValue.validate();
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        if (validate()) {
           blService.add(new CommodityVo(
                        tfId.getText(),
                        tfName.getText(),
                        tfSort.getText(),
                        Double.parseDouble(tfAmount.getText()),
                        new Date(),
                        null,
                        null,
                        Double.parseDouble(tfpurchasePrice.getText()),
                        Double.parseDouble(tfpurchasePrice.getText()),
                        Double.parseDouble(tfretailPrice.getText()),
                        Double.parseDouble(tfretailPrice.getText()),
                        Double.parseDouble(tfwarningValue.getText()),
                   0
                ));
            PromptDialogHelper.start("创建成功！","已经创建新的商品。")
                    .addCloseButton("好","CHECK",e -> close())
                    .createAndShow();
        }
    }

    public void close() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
        runnable.run();
    }
}
