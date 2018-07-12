package trapx00.lightx00.client.presentation.inventoryui.warning;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import trapx00.lightx00.client.bl.commoditybl.CommodityInfo;
import trapx00.lightx00.client.bl.commoditybl.factory.CommodityInfoFactory;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryWarningBlService;
import trapx00.lightx00.client.blservice.inventoryblservice.InventoryWarningBlServiceFactory;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelectionItemModel;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryDetailBillVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.exception.presentation.NotCompleteException;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.inventorystaff.InventoryBillType;
import trapx00.lightx00.shared.po.inventorystaff.InventoryWarningItem;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;
import trapx00.lightx00.shared.util.BillHelper;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class InventoryWarningUiController implements DraftContinueWritableUiController, ExternalLoadableUiController,ReversibleUi {

    public JFXTextField tfOperator;
    public JFXTextField tfDate;
    public JFXTextField tfId;
    public JFXButton btnDelete;
    public JFXComboBox<Label> jfxComboBox=new JFXComboBox<>();
    public JFXTreeTableView<CommoditySelectionItemModel> inventoryGiftItems;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tcName;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tcId;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tcAmount;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tcStockAmount;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tcWarningWalue;

    public JFXTreeTableView<CommoditySelectionItemModel> commodityTable;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableNameColumn;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableSortColumn;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableIdColumn;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tableWarningColumn;

    public MenuItem modifyMenuItem;

    private ObjectProperty<CommodityVo> currentCommodity = new SimpleObjectProperty<>();
    private ObjectProperty<Date> currentDate = new SimpleObjectProperty<>();
    private ObjectProperty<EmployeeVo> currentEmployee = new SimpleObjectProperty<>();

    private ObservableList<CommoditySelectionItemModel> inventoryGiftItemModelObservableList = FXCollections.observableArrayList();
    public ObservableList<CommoditySelectionItemModel> commodityModels = FXCollections.observableArrayList();

    private CommodityInfo blService1= CommodityInfoFactory.getCommodityInfo();
    private InventoryWarningBlService blService= InventoryWarningBlServiceFactory.getInstance();


    /**
     * Start continuing write a draft. Returns a External loaded ui package.
     * Overrides to return a specific ui controller.
     *
     * @param draft draft
     * @return External loaded ui package including a controller and the component.
     */
    @Override
    public ExternalLoadedUiPackage continueWriting(Draftable draft) {
        InventoryDetailBillVo inventoryDetailBillVo = (InventoryDetailBillVo) draft;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        InventoryWarningUiController inventoryWarningUiController = externalLoadedUiPackage.getController();
        inventoryWarningUiController.tfId.setText(inventoryDetailBillVo.getId().equals(BillHelper.refreshIdRequest) ? blService.getId() : inventoryDetailBillVo.getId());
        inventoryWarningUiController.currentDate.setValue(inventoryDetailBillVo.getDate());
        inventoryWarningUiController.tfOperator.setText(inventoryDetailBillVo.getOperatorId());
        inventoryWarningUiController.jfxComboBox.setValue(new Label(inventoryDetailBillVo.getInventoryBillType().toString()));
        inventoryWarningUiController.addCommodityItem(inventoryDetailBillVo.getCommodities());
        return externalLoadedUiPackage;
    }

    public void addCommodityItem (InventoryWarningItem[] inventoryWarningItems){
        for(InventoryWarningItem inventoryWarningItem:inventoryWarningItems){
            inventoryGiftItemModelObservableList.add(new CommoditySelectionItemModel(
                    blService1.queryCommodity(new CommodityQueryVo().idEq(inventoryWarningItem.getId()))[0])
            );
        }
    }

    public void initialize() {
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityVoObjectProperty().getName()));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getActualAmount())));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getId())));
        tcStockAmount.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getAmount())));
        tcWarningWalue.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getWarningValue())));


        currentDate.addListener(((observable, oldValue, newValue) -> {
            tfDate.setText(newValue == null ? "" : DateHelper.fromDate(newValue));
        }));

        currentEmployee.addListener(((observable, oldValue, newValue) -> {
            tfOperator.setText(newValue == null ? "" : newValue.getName());
        }));

        auto();



        TreeItem<CommoditySelectionItemModel> root = new RecursiveTreeItem<>(inventoryGiftItemModelObservableList, RecursiveTreeObject::getChildren);
        inventoryGiftItems.setRoot(root);
        inventoryGiftItems.setShowRoot(false);
        inventoryGiftItems.setEditable(true);
        inventoryGiftItems.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        jfxComboBox.getItems().add(new Label("Loss"));
        jfxComboBox.getItems().add(new Label("OverFlow"));
        jfxComboBox.getItems().add(new Label("Warning"));

        modifyMenuItem.setOnAction((ActionEvent t)->{
                CommodityVo selected=commodityTable.getSelectionModel().getSelectedItem().getValue().getCommodityVoObjectProperty();
                new InventoryActualValue().show(selected,this::updateCommodity);

        });

        initTable();
    }

    public void updateCommodity(){
        CommodityVo[] queryResult = blService1.queryCommodity(new CommodityQueryVo());
        commodityModels.clear();
        commodityModels.addAll(Arrays.stream(queryResult).map(CommoditySelectionItemModel::new).collect(Collectors.toList()));
    }
    @FXML

    private void initTable() {
        tableWarningColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getWarningValue())));
        tableNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityVoObjectProperty().getName()));
        tableIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getId())));
        tableSortColumn.setCellValueFactory(cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getCommodityVoObjectProperty().getAmount())));

        commodityTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                CommodityVo selected = getSelected();
                new InventoryWarningChoose().show(selected.getId(),aDouble ->addQWE(aDouble,selected));
            }});

        TreeItem<CommoditySelectionItemModel> root = new RecursiveTreeItem<>(commodityModels, RecursiveTreeObject::getChildren);
        commodityTable.setRoot(root);
        commodityTable.setShowRoot(false);
        commodityTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //支持多选，没有这句话就是不支持

        CommodityVo[] queryResult = blService1.queryCommodity(new CommodityQueryVo());
        commodityModels.clear();
        commodityModels.addAll(Arrays.stream(queryResult).map(CommoditySelectionItemModel::new).collect(Collectors.toList()));

        FrameworkUiManager.getWholePane().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onBtnSubmitClicked();
            }
        });
    }

    public void addQWE(double a,CommodityVo vo){
        vo.setActualAmount(a);
        System.out.println();
        if(jfxComboBox.getSelectionModel().getSelectedItem()==null){
            inventoryGiftItemModelObservableList.
                    add(new CommoditySelectionItemModel(vo));
            if(vo.getAmount()>vo.getActualAmount())
                jfxComboBox.getSelectionModel().select(0);
            else if(vo.getActualAmount()>vo.getAmount())
                jfxComboBox.getSelectionModel().select(1);
            else
                jfxComboBox.getSelectionModel().select(2);
        }else{
            if(jfxComboBox.getSelectionModel().getSelectedItem().getText().equals("Loss")){
                System.out.println("loss");
                if(vo.getActualAmount()<vo.getAmount())
                    inventoryGiftItemModelObservableList.
                            add(new CommoditySelectionItemModel(vo));
                else
                    PromptDialogHelper.start("添加失败","该单据为报损单").createAndShow();
            }else if(jfxComboBox.getSelectionModel().getSelectedItem().getText().equals("OverFlow")){
                if(vo.getAmount()<vo.getActualAmount())
                    inventoryGiftItemModelObservableList.
                            add(new CommoditySelectionItemModel(vo));
                else{
                    PromptDialogHelper.start("添加失败","该单据为报溢单").createAndShow();
                }

            }else{
                System.out.println("warning");
                if(vo.getActualAmount()==vo.getAmount())
                    inventoryGiftItemModelObservableList.
                            add(new CommoditySelectionItemModel(vo));
                else
                    PromptDialogHelper.start("添加失败","该单据为报警单").createAndShow();
            }
        }

    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/warning/InventoryWarningUi.fxml").loadAndGetPackageWithoutException();
    }

    @Override
    public ExternalLoadedUiPackage revertReversible(Reversible reversible) {
        /**
         * 红冲功能实现。
         * 和草稿继续填写差不多，但是需要：
         * 1. 重新获取ID（因为红冲单子其实是一张新的UI单子）
         * 2. 取反数量。
         */
        InventoryDetailBillVo inventoryDetailBillVo = (InventoryDetailBillVo) reversible;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        InventoryWarningUiController inventoryWarningUiController = externalLoadedUiPackage.getController();
        inventoryWarningUiController.tfId.setText(inventoryWarningUiController.blService.getId());
        inventoryWarningUiController.currentEmployee.setValue(FrameworkUiManager.getCurrentEmployee());
        inventoryWarningUiController.jfxComboBox.setValue(new Label(inventoryDetailBillVo.getInventoryBillType().toString()));
        inventoryWarningUiController.currentDate.setValue(new Date());
        for (InventoryWarningItem item : inventoryDetailBillVo.getCommodities()) {
            item.setDelta(-item.getDelta());
        }
        inventoryWarningUiController.addCommodityItem(inventoryDetailBillVo.getCommodities());
        return externalLoadedUiPackage;
    }



    public CommodityVo getSelected(){
       return commodityTable.getSelectionModel().getSelectedItem().getValue().getCommodityVoObjectProperty();
    }

    public void onBtnDeleteItemClicked() {
        try {
            int index = inventoryGiftItems.getSelectionModel().getSelectedIndex();
            inventoryGiftItemModelObservableList.remove(index);
        } catch (Exception ignored) {

        }
    }

    private InventoryDetailBillVo getInventoryWanringVo() {
        if (tfId.getText().length() == 0 || currentEmployee == null || currentDate == null) {
            PromptDialogHelper.start("提交失败！","请选点击自动填写信息以自动填写ID、日期和操作员信息。")
                    .addCloseButton("好的","CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
        if (jfxComboBox.getValue().getText().length()==0) {
            PromptDialogHelper.start("提交失败！","请先选择单据类型。")
                    .addCloseButton("好的","CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
        InventoryBillType inventoryBillType;
        switch (jfxComboBox.getValue().getText()) {
            case "Loss":
                inventoryBillType = InventoryBillType.Loss;
                break;
            case "OverFlow":
                inventoryBillType = InventoryBillType.Overflow;
                break;
            default:
                inventoryBillType = InventoryBillType.Warning;
                break;
        }

        CommodityVo [] commodityVos=inventoryGiftItemModelObservableList.stream().map(CommoditySelectionItemModel::getCommodityVoObjectProperty).toArray(CommodityVo[]::new);
        InventoryWarningItem[] inventoryWarningItems=new InventoryWarningItem[commodityVos.length];
        for(int i=0;i<commodityVos.length;i++){
            inventoryWarningItems[i]=new InventoryWarningItem(commodityVos[i].getId(),commodityVos[i].getActualAmount()-commodityVos[i].getAmount(),commodityVos[i].getRecentRetailPrice()
            );
        }
        return new InventoryDetailBillVo(
                blService.getId(),
                currentDate.getValue(),
                BillState.Draft,
                currentEmployee.getValue().getId(),
               inventoryWarningItems,
                inventoryBillType
        );
    }

    public void onBtnSubmitClicked() {
        try {
            InventoryDetailBillVo inventoryDetailBillVo=getInventoryWanringVo();
            PromptDialogHelper.start("确认单据", "").setContent(
                    inventoryDetailBillVo.billDetailUi().showContent(inventoryDetailBillVo).getComponent())
                    .addCloseButton("确定", "CHECK", e -> {
                        try {
                            blService.submit(inventoryDetailBillVo);
                            PromptDialogHelper.start("提交成功！", "你的单据已经提交成功！")
                                    .addCloseButton("继续填写", "EDIT", e1 -> {
                                        onBtnResetClicked();
                                        auto();
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
        save();
    }
    public void save(){
        try {
            blService.saveAsDraft(getInventoryWanringVo());
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

    public void onBtnResetClicked() {
        tfId.setText("");
        tfOperator.setText("");
        tfDate.setText("");
        jfxComboBox.setValue(new Label());
        currentDate.setValue(null);
        currentEmployee.setValue(null);
        currentCommodity.setValue(null);
        inventoryGiftItemModelObservableList.clear();
    }
    public void onBtnSetItemClicked(){
        CommodityVo commodityVo=getSelected();
        if(commodityVo!=null){
            new InventoryWarningModificationUi().show(commodityVo.getId(),aDouble -> inventoryGiftItems.getSelectionModel().getSelectedItem().
                    getValue().getCommodityVoObjectProperty().setActualAmount(aDouble));
        }

    }

    public void auto() {
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

    public void onBtnCancelClicked() {
        PromptDialogHelper.start("是否退出？", "是否保存草稿？")
                .addCloseButton("保存", "SAVE", e -> save())
                .addCloseButton("不保存", "DELETE", e -> FrameworkUiManager.switchBackToHome())
                .addCloseButton("取消", "CLOSE", null)
                .createAndShow();

    }
    public void onAutoFilledClicked(){
        CommodityVo [] commodityVos=blService1.queryCommodity(new CommodityQueryVo());
        inventoryGiftItemModelObservableList.clear();
        for(CommodityVo temp:commodityVos){
            if(temp.getAmount()<temp.getWarningValue()){
                temp.setActualAmount(temp.getAmount());
                blService1.modify(temp);
                jfxComboBox.getSelectionModel().select(2);
                inventoryGiftItemModelObservableList.add(new CommoditySelectionItemModel(temp));
            }
        }
    }



}
