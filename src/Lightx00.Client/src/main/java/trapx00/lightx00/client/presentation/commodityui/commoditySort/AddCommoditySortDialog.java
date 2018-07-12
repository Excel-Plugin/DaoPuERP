package trapx00.lightx00.client.presentation.commodityui.commoditySort;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import trapx00.lightx00.client.blservice.commodityblservice.CommoditySortBlService;
import trapx00.lightx00.client.blservice.commodityblservice.CommoditySortBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.inventorystaff.CommoditySortVo;

public class AddCommoditySortDialog implements ExternalLoadableUiController {
    public JFXTextField tfId;
    public JFXTextField tfName;
    public JFXTextField tfSort;
    public JFXComboBox<Label> jfxComboBox=new JFXComboBox<Label>();

    private CommoditySortBlService blService= CommoditySortBlServiceFactory.getInstance();
    private Runnable runnable;

    public void show(String preId,Runnable runnable) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        AddCommoditySortDialog ui = externalLoadedUiPackage.getController();
        ui.tfId.setText(blService.getId());
        ui.tfSort.setText(preId);
        ui.jfxComboBox.getItems().add(new Label("是"));
        ui.jfxComboBox.getItems().add(new Label("不是"));
        PromptDialogHelper.start("","").setContent(externalLoadedUiPackage.getComponent()).createAndShow();
        ui.runnable = runnable;
    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/commoditysort/AddCommoditySortDialog.fxml").loadAndGetPackageWithoutException();
    }

    @FXML
    private void initialize() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("请输入");
        tfName.getValidators().add(validator);
        tfName.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfName.validate();
            }
        });
    }

    public void onBtnCancelClicked(ActionEvent actionEvent) {
        close();
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        if (tfName.validate()) {
            blService.add(new CommoditySortVo(
                    tfId.getText(),
                    tfName.getText(),
                    jfxComboBox.getValue().getText().equals("是")? 1:0,
                    tfSort.getText(),
                    null)
            );
            PromptDialogHelper.start("创建成功！","已经创建新的商品分类。")
                    .addCloseButton("好","CHECK",e -> close())
                    .createAndShow();
        }
    }

    public void close() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
        runnable.run();
    }
}