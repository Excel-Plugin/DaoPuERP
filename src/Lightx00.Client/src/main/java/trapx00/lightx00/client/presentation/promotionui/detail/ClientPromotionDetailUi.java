package trapx00.lightx00.client.presentation.promotionui.detail;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.presentation.promotionui.PromotionCommodityModel;
import trapx00.lightx00.client.vo.manager.promotion.ClientPromotionVo;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.util.DateHelper;

public class ClientPromotionDetailUi extends PromotionDetailUi {
    public JFXTextField tfId;
    public JFXTextField tfStartDate;
    public JFXTextField tfEndDate;
    public JFXTextField tfClientLevel;
    public JFXTreeTableView<PromotionCommodityModel> tbPromotionCommodity;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcId;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcName;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcPrice;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcAmount;
    public JFXButton btnBack;

    private ObservableList<PromotionCommodityModel> promotionCommodityModelObservableList = FXCollections.observableArrayList();

    @Override
    public ExternalLoadedUiPackage showContent(PromotionVoBase arg) {
        ClientPromotionVo clientPromotion = (ClientPromotionVo) arg;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        ClientPromotionDetailUi clientPromotionDetailUi = (ClientPromotionDetailUi) externalLoadedUiPackage.getController();
        clientPromotionDetailUi.tfId.setText(clientPromotion.getId());
        clientPromotionDetailUi.tfStartDate.setText(DateHelper.fromDate(clientPromotion.getStartDate()));
        clientPromotionDetailUi.tfEndDate.setText(DateHelper.fromDate(clientPromotion.getEndDate()));
        clientPromotionDetailUi.tfClientLevel.setText(String.valueOf(clientPromotion.getClientLevel()));
        clientPromotionDetailUi.addPromotionCommodities(clientPromotion.getPromotionCommodities());
        return externalLoadedUiPackage;
    }

    public void addPromotionCommodities(PromotionCommodity[] promotionCommodities) {
        for (PromotionCommodity commodity : promotionCommodities) {
            promotionCommodityModelObservableList.add(new PromotionCommodityModel(commodity));
        }

    }

    public void initialize() {
        tcId.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        tcName.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        tcPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().amountProperty())));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().amountProperty())));
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
        return new UiLoader("/fxml/managerui/ClientPromotionDetailUi.fxml").loadAndGetPackageWithoutException();
    }

    @FXML
    public void onBtnBackClicked() {
        onClose();
    }

    @FXML
    public void onBtnSubmitClicked() {
        onClose();
    }
}
