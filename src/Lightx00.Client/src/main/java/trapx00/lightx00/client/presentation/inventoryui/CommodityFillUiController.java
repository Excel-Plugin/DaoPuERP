package trapx00.lightx00.client.presentation.inventoryui;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.salestaff.CommodityFillVo;

import java.util.function.Consumer;

public class CommodityFillUiController extends SelectingDialog implements ExternalLoadableUiController {
    public JFXTextField tfCommodityNumber;
    public JFXTextField tfCommodityComment;
    public Consumer<CommodityFillVo> callback;

    /**
     * show the select client dialog
     *
     * @param callback call back function
     */
    public void showCommodityFillDialog(Consumer<CommodityFillVo> callback) {
        ExternalLoadedUiPackage uiPackage = load();
        CommodityFillUiController controller = uiPackage.getController();
        controller.callback = callback;
        PromptDialogHelper.start("请填写商品数量和商品备注", "").setContent((Region) uiPackage.getComponent()).createAndShow();
    }
    public void initialize(){
        NumberValidator numberValidator = new NumberValidator();
        numberValidator.setMessage("请输入数字类型");
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.setMessage("请输入信息");

        tfCommodityNumber.getValidators().add(numberValidator);

        tfCommodityNumber.focusedProperty().addListener(event->{
            tfCommodityNumber.validate();
        });
    }
    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/CommodityFillUi.fxml").loadAndGetPackageWithoutException();
    }

    @FXML
    private void onBtnSubmitClicked(ActionEvent actionEvent) {
        if(tfCommodityNumber.validate()) {
            onClose(); //一定要调用这个来把弹出框关了。
            if (callback != null) {
                callback.accept(new CommodityFillVo(Double.parseDouble(tfCommodityNumber.getText()), tfCommodityComment.getText())); //选择结束，调用回调方法。
            }
        }
    }

    @FXML
    private void onBtnCancelClicked(ActionEvent actionEvent) {
        onClose();
    }
}
