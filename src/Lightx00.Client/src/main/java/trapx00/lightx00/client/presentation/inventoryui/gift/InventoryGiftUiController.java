package trapx00.lightx00.client.presentation.inventoryui.gift;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryGiftBlService;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryGiftBlServiceFactory;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelection;
import trapx00.lightx00.client.presentation.commodityui.factory.CommodityUiFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryGiftVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.exception.presentation.NotCompleteException;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.util.BillHelper;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Date;

public class InventoryGiftUiController implements DraftContinueWritableUiController, ExternalLoadableUiController,ReversibleUi {

    public JFXTextField tfOperator;
    public JFXTextField tfDate;
    public JFXTextField tfId;
    public JFXButton btnAdd;
    public JFXButton btnDelete;
    public JFXButton btnSet;
    public JFXTreeTableView<InventoryGiftItemModel> inventoryGiftItems;
    public JFXTreeTableColumn<InventoryGiftItemModel, String> tcName;
    public JFXTreeTableColumn<InventoryGiftItemModel, String> tcPrice;
    public JFXTreeTableColumn<InventoryGiftItemModel, String> tcAmount;
    public JFXTreeTableColumn<InventoryGiftItemModel,String > tfAcutal;

    private ObjectProperty<CommodityVo> currentCommodity = new SimpleObjectProperty<>();
    private ObjectProperty<Date> currentDate = new SimpleObjectProperty<>();
    private ObjectProperty<EmployeeVo> currentEmployee = new SimpleObjectProperty<>();

    private ObservableList<InventoryGiftItemModel> inventoryGiftItemModelObservableList = FXCollections.observableArrayList();
    private InventoryGiftBlService blService= InventoryGiftBlServiceFactory.getInstance();
    private CommoditySelection commoditySelection= CommodityUiFactory.getCommoditySelectionUi();

    /**
     * Start continuing write a draft. Returns a External loaded ui package.
     * Overrides to return a specific ui controller.
     *
     * @param draft draft
     * @return External loaded ui package including a controller and the component.
     */
    @Override
    public ExternalLoadedUiPackage continueWriting(Draftable draft) {
        /**
         * 草稿功能实现。
         * 和对应单据详细界面一样，通过传入的参数初始化对应的控件元素信息。
         */
        InventoryGiftVo inventoryGiftVo = (InventoryGiftVo) draft;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        InventoryGiftUiController inventoryGiftUiController =  externalLoadedUiPackage.getController();
        inventoryGiftUiController.tfId.setText(inventoryGiftVo.getId().equals(BillHelper.refreshIdRequest) ? blService.getId() : inventoryGiftVo.getId());
        inventoryGiftUiController.tfDate.setText(DateHelper.fromDate(inventoryGiftVo.getDate()));
        inventoryGiftUiController.tfOperator.setText(inventoryGiftVo.getOperatorId());
        inventoryGiftUiController.addGiftItems(inventoryGiftVo.getGifts());
        return externalLoadedUiPackage;
    }

    public void initialize() {



        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(CommodityUiFactory.getCommoditySelectionUi().queryId(cellData.getValue().getValue().getPromotionCommodityObjectProperty().getCommodityId()).getName()));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getPromotionCommodityObjectProperty().getAmount())));
        tcPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getPromotionCommodityObjectProperty().getPrice())));

        currentDate.addListener(((observable, oldValue, newValue) -> {
            tfDate.setText(newValue == null ? "" : DateHelper.fromDate(newValue));
        }));

        currentEmployee.addListener(((observable, oldValue, newValue) -> {
            tfOperator.setText(newValue == null ? "" : String.format("%s(id: %s)", newValue.getId(), newValue.getName()));
        }));

        autofill();

        TreeItem<InventoryGiftItemModel> root = new RecursiveTreeItem<>(inventoryGiftItemModelObservableList, RecursiveTreeObject::getChildren);
        inventoryGiftItems.setRoot(root);
        inventoryGiftItems.setShowRoot(false);
        inventoryGiftItems.setEditable(true);
        inventoryGiftItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        FrameworkUiManager.getWholePane().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onBtnSubmitClicked();
            }
        });
    }


    public void addGiftItems(PromotionCommodity[] promotionCommodities) {
        for (PromotionCommodity promotionCommodity : promotionCommodities) {
            inventoryGiftItemModelObservableList.add(new InventoryGiftItemModel(promotionCommodity));
        }

    }
    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/gift/InventoryGiftUi.fxml").loadAndGetPackageWithoutException();
    }

    @Override
    public ExternalLoadedUiPackage revertReversible(Reversible reversible) {
        /**
         * 红冲功能实现。
         * 和草稿继续填写差不多，但是需要：
         * 1. 重新获取ID（因为红冲单子其实是一张新的UI单子）
         * 2. 取反数量。
         */
        InventoryGiftVo inventoryGiftVo = (InventoryGiftVo) reversible;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        InventoryGiftUiController inventoryGiftUiController = externalLoadedUiPackage.getController();
        inventoryGiftUiController.tfId.setText(inventoryGiftUiController.blService.getId());
        inventoryGiftUiController.currentEmployee.setValue(FrameworkUiManager.getCurrentEmployee());
        inventoryGiftUiController.currentDate.setValue(new Date());
        for (PromotionCommodity item : inventoryGiftVo.getGifts()) {
            item.setAmount(-item.getAmount());
        }
        inventoryGiftUiController.addGiftItems(inventoryGiftVo.getGifts());
        return externalLoadedUiPackage;
    }

    public void onBtnAddItemClicked() {
        CommodityUiFactory.getCommoditySelectionUi()
                .showCommoditySelectDialog(vo ->addItem(vo));

    }

    public void addItem(CommodityVo vo){
        InventoryGiftItemModel inventoryGiftItemModel=new InventoryGiftItemModel(new PromotionCommodity(vo.getId(),
                vo.getName(),vo.getRetailPrice(),0));
        new InventoryGiftItemModificationUi().show(vo.getId(),aDouble -> inventoryGiftItemModelObservableList.add(new InventoryGiftItemModel(
                new PromotionCommodity(vo.getId(),vo.getName(),vo.getRecentRetailPrice(),aDouble)
        )));


    }

    public void onBtnSetItemClicked(){
        InventoryGiftItemModel inventoryGiftItemModel=getSelected();
        if(inventoryGiftItemModel!=null){
            new InventoryGiftItemModificationUi().show(inventoryGiftItemModel.getPromotionCommodityObjectProperty().getCommodityId(),aDouble -> inventoryGiftItemModel.getPromotionCommodityObjectProperty().setAmount(aDouble));
            if(inventoryGiftItemModel.getPromotionCommodityObjectProperty().getAmount()>
                    commoditySelection.queryId(inventoryGiftItemModel.getPromotionCommodityObjectProperty().getCommodityId()).getAmount() )
            {
                PromptDialogHelper.start("失败","赠送数量超过库存数量");
                inventoryGiftItemModel.getPromotionCommodityObjectProperty().setAmount(0);
            }
        }

    }

    public InventoryGiftItemModel getSelected(){
        try {
            return inventoryGiftItems.getSelectionModel().getSelectedItem().getValue();
        } catch (Exception e) {
            new PromptDialogHelper("未选择！","请先选择一个赠送商品！")
                    .addCloseButton("好","CHECK", null)
                    .createAndShow();
            return null;
        }
    }

    public void onBtnDeleteItemClicked() {
        try {
            int index = inventoryGiftItems.getSelectionModel().getSelectedIndex();
            inventoryGiftItemModelObservableList.remove(index);
        } catch (Exception ignored) {

        }
    }

    private InventoryGiftVo getCurrentGiftVo() {
        if (tfId.getText().length() == 0 || currentEmployee == null || currentDate == null) {
            PromptDialogHelper.start("提交失败！","请选点击自动填写信息以自动填写ID、日期和操作员信息。")
                    .addCloseButton("好的","CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }

        return new InventoryGiftVo(
                blService.getId(),
                currentDate.getValue(),
                BillState.Draft,
                inventoryGiftItemModelObservableList.stream().map(InventoryGiftItemModel::getPromotionCommodityObjectProperty).toArray(PromotionCommodity[]::new),
                currentEmployee.getValue().getId()
        );
    }

    public void onBtnSubmitClicked() {
        try {
            InventoryGiftVo inventoryGiftVo=getCurrentGiftVo();
            PromptDialogHelper.start("确认单据", "").setContent(
                    inventoryGiftVo.billDetailUi().showContent(inventoryGiftVo).getComponent())
                    .addCloseButton("确定", "CHECK", e -> {
                        try {
                            blService.sumbit(inventoryGiftVo);
                            PromptDialogHelper.start("提交成功！", "你的单据已经提交成功！")
                                    .addCloseButton("继续填写", "EDIT", e1 -> {
                                        onBtnResetClicked();
                                        autofill();
                                    })
                                    .addCloseButton("返回主界面", "CHECK", e1 -> FrameworkUiManager.switchBackToHome())
                                    .createAndShow();
                        } catch (UncheckedRemoteException e1) {
                            PromptDialogHelper.start("提交失败！", "网络错误。详细信息：\n" + e1.getRemoteException().getMessage())
                                    .addCloseButton("好的", "CHECK", null)
                                    .createAndShow();
                        } catch (IdExistsException e1) {
                            PromptDialogHelper.start("提交失败", "ID已经存在，请重新获取ID！")
                                    .addCloseButton("好的", "CHECK", null)
                                    .createAndShow();
                        }
                    })
                    .addCloseButton("取消", "CLOSE", null)
                    .createAndShow();
        } catch (NotCompleteException ignored) {

        }
    }

    public void onBtnSaveAsDraftClicked() {
        saveAsDraft();

    }

    public void saveAsDraft(){
        try {
            blService.saveAsDraft(getCurrentGiftVo());
            PromptDialogHelper.start("保存草稿成功", "你的单据已经保存为草稿。")
                    .addCloseButton("好的", "CHECK", e -> FrameworkUiManager.switchBackToHome())
                    .createAndShow();
        } catch (NotCompleteException ignored) {
        } catch (UncheckedRemoteException e) {
            PromptDialogHelper.start("提交失败！", "网络错误。详细信息：\n" + e.getRemoteException().getMessage())
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        } catch (Exception e) {
            PromptDialogHelper.start("提交失败", "错误信息如下：\n" + e.getMessage())
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
        }
    }

    public void autofill(){
        try {
            tfId.setText(blService.getId());
            currentDate.setValue(new Date());
            currentEmployee.setValue(FrameworkUiManager.getCurrentEmployee());
        } catch (NoMoreBillException e) {
            PromptDialogHelper.start("ID不够！","当日ID已经达到99999，无法增加新的单据。")
                    .addCloseButton("好的","CHECK", null)
                    .createAndShow();
        }
    }
    public void onBtnResetClicked() {
        tfId.setText("");
        tfOperator.setText("");
        tfDate.setText("");
        currentDate.setValue(null);
        currentEmployee.setValue(null);
        currentCommodity.setValue(null);
        inventoryGiftItemModelObservableList.clear();
    }

    public void onBtnCancelClicked() {
        PromptDialogHelper.start("是否退出？", "是否保存草稿？")
                .addCloseButton("保存", "SAVE", e -> saveAsDraft())
                .addCloseButton("不保存", "DELETE", e -> FrameworkUiManager.switchBackToHome())
                .addCloseButton("取消", "CLOSE", null)
                .createAndShow();

    }





}
