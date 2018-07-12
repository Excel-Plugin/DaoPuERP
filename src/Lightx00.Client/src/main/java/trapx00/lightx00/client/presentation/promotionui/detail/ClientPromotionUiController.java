package trapx00.lightx00.client.presentation.promotionui.detail;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.NumberValidator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.Callback;
import trapx00.lightx00.client.blservice.promotionblservice.ClientPromotionBlService;
import trapx00.lightx00.client.blservice.promotionblservice.ClientPromotionBlServiceFactory;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelection;
import trapx00.lightx00.client.presentation.commodityui.factory.CommodityUiFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.presentation.helpui.validator.ValidatorHelper;
import trapx00.lightx00.client.presentation.promotionui.PromotionCommodityModel;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.manager.promotion.ClientPromotionVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.presentation.NotCompleteException;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.po.manager.promotion.PromotionState;
import trapx00.lightx00.shared.util.BillHelper;
import trapx00.lightx00.shared.util.DateHelper;

import java.time.LocalDate;
import java.util.Date;

public class ClientPromotionUiController implements DraftContinueWritableUiController, ExternalLoadableUiController {
    public JFXTextField tfId;
    public JFXDatePicker tfStartDate = new JFXDatePicker();
    public JFXDatePicker tfEndDate = new JFXDatePicker();
    public JFXComboBox<String> cbClientLevel = new JFXComboBox<>();
    public JFXTextField tfSalePrice;
    public JFXTextField tfCouponPrice;
    public JFXTreeTableView<PromotionCommodityModel> tbPromotionCommodity;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcId;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcName;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcPrice;
    public JFXTreeTableColumn<PromotionCommodityModel, String> tcAmount;
    public JFXButton btnAdd;
    public JFXButton btnDelete;
    public JFXButton btnSubmit;
    public JFXButton btnDraft;
    public JFXButton btnReset;
    public Label lbTotal;

    private ClientPromotionBlService blService = ClientPromotionBlServiceFactory.getInstance();
    private CommoditySelection commoditySelection = CommodityUiFactory.getCommoditySelectionUi();
    private ObservableList<PromotionCommodityModel> promotionCommodityModelObservableList = FXCollections.observableArrayList();
    final private ObservableList<String> ints = FXCollections.observableArrayList("1","2","3","4","5");

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/managerui/ClientPromotionUi.fxml").loadAndGetPackageWithoutException();
    }


    /**
     * Start continuing write a draft. Returns a ExternalLoadableUiController. It can be used to set the stage without casting to specific ui controller.
     * Overrides to return a specific ui controller.
     *
     * @param draft draft
     * @return a ExternalLoadableUiController
     */
    @Override
    public ExternalLoadedUiPackage continueWriting(Draftable draft) {
        /*
         * 草稿功能实现。
         * 和对应单据详细界面一样，通过传入的参数初始化对应的控件元素信息。
         */
        ClientPromotionVo clientPromotion = (ClientPromotionVo) draft;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        ClientPromotionUiController continueWriting = externalLoadedUiPackage.getController();
        continueWriting.tfId.setText(clientPromotion.getId());
        continueWriting.tfStartDate.setValue(DateHelper.dateToLocalDate(clientPromotion.getStartDate()));
        continueWriting.tfEndDate.setValue(DateHelper.dateToLocalDate(clientPromotion.getEndDate()));
        continueWriting.cbClientLevel.setValue(clientPromotion.getClientLevel()+"");
        continueWriting.tfCouponPrice.setText(clientPromotion.getCouponPrice()+"");
        continueWriting.addPromotionCommodities(clientPromotion.getPromotionCommodities());
        return externalLoadedUiPackage;
    }

    public void initialize() {
        tfId.setText(blService.getId());
        cbClientLevel.setItems(ints);

        ValidatorHelper.addDefaultDoubleValidator(tfSalePrice);
        ValidatorHelper.addDefaultDoubleValidator(tfCouponPrice);

        tbPromotionCommodity.setEditable(true);
        tcId.setCellValueFactory(cellData -> cellData.getValue().getValue().idProperty());
        tcName.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        tcPrice.setCellValueFactory(cellData -> new SimpleStringProperty(BillHelper.toFixed(cellData.getValue().getValue().getPrice())));
        tcAmount.setCellValueFactory(cellData -> new SimpleStringProperty(BillHelper.toFixed(cellData.getValue().getValue().getAmount())));
        tcAmount.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        tcAmount.setOnEditCommit((TreeTableColumn.CellEditEvent<PromotionCommodityModel,String> t)-> {
            (t.getTreeTableView().getTreeItem((t.getTreeTablePosition().getRow())).getValue()).setAmount(Double.parseDouble(t.getNewValue()));
            updateTotal();
        });
        tfStartDate.setDayCellFactory(startDayCellFactory);
        tfEndDate.setDayCellFactory(endDayCellFactory);

        promotionCommodityModelObservableList.addListener((ListChangeListener<PromotionCommodityModel>) c -> {
            double total = 0.0;
            for(PromotionCommodityModel model:promotionCommodityModelObservableList) {
                total+= model.getAmount()*model.getPrice();
            }
            lbTotal.setText(String.valueOf(total));
        });

        TreeItem<PromotionCommodityModel> root = new RecursiveTreeItem<>(promotionCommodityModelObservableList, RecursiveTreeObject::getChildren);
        tbPromotionCommodity.setRoot(root);
        tbPromotionCommodity.setShowRoot(false);
        tbPromotionCommodity.setEditable(true);
        tbPromotionCommodity.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void updateTotal() {
        double total = 0.0;
        for(PromotionCommodityModel model:promotionCommodityModelObservableList) {
            total+= model.getAmount()*model.getPrice();
        }
        lbTotal.setText(String.valueOf(total));
    }

    private ClientPromotionVo getCurrentClientPromotionVo() {
        double salePrice = 0.0;
        double couponPrice = 0.0;
        boolean ifSale = false,ifCoupon = false;
        PromotionCommodity[] promotionCommodities =  promotionCommodityModelObservableList.stream().map(PromotionCommodityModel::toPromotionCommodity).toArray(PromotionCommodity[]::new);
        if (tfId.getText().length() == 0) {
            PromptDialogHelper.start("提交失败！","请先获得促销策略编号。")
                    .addCloseButton("好的","CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
        else if (tfStartDate.getValue() == null || tfEndDate.getValue() == null) {
            PromptDialogHelper.start("提交失败！","请输入有效的促销策略生效时间区间。")
                    .addCloseButton("好的","CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
        else if (cbClientLevel.getValue() == null ) {
            PromptDialogHelper.start("提交失败！","请选择客户等级。")
                    .addCloseButton("好的","CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
        if (tfSalePrice.getText().length()!= 0) {
            salePrice = Integer.parseInt(tfSalePrice.getText());
            ifSale = true;
        }
        if (tfCouponPrice.getText().length()!=0) {
            couponPrice = Integer.parseInt(tfCouponPrice.getText());
            ifCoupon = true;
        }
        if(!ifSale && !ifCoupon && promotionCommodities==null){
            PromptDialogHelper.start("提交失败！","促销策略无效！请选择客户促销方式。")
                    .addCloseButton("好的","CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
        PromotionState state = PromotionState.Waiting;
        if(tfStartDate.getValue().isAfter(DateHelper.dateToLocalDate(new Date())))
            state = PromotionState.Active;
        return new ClientPromotionVo(
                tfId.getText(),
                DateHelper.fromLocalDate(tfStartDate.getValue()),
                DateHelper.fromLocalDate(tfEndDate.getValue()),
                state,
                Integer.parseInt(cbClientLevel.getValue()),
                couponPrice,
                promotionCommodities,
                salePrice
        );
    }

    public void onBtnSubmitClicked() {
        try {
            ClientPromotionVo promotion = getCurrentClientPromotionVo();
            PromptDialogHelper.start("确认当前促销策略", "").setContent(
                    promotion.promotionDetailUi().showContent(promotion).getComponent())
                    .addCloseButton("确定", "CHECK", e -> {
                        try {
                            blService.submit(promotion);
                            PromptDialogHelper.start("提交成功！", "客户类促销策略已经提交。")
                                    .addCloseButton("继续填写", "EDIT", e1 -> onBtnResetClicked())
                                    .addCloseButton("返回主界面", "CHECK", e1 -> FrameworkUiManager.switchBackToHome())
                                .createAndShow();
                        } catch (UncheckedRemoteException e1) {
                            PromptDialogHelper.start("提交失败！", "网络错误。")
                                    .addCloseButton("好的", "CHECK", null)
                                    .createAndShow();
                        } catch (IdExistsException e1) {
                            PromptDialogHelper.start("提交失败！", "促销策略编号已经存在，请重新获取编号。")
                                    .addCloseButton("好的", "CHECK", null)
                                    .createAndShow();
                        }
                    })
        .addCloseButton("取消", "CLOSE", null)
                .createAndShow();
    } catch (NotCompleteException ignored) {

    }

}

    public void onBtnDraftClicked() {
        try {
            blService.saveAsDraft(getCurrentClientPromotionVo());
            PromptDialogHelper.start("保存草稿成功","促销策略已经保存为草稿。")
                    .addCloseButton("好的","CHECK", e -> onBtnResetClicked())
                    .createAndShow();
        } catch (NotCompleteException ignored) {
        } catch (UncheckedRemoteException e) {
            PromptDialogHelper.start("提交失败！","网络错误。")
                    .addCloseButton("好的","CHECK", null)
                    .createAndShow();
        }
    }

    public void onBtnResetClicked() {
        tfId.setText(blService.getId());
        tfStartDate.setValue(null);
        tfEndDate.setValue(null);
        cbClientLevel.setValue(null);
        tfCouponPrice.setText("");
        promotionCommodityModelObservableList.clear();
    }

    public void onBtnAddGiftClicked() {
        commoditySelection.showCommoditySelectDialog(
                vo -> promotionCommodityModelObservableList.add(new PromotionCommodityModel(new PromotionCommodity(vo.getId(), vo.getName(),vo.getRetailPrice(),1)))
        );
    }

    public void onBtnDeleteGiftClicked () {
        try {
            int index = tbPromotionCommodity.getSelectionModel().getSelectedIndex();
            promotionCommodityModelObservableList.remove(index);
        } catch (Exception ignored) {

        }
    }

    private void addPromotionCommodities (PromotionCommodity[] promotionCommodities) {
        for (PromotionCommodity commodity : promotionCommodities) {
            promotionCommodityModelObservableList.add(
                    new PromotionCommodityModel(commodity));
        }

    }

    private final Callback<DatePicker, DateCell> endDayCellFactory = new Callback<DatePicker, DateCell>() {
        @Override
        public DateCell call(final DatePicker datePicker) {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item,empty);
                    if(tfStartDate.getValue() != null) {
                        if (item.isBefore(tfStartDate.getValue().plusDays(1))) {
                            setDisable(true);
                        }
                    }
                }

            };
        }
    };

    private final Callback<DatePicker, DateCell> startDayCellFactory = new Callback<DatePicker, DateCell>() {
        @Override
        public DateCell call(final DatePicker datePicker) {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item,empty);
                    if(item.isBefore(DateHelper.dateToLocalDate(new Date()))) {
                        setDisable(true);
                    }
                }

            };
        }
    };

}
