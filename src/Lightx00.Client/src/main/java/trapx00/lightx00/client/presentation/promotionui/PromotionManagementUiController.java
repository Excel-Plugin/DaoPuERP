package trapx00.lightx00.client.presentation.promotionui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import trapx00.lightx00.client.blservice.promotionblservice.*;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.shared.exception.database.PromotionInvalidStateException;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.po.manager.promotion.PromotionType;
import trapx00.lightx00.shared.queryvo.promotion.ClientPromotionQueryVo;
import trapx00.lightx00.shared.queryvo.promotion.ComSalePromotionQueryVo;
import trapx00.lightx00.shared.queryvo.promotion.TotalPricePromotionQueryVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PromotionManagementUiController implements ExternalLoadableUiController {
    public JFXTextField tfSearch;
    public JFXCheckBox cbAbandon;
    public JFXComboBox<PromotionType> cbType;
    public JFXTreeTableView<PromotionInfoModel> tablePromotion;
    public JFXTreeTableColumn<PromotionInfoModel, String> tcId;
    public JFXTreeTableColumn<PromotionInfoModel, String> tcType;
    public JFXTreeTableColumn<PromotionInfoModel, String> tcStartDate;
    public JFXTreeTableColumn<PromotionInfoModel, String> tcEndDate;
    public JFXTreeTableColumn<PromotionInfoModel, String> tcState;
    public JFXButton btnRefresh;
    public JFXButton btnDelete;

    private ObservableList<PromotionInfoModel> promotionInfoModels = FXCollections.observableArrayList();
    private ClientPromotionBlService clientPromotionBlService = ClientPromotionBlServiceFactory.getInstance();
    private ComSalePromotionBlService comSalePromotionBlService = ComSalePromotionBlServiceFactory.getInstance();
    private TotalPricePromotionBlService totalPricePromotionBlService = TotalPricePromotionBlServiceFactory.getInstance();

    private void search() {
        boolean show = cbAbandon.isSelected();
        String str = tfSearch.getText();
        if(str.length()==0) {
            updateItems();
        }
        else {
            List<PromotionVoBase> promotions = new ArrayList<>();
            ComSalePromotionQueryVo comSalePromotionQueryVo = new ComSalePromotionQueryVo();
            PromotionVoBase[] result1 = comSalePromotionBlService.queryPromotion(comSalePromotionQueryVo);
            if(result1!=null) promotions.addAll(Arrays.stream(result1).collect(Collectors.toList()));
            TotalPricePromotionQueryVo totalPricePromotionQueryVo = new TotalPricePromotionQueryVo();
            PromotionVoBase[] result2 = totalPricePromotionBlService.queryPromotion(totalPricePromotionQueryVo);
            if(result2!=null) promotions.addAll(Arrays.stream(result2).collect(Collectors.toList()));
            ClientPromotionQueryVo clientPromotionQueryVo = new ClientPromotionQueryVo();
            PromotionVoBase[] result3 = clientPromotionBlService.queryPromotion(clientPromotionQueryVo);
            if(result3!=null) promotions.addAll(Arrays.stream(result3).collect(Collectors.toList()));

            if (promotions != null) {
                promotionInfoModels.clear();
                for(PromotionVoBase promotion:promotions) {
                    if(show) {
                        if (!promotion.getState().equals(PromotionState.Draft)
                                && promotion.getId().contains(str)
                                || promotion.getType().toString().contains(str)
                                || promotion.getState().toString().contains(str)) {
                            promotionInfoModels.add(new PromotionInfoModel(promotion));
                        }
                    }
                    else {
                        if(!promotion.getState().equals(PromotionState.Draft)
                                && !promotion.getState().equals(PromotionState.Abandoned)
                                && promotion.getId().contains(str)
                                || promotion.getType().toString().contains(str)
                                || promotion.getState().toString().contains(str)) {
                            promotionInfoModels.add(new PromotionInfoModel(promotion));
                        }
                    }
                }

            }
            else if(promotions==null||promotions.size()==0) {
                new PromptDialogHelper("未查到匹配信息！", "")
                        .addCloseButton("好", "CHECK", null)
                        .createAndShow();
            }
        }
    }

    public void initialize() {
        initTable();
        updateItems();
    }

    private void updateItems() {
        promotionInfoModels.clear();
        addComSaleModel();
        addClientModel();
        addTotalPriceModel();
    }


    private void initTable() {
        tfSearch.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                cbType.getSelectionModel().clearSelection();
                search();
            }
        });
        cbAbandon.selectedProperty().addListener((observable, oldValue, newValue) -> {
            updateItems();
        });
        tcId.setCellValueFactory(cellData -> cellData.getValue().getValue().idProperty());
        tcStartDate.setCellValueFactory(cellData -> new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getStartDate())));
        tcEndDate.setCellValueFactory(cellData -> new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getEndDate())));
        tcType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getType().toString()));
        tcState.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getValue().getState().toString()));

        TreeItem<PromotionInfoModel> root = new RecursiveTreeItem<>(promotionInfoModels, RecursiveTreeObject::getChildren);
        tablePromotion.setRoot(root);
        tablePromotion.setShowRoot(false);
        tablePromotion.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tablePromotion.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                onBtnDetailClicked();
            }
        });

        cbType.getItems().addAll(FXCollections.observableArrayList(PromotionType.values()));
        cbType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue == null){

            }
            else {
                tfSearch.setText("");
                promotionInfoModels.clear();
                switch (newValue) {
                    case ComSalePromotion:
                        addComSaleModel();
                        break;
                    case ClientPromotion:
                        addClientModel();
                        break;
                    case TotalPricePromotion:
                        addTotalPriceModel();
                        break;
                }
            }
        });
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/managerui/PromotionManagementUi.fxml").loadAndGetPackageWithoutException();
    }

    public PromotionVoBase getSelected() {
        try {
            return tablePromotion.getSelectionModel().getSelectedItem().getValue().getPromotion();
        } catch (Exception e) {
            new PromptDialogHelper("未选择！","请先选择一个促销策略！")
                    .addCloseButton("好","CHECK", null)
                    .createAndShow();
            return null;
        }
    }

    public void onBtnDeleteClicked() {
        PromotionVoBase selected = getSelected();
        if (selected != null) {
            new PromptDialogHelper("确定删除？", "促销策略将被废弃，无法撤销。")
                    .addCloseButton("取消","CLOSE",null)
                    .addButton("确定","CHECK",e -> {
                        ResultMessage result = delete(selected.getId(), selected.getType());
                        if (result.equals(ResultMessage.Success)) {
                            new PromptDialogHelper("删除成功", "")
                                    .addCloseButton("好", "CHECK", e2 -> {
                                        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                                        updateItems();
                                    })
                                    .createAndShow();
                        } else {
                            new PromptDialogHelper("删除失败", "")
                                    .addCloseButton("好", "CHECK", e2 -> {
                                        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                                        updateItems();
                                    })
                                    .createAndShow();
                        }

                    })
                    .createAndShow();
        }
    }

    private ResultMessage delete(String id, PromotionType type) {
        ResultMessage result = null;
        try {
            switch (type) {
                case ComSalePromotion:
                    result = comSalePromotionBlService.delete(id);
                    break;
                case TotalPricePromotion:
                    result =  totalPricePromotionBlService.delete(id);
                    break;
                case ClientPromotion:
                    result =  clientPromotionBlService.delete(id);
                    break;
                default:
                    result =  ResultMessage.Failure;
            }
        } catch (PromotionInvalidStateException e1) {
            new PromptDialogHelper("删除失败", "促销策略生效中，无法删除。")
                    .addCloseButton("好", "CHECK", e2 -> {
                        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                        updateItems();
                    })
                    .createAndShow();
        }
        return result;
    }

    public void onBtnRefreshClicked() {
        updateItems();
    }

    public void onBtnDetailClicked() {
        TreeItem<PromotionInfoModel> selectedItem = tablePromotion.getSelectionModel().getSelectedItem();
        if(selectedItem.getValue()!=null) {
            PromotionVoBase promotion = selectedItem.getValue().getPromotion();
            PromptDialogHelper.start("促销策略详细信息","")
                    .setContent(promotion.promotionDetailUi().showContent(promotion).getComponent())
                    .addCloseButton("返回","REFRESH",null)
                    .createAndShow();
        }
        else {
            PromptDialogHelper.start("错误","请先选择一条促销策略。")
                    .addCloseButton("好的","DONE",null)
                    .createAndShow();
        }
    }
    private void addComSaleModel() {
        ComSalePromotionQueryVo comSalePromotionQueryVo = new ComSalePromotionQueryVo();
        PromotionVoBase[] result = comSalePromotionBlService.queryPromotion(comSalePromotionQueryVo);
        refreshContent(result);
    }

    private void refreshContent(PromotionVoBase[] result) {
        for(PromotionVoBase promotion:result) {
            if(!promotion.getState().equals(PromotionState.Draft)) {
                if (cbAbandon.isSelected())
                    promotionInfoModels.add(new PromotionInfoModel(promotion));
                else {
                    if (!promotion.getState().equals(PromotionState.Abandoned))
                        promotionInfoModels.add(new PromotionInfoModel(promotion));
                }
            }
        }
    }

    private void addTotalPriceModel() {
        TotalPricePromotionQueryVo totalPricePromotionQueryVo = new TotalPricePromotionQueryVo();
        PromotionVoBase[] result = totalPricePromotionBlService.queryPromotion(totalPricePromotionQueryVo);
        refreshContent(result);
    }

    private void addClientModel() {
        ClientPromotionQueryVo clientPromotionQueryVo = new ClientPromotionQueryVo();
        PromotionVoBase[] result = clientPromotionBlService.queryPromotion(clientPromotionQueryVo);
        refreshContent(result);
    }

}
