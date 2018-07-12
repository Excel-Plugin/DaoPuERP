package trapx00.lightx00.client.presentation.promotionui.detail;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.presentation.promotionui.PromotionCommodityModel;
import trapx00.lightx00.client.vo.manager.promotion.ComSalePromotionVo;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.util.DateHelper;

public class ComSalePromotionDetailUi extends PromotionDetailUi {
    public JFXTextField tfId;
    public JFXTextField tfStartDate;
    public JFXTextField tfEndDate;
    public JFXTextField tfSalePrice;
    public JFXTreeTableView<PromotionCommodityModel> tbPromotionCommodity;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcId;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcName;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcPrice;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcAmount;
    public JFXButton btnBack;

    private ObservableList<PromotionCommodityModel> promotionCommodityModelObservableList = FXCollections.observableArrayList();

    @Override
    public ExternalLoadedUiPackage showContent(PromotionVoBase arg) {
        ComSalePromotionVo comSalePromotion = (ComSalePromotionVo) arg;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        ComSalePromotionDetailUi comSalePromotionDetailUi = (ComSalePromotionDetailUi) externalLoadedUiPackage.getController();
        comSalePromotionDetailUi.tfId.setText(comSalePromotion.getId());
        comSalePromotionDetailUi.tfStartDate.setText(DateHelper.fromDate(comSalePromotion.getStartDate()));
        comSalePromotionDetailUi.tfEndDate.setText(DateHelper.fromDate(comSalePromotion.getEndDate()));
        comSalePromotionDetailUi.tfSalePrice.setText(String.valueOf(comSalePromotion.getOnSalePrice()));
        comSalePromotionDetailUi.addPromotionCommodities(comSalePromotion.getPromotionCommodities());
        return externalLoadedUiPackage;
    }

    public void addPromotionCommodities(PromotionCommodity[] promotionCommodities) {
        for (PromotionCommodity commodity : promotionCommodities) {
            promotionCommodityModelObservableList.add(new PromotionCommodityModel(commodity));
        }

    }

    public void initialize() {
        tcId.setCellValueFactory(cellData -> cellData.getValue().getValue().idProperty());
        tcName.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        tcPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getPrice())));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getAmount())));
        TreeItem<PromotionCommodityModel> root = new RecursiveTreeItem<>(promotionCommodityModelObservableList, RecursiveTreeObject::getChildren);
        tbPromotionCommodity.setRoot(root);
        tbPromotionCommodity.setShowRoot(false);

    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/managerui/ComSalePromotionDetailUi.fxml").loadAndGetPackageWithoutException();
    }

    public void onBtnBackClicked() {
        onClose();
    }

    public void onBtnSubmitClicked() {
        onClose();
    }
}
