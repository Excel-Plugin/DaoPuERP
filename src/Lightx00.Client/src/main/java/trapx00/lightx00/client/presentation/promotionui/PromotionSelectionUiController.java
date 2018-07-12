package trapx00.lightx00.client.presentation.promotionui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.bl.promotionbl.PromotionInfo;
import trapx00.lightx00.client.bl.promotionbl.factory.PromotionInfoFactory;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.PromptDialogHelper;
import trapx00.lightx00.client.presentation.helpui.SelectingDialog;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;

import java.util.function.Consumer;

public class PromotionSelectionUiController extends SelectingDialog implements PromotionSelection {
    public JFXTreeTableView<PromotionSelectionItemModel> tbPromotion;
    public JFXTreeTableColumn<PromotionSelectionItemModel, String> tcId;
    public JFXTreeTableColumn<PromotionSelectionItemModel, String> tcType;
    public JFXTreeTableColumn<PromotionSelectionItemModel, String> tcGift;//赠品
    public JFXTreeTableColumn<PromotionSelectionItemModel, String> tcCoupon;//代金券
    public JFXTreeTableColumn<PromotionSelectionItemModel, String> tcSale;//客户折扣
    public JFXButton btnSelect;
    public JFXButton btnClose;

    private Consumer<PromotionVoBase> callback;
    private ObservableList<PromotionSelectionItemModel> promotionSelectionItemModelObservableList = FXCollections.observableArrayList();

    private PromotionInfo promotionInfo = PromotionInfoFactory.getPromotionInfo();
    private PromotionVoBase[] available;

    @FXML
    private void initialize() {
        initTable();
        update();

    }

    private void update() {
        promotionSelectionItemModelObservableList.clear();
        if (available != null) {
            for (PromotionVoBase promotion : available) {
                promotionSelectionItemModelObservableList.add(new PromotionSelectionItemModel(promotion));
            }
        }
    }


    @FXML
    private void initTable() {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getId()));
        tcType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getType().toString()));
        tcGift.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getGift()));
        tcCoupon.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCoupon()));
        tcSale.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getSale()));
        TreeItem<PromotionSelectionItemModel> root = new RecursiveTreeItem<>(promotionSelectionItemModelObservableList, RecursiveTreeObject::getChildren);
        tbPromotion.setRoot(root);
        tbPromotion.setShowRoot(false);
        tbPromotion.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tbPromotion.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<PromotionSelectionItemModel> selectedItem = tbPromotion.getSelectionModel().getSelectedItem();
                showDetail(selectedItem.getValue());
            }
        });
    }

    @Override
    public void showEmployeeSelectDialog(Consumer<PromotionVoBase> callback, SaleBillVo bill) {
        PromotionVoBase[] result = promotionInfo.queryPromotion(bill);
        if (result == null) {
            PromptDialogHelper.start("查询结果", "无可用促销策略！")
                    .addCloseButton("好的", "CHECK", e -> onClose())
                    .createAndShow();
        } else {
            ExternalLoadedUiPackage uiPackage = load();
            PromotionSelectionUiController controller = uiPackage.getController();
            controller.callback = callback;
            controller.available = result;
            controller.update();
            PromptDialogHelper.start("","").setContent(uiPackage.getComponent()).createAndShow();
        }
    }

    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/managerui/PromotionSelectionUi.fxml").loadAndGetPackageWithoutException();
    }

    public void onBtnCloseClicked() {
        onClose();
    }

    public void onBtnSelectClicked() {
        PromotionVoBase selected = tbPromotion.getSelectionModel().getSelectedItem().getValue().getPromotion();
        if (callback != null && selected != null) {
            callback.accept(selected);
        }
        onClose();
    }

    private void showDetail(PromotionSelectionItemModel model) {
        if (model != null) {
            PromotionVoBase selected = model.getPromotion();
            PromptDialogHelper.start("单据详细信息", "")
                    .setContent(selected.promotionDetailUi().showContent(selected).getComponent())
                    .addCloseButton("好", "CLOSE", null)
                    .createAndShow();
        }
    }

}
