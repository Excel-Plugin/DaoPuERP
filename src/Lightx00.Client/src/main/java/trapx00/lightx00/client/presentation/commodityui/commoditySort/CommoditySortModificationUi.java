package trapx00.lightx00.client.presentation.commodityui.commoditySort;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlService;
import trapx00.lightx00.client.blservice.commodityblservice.CommodityBlServiceFactory;
import trapx00.lightx00.client.blservice.commodityblservice.CommoditySortBlService;
import trapx00.lightx00.client.blservice.commodityblservice.CommoditySortBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.inventorystaff.CommoditySortVo;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;
import trapx00.lightx00.shared.queryvo.CommoditySortQueryVo;

public class CommoditySortModificationUi  implements ExternalLoadableUiController {
    public JFXTextField tfId;
    public JFXTextField tfName;
    public JFXTextField tfSort;
    public JFXComboBox<Label> jfxComboBox=new JFXComboBox<Label>();

    private CommoditySortBlService blService= CommoditySortBlServiceFactory.getInstance();
    private CommodityBlService blService1= CommodityBlServiceFactory.getInstance();
    private Runnable runnable;

    public void show(CommoditySortVo commoditySortVo,Runnable runnable) {
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        CommoditySortModificationUi ui = externalLoadedUiPackage.getController();
        ui.tfId.setText(commoditySortVo.getId());
        if(commoditySortVo.getPreId()==null){
            ui.tfSort.setText("");
        }else{
            ui.tfSort.setText(blService.query(new CommoditySortQueryVo().eq("id",commoditySortVo.getPreId()))[0].getName());
        }
        ui.tfName.setText(commoditySortVo.getName());
        ui.jfxComboBox.getItems().add(new Label("是"));
        ui.jfxComboBox.getItems().add(new Label("不是"));
        ui.jfxComboBox.getSelectionModel().select(commoditySortVo.getLeaf()==1?0:1);
        PromptDialogHelper.start("","").setContent(externalLoadedUiPackage.getComponent()).createAndShow();
        ui.runnable = runnable;
    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/commoditysort/CommoditySortModificationUi.fxml").loadAndGetPackageWithoutException();
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
            CommoditySortVo []commoditySortVo=blService.query(new CommoditySortQueryVo().eq("preId",tfId.getText()));
            CommodityVo[] commodityVo=blService1.query(new CommodityQueryVo().eq("type",tfId.getText()));
            if(commoditySortVo.length==0&& commodityVo.length==0){
                blService.modify(new CommoditySortVo(
                        tfId.getText(),
                        tfName.getText(),
                        jfxComboBox.getValue().getText().equals("是")? 1:0,
                        tfSort.getText().equals("")?null:blService.query(new CommoditySortQueryVo().eq("name",tfSort.getText()))[0].getId(),
                        null)
                );
                PromptDialogHelper.start("修改成功！","已经修改商品分类。")
                        .addCloseButton("好","CHECK",e -> close())
                        .createAndShow();
            }else{
                if(jfxComboBox.getValue().getText().equals("不是")&&commodityVo.length!=0)
                    PromptDialogHelper.start("修改失败","该节点为叶节点").createAndShow();
                else if(jfxComboBox.getValue().getText().equals("是")&&commoditySortVo.length!=0){
                    PromptDialogHelper.start("修改失败","该节点为非叶节点").createAndShow();
                }
                else{
                    blService.modify(new CommoditySortVo(
                            tfId.getText(),
                            tfName.getText(),
                            jfxComboBox.getValue().getText().equals("是")? 1:0,
                            tfSort.getText().equals("")?null:blService.query(new CommoditySortQueryVo().eq("name",tfSort.getText()))[0].getId(),
                            null)
                    );
                    PromptDialogHelper.start("修改成功！","已经修改商品分类。")
                            .addCloseButton("好","CHECK",e -> close())
                            .createAndShow();
                }
            }

        }
    }

    public void close() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
        runnable.run();
    }
}
