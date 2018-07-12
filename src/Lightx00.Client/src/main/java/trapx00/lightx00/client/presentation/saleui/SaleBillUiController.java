package trapx00.lightx00.client.presentation.saleui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.EmployeeInfoFactory;
import trapx00.lightx00.client.bl.commoditybl.CommodityInfo;
import trapx00.lightx00.client.bl.commoditybl.factory.CommodityInfoFactory;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlService;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlServiceFactory;
import trapx00.lightx00.client.blservice.saleblservice.SaleBillBlService;
import trapx00.lightx00.client.blservice.saleblservice.SaleBillBlServiceFactory;
import trapx00.lightx00.client.presentation.adminui.EmployeeSelection;
import trapx00.lightx00.client.presentation.adminui.factory.UserManagementUiFactory;
import trapx00.lightx00.client.presentation.clientui.ClientInfoUi;
import trapx00.lightx00.client.presentation.clientui.factory.ClientInfoUiFactory;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelection;
import trapx00.lightx00.client.presentation.commodityui.factory.CommodityUiFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.presentation.helpui.validator.ValidatorHelper;
import trapx00.lightx00.client.presentation.inventoryui.CommodityItemModel;
import trapx00.lightx00.client.presentation.promotionui.PromotionSelection;
import trapx00.lightx00.client.presentation.promotionui.factory.PromotionSelectionFactory;
import trapx00.lightx00.client.presentation.saleui.factory.SaleCommodityFillUiFactory;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.client.vo.manager.promotion.ClientPromotionVo;
import trapx00.lightx00.client.vo.manager.promotion.ComSalePromotionVo;
import trapx00.lightx00.client.vo.manager.promotion.PromotionVoBase;
import trapx00.lightx00.client.vo.manager.promotion.TotalPricePromotionVo;
import trapx00.lightx00.client.vo.salestaff.SaleBillVo;
import trapx00.lightx00.client.vo.salestaff.SaleStaffVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.exception.presentation.NotCompleteException;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.manager.promotion.PromotionCommodity;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.po.salestaff.SaleStaffType;
import trapx00.lightx00.shared.queryvo.CommodityQueryVo;
import trapx00.lightx00.shared.util.BillHelper;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Date;

public class SaleBillUiController implements DraftContinueWritableUiController, ExternalLoadableUiController, ReversibleUi {

    @FXML
    VBox dialogContainer;
    @FXML
    JFXTextField tfBillId;
    @FXML
    JFXTextField tfSalesmanId;
    @FXML
    JFXTextField tfSalesmanName;
    @FXML
    JFXTextField tfOperator;
    @FXML
    JFXTextField tfDate;
    @FXML
    JFXTextField tfClientId;
    @FXML
    JFXTextField tfClientName;
    @FXML
    JFXTextField tfClientLevel;
    @FXML
    JFXComboBox<String> cbRepository;
    @FXML
    JFXTextField tfOriginTotal;
    @FXML
    JFXTextField tfMinusProfits;
    @FXML
    JFXTextField tfToken;
    @FXML
    JFXTextField tfPromotionId;
    @FXML
    JFXTextField tfGiftToken;
    @FXML
    JFXTextField tfComment;
    @FXML
    private JFXTextField tfUltiTotal;
    @FXML
    private JFXTreeTableView<CommodityItemModel> tbCommodityList;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityIdColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityNameColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityTypeColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityNumberColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityPriceColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityTotalColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcCommodityCommentColumn;

    @FXML
    private JFXTreeTableView<CommodityItemModel> tbGiftList;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftIdColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftNameColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftTypeColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftNumberColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftPriceColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftTotalColumn;
    @FXML
    private JFXTreeTableColumn<CommodityItemModel, String> tcGiftCommentColumn;

    private ObjectProperty<Date> currentDate = new SimpleObjectProperty<>();
    private ObjectProperty<EmployeeVo> currentEmployee = new SimpleObjectProperty<>();
    private SaleBillBlService blService = SaleBillBlServiceFactory.getInstance();
    private CommodityInfo commodityInfo = CommodityInfoFactory.getCommodityInfo();
    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
    private ObservableList<CommodityItemModel> commodityItemModelObservableList = FXCollections.observableArrayList();
    private ObservableList<CommodityItemModel> giftItemModelObservableList = FXCollections.observableArrayList();
    private ClientInfoUi clientInfoUi = ClientInfoUiFactory.getClientInfoUi();
    private EmployeeSelection employeeSelection = UserManagementUiFactory.getEmployeeSelectionUi();
    private ClientBlService clientBlService = ClientBlServiceFactory.getInstance();
    private CommoditySelection commoditySelection = CommodityUiFactory.getCommoditySelectionUi();
    private SaleCommodityFillUiController saleCommodityFillUiController = SaleCommodityFillUiFactory.getSaleCommodityFillUiController();
    private PromotionSelection promotionSelection = PromotionSelectionFactory.getPromotionSelection();
    private PromotionVoBase promotionVo = null;
    private StringProperty tfClientIdProperty = new SimpleStringProperty("");
    private StringProperty tfClientNameProperty = new SimpleStringProperty("");
    private StringProperty tfSalesmanIdProperty = new SimpleStringProperty("");
    private StringProperty tfSalesmanNameProperty = new SimpleStringProperty("");

    /**
     * Start continuing write a draft. Returns a ExternalLoadableUiController. It can be used to set the stage without casting to specific ui controller.
     * Overrides to return a specific ui controller.
     *
     * @param draft draft
     * @return a ExternalLoadableUiController
     */
    @Override
    public ExternalLoadedUiPackage continueWriting(Draftable draft) {
        currentEmployee.set(FrameworkUiManager.getCurrentEmployee());
        SaleBillVo saleBillVo = (SaleBillVo) draft;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        SaleBillUiController saleBillUiController = externalLoadedUiPackage.getController();
        saleBillUiController.tfBillId.setText(saleBillVo.getId().equals(BillHelper.refreshIdRequest) ? blService.getId() : saleBillVo.getId());
        saleBillUiController.tfDate.setText(saleBillVo.getDate().toString());
        saleBillUiController.tfSalesmanId.setText(saleBillVo.getSalesmanId());
        saleBillUiController.tfSalesmanName.setText(employeeInfo.queryById(saleBillVo.getSalesmanId()).getName());
        saleBillUiController.tfOperator.setText(String.format("%s(id: %s)", currentEmployee.getValue().getName(), currentEmployee.getValue().getId()));
        saleBillUiController.tfClientId.setText(saleBillVo.getClientId());
        saleBillUiController.tfClientName.setText(clientBlService.queryById(saleBillVo.getClientId()).getName());
        saleBillUiController.tfClientLevel.setText(clientBlService.queryById(saleBillVo.getClientId()).getClientLevel() + "");
        saleBillUiController.cbRepository.setValue(saleBillVo.getRepository() + "");
        saleBillUiController.tfOriginTotal.setText(saleBillVo.getOriginTotal() + "");
        saleBillUiController.tfMinusProfits.setText(saleBillVo.getMinusProfits() + "");
        saleBillUiController.tfToken.setText(saleBillVo.getToken() + "");
        saleBillUiController.tfUltiTotal.setText(saleBillVo.getUltiTotal() + "");
        saleBillUiController.tfPromotionId.setText(saleBillVo.getPromotionId());
        saleBillUiController.tfGiftToken.setText(saleBillVo.getGiftToken() + "");
        saleBillUiController.tfComment.setText(saleBillVo.getComment());
        saleBillUiController.addCommodityListItems(saleBillVo.getCommodityList());
        saleBillUiController.addGiftListItems(saleBillVo.getGiftList());
        return externalLoadedUiPackage;
    }

    public void addCommodityListItems(CommodityItem[] commodityItems) {
        for (CommodityItem commodityItem : commodityItems) {
            commodityItemModelObservableList.add(new CommodityItemModel(commodityItem));
        }
    }

    public void addGiftListItems(CommodityItem[] commodityItems) {
        for (CommodityItem commodityItem : commodityItems) {
            giftItemModelObservableList.add(new CommodityItemModel(commodityItem));
        }
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/saleui/SaleBillUi.fxml").loadAndGetPackageWithoutException();
    }

    public void initialize() {
        currentDate.addListener(((observable, oldValue, newValue) -> {
            tfDate.setText(newValue == null ? "" : DateHelper.fromDate(newValue));
        }));

        currentEmployee.addListener(((observable, oldValue, newValue) -> {
            tfOperator.setText(newValue == null ? "" : String.format("%s(id: %s)", newValue.getName(), newValue.getId()));
        }));

        tcCommodityIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getCommodityId()));
        tcCommodityNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getName()));
        tcCommodityTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getType()));
        tcCommodityNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getNumber() + ""));
        tcCommodityPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getPrice() + ""));
        tcCommodityTotalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getTotal() + ""));
        tcCommodityCommentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getComment()));
        TreeItem<CommodityItemModel> root = new RecursiveTreeItem<>(commodityItemModelObservableList, RecursiveTreeObject::getChildren);
        tbCommodityList.setRoot(root);
        tbCommodityList.setShowRoot(false);

        tcGiftIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getCommodityId()));
        tcGiftNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getName()));
        tcGiftTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getType()));
        tcGiftNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getNumber() + ""));
        tcGiftPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getPrice() + ""));
        tcGiftTotalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getTotal() + ""));
        tcGiftCommentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCommodityItemObjectProperty().getComment()));
        TreeItem<CommodityItemModel> rootGift = new RecursiveTreeItem<>(giftItemModelObservableList, RecursiveTreeObject::getChildren);
        tbGiftList.setRoot(rootGift);
        tbGiftList.setShowRoot(false);

        commodityItemModelObservableList.addListener(new SaleBillUiController.ListHandler());
        tfClientId.textProperty().bindBidirectional(tfClientIdProperty);
        tfClientName.textProperty().bindBidirectional(tfClientNameProperty);
        tfSalesmanId.textProperty().bindBidirectional(tfSalesmanIdProperty);
        tfSalesmanName.textProperty().bindBidirectional(tfSalesmanNameProperty);

        ObservableList<String> stringObservableList = FXCollections.observableArrayList(
            "1", "2", "3"
        );
        cbRepository.setItems(stringObservableList);
        cbRepository.setValue("1");
        tfMinusProfits.setText("0");
        tfToken.setText("0");
        tfGiftToken.setText("0");
        autofill();

        ValidatorHelper.addDefaultRequiredValidator(tfClientId);
        ValidatorHelper.addDefaultRequiredValidator(tfClientName);
        ValidatorHelper.addDefaultRequiredValidator(tfSalesmanId);
        ValidatorHelper.addDefaultRequiredValidator(tfSalesmanName);
        ValidatorHelper.addDefaultDoubleValidator(tfGiftToken);
        ValidatorHelper.addDefaultDoubleValidator(tfToken);
        ValidatorHelper.addDefaultDoubleValidator(tfMinusProfits);
        ValidatorHelper.addDefaultDoubleValidator(tfToken);

        tfMinusProfits.setOnKeyReleased(event -> {
            if (tfMinusProfits.validate() || tfMinusProfits.getText().length() == 0) {
                new ListHandler().change();
            }
        });
        tfToken.setOnKeyReleased(event -> {
            if (tfMinusProfits.validate() || tfMinusProfits.getText().length() == 0) {
                new ListHandler().change();
            }
        });
        initHotKey();
    }

    private void initHotKey() {
        FrameworkUiManager.getWholePane().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onBtnSubmitClicked();
            }
        });
    }

    /**
     * Revert a reversible.
     *
     * @param reversible reversible
     * @return External loaded ui package including a controller and the component.
     */
    @Override
    public ExternalLoadedUiPackage revertReversible(Reversible reversible) {
        currentEmployee.set(FrameworkUiManager.getCurrentEmployee());
        SaleBillVo saleBillVo = (SaleBillVo) reversible;
        saleBillVo.setUltiTotal(-saleBillVo.getUltiTotal());
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        SaleBillUiController saleBillUiController = externalLoadedUiPackage.getController();
        saleBillUiController.tfBillId.setText(blService.getId());
        saleBillUiController.tfDate.setText(saleBillVo.getDate().toString());
        saleBillUiController.tfSalesmanId.setText(saleBillVo.getSalesmanId());
        saleBillUiController.tfSalesmanName.setText(employeeInfo.queryById(saleBillVo.getSalesmanId()).getName());
        saleBillUiController.tfOperator.setText(String.format("%s(id: %s)", currentEmployee.getValue().getName(), currentEmployee.getValue().getId()));
        saleBillUiController.tfClientId.setText(saleBillVo.getClientId());
        saleBillUiController.tfClientName.setText(clientBlService.queryById(saleBillVo.getClientId()).getName());
        saleBillUiController.tfClientLevel.setText(clientBlService.queryById(saleBillVo.getClientId()).getClientLevel() + "");
        saleBillUiController.cbRepository.setValue(saleBillVo.getRepository() + "");
        saleBillUiController.tfOriginTotal.setText(saleBillVo.getOriginTotal() + "");
        saleBillUiController.tfMinusProfits.setText(saleBillVo.getMinusProfits() + "");
        saleBillUiController.tfToken.setText(saleBillVo.getToken() + "");
        saleBillUiController.tfUltiTotal.setText(saleBillVo.getUltiTotal() + "");
        saleBillUiController.tfPromotionId.setText(saleBillVo.getPromotionId());
        saleBillUiController.tfGiftToken.setText(saleBillVo.getGiftToken() + "");
        saleBillUiController.tfComment.setText(saleBillVo.getComment());
        saleBillUiController.addCommodityListItems(saleBillVo.getCommodityList());
        saleBillUiController.addGiftListItems(saleBillVo.getGiftList());
        return externalLoadedUiPackage;
    }

    @FXML
    private void onClientClicked() {
        clientInfoUi.showClientSelectDialog(x -> {
            tfClientId.setText(x.getId());
            tfClientName.setText(x.getName());
            tfClientLevel.setText(x.getClientLevel() + "");
            tfClientId.validate();
            tfClientName.validate();
        });
    }

    @FXML
    private void onEmployeeClicked() {
        employeeSelection.showEmployeeSelectDialog(x -> {
            tfSalesmanId.setText(x.get(0).getId());
            tfSalesmanName.setText(x.get(0).getName());
            tfSalesmanName.validate();
            tfSalesmanId.validate();
        });
    }

    @FXML
    private void onBtnCancelClicked() {
        PromptDialogHelper.start("是否要存入草稿箱", null)
            .addCloseButton("存入", "DONE", e -> saveAsDraft())
            .addCloseButton("不存入", "CLOSE", e -> FrameworkUiManager.switchBackToHome())
            .addCloseButton("取消", "UNDO", null)
            .createAndShow();
    }

    @FXML
    private void onBtnAddItemClicked() {
        commoditySelection.showCommoditySelectDialog(x -> {
            saleCommodityFillUiController.showSaleCommodityFillDialog(y -> {
                double tempAmount = y.getNumber();
                for (CommodityItemModel commodityItemModel : commodityItemModelObservableList) {
                    if (commodityItemModel.getCommodityItemObjectProperty().getCommodityId().equals(x.getId())) {
                        tempAmount += commodityItemModel.getCommodityItemObjectProperty().getNumber();
                    }
                }
                for (CommodityItemModel commodityItemModel : giftItemModelObservableList) {
                    if (commodityItemModel.getCommodityItemObjectProperty().getCommodityId().equals(x.getId())) {
                        tempAmount += commodityItemModel.getCommodityItemObjectProperty().getNumber();
                    }
                }
                if (x.getAmount() < tempAmount) {
                    PromptDialogHelper.start("超出已有库存数量，请重新填写", null)
                        .addCloseButton("确定", "DONE", null)
                        .createAndShow();
                } else {
                    commodityItemModelObservableList.add(new CommodityItemModel(new CommodityItem(x.getId(), x.getName(), x.getType(), y.getNumber(), x.getPurchasePrice(), x.getPurchasePrice() * y.getNumber(), y.getComment())));
                }
            });
        });
    }

    @FXML
    private void onBtnDeleteItemClicked() {
        ObservableList<Integer> commodityIndexList = tbCommodityList.getSelectionModel().getSelectedIndices();
        for (int index : commodityIndexList) {
            commodityItemModelObservableList.remove(index);
        }
    }

    private CommodityItem[] promotionToCommodityItem(PromotionCommodity[] promotionCommodities) {
        CommodityItem[] commodityItems = new CommodityItem[promotionCommodities.length];
        for (int i = 0; i < promotionCommodities.length; i++) {
            CommodityVo commodityVo = commodityInfo.queryCommodity(new CommodityQueryVo().idEq(promotionCommodities[i].getCommodityId()))[0];
            commodityItems[i] = new CommodityItem(commodityVo.getId(), commodityVo.getName(), commodityVo.getType(), promotionCommodities[i].getAmount(), commodityVo.getRetailPrice(), promotionCommodities[i].getAmount() * commodityVo.getRetailPrice(), "赠品");
        }
        return commodityItems;
    }

    private double calculateMinusProfit(CommodityItem[] commodityItems, double onSalePrice) {
        //最大组合数
        int maxCom = 1000000000;
        double originTotal = 0;
        for (CommodityItem commodityItem : commodityItems) {
            for (CommodityItemModel commodityItemModel : commodityItemModelObservableList) {
                if (commodityItem.getCommodityId().equals(commodityItemModel.getCommodityItemObjectProperty().getCommodityId())) {
                    int temp = (int) (commodityItemModel.getCommodityItemObjectProperty().getNumber() / commodityItem.getNumber());
                    if (maxCom > temp) {
                        maxCom = temp;
                    }
                    originTotal += commodityItemModel.getCommodityItemObjectProperty().getPrice();
                    break;
                }
            }
        }
        return maxCom * (originTotal - onSalePrice);
    }

    @FXML
    private void onBtnGetPromotionClicked() {
        giftItemModelObservableList.clear();
        promotionSelection.showEmployeeSelectDialog(
            promotionVoBase -> {
                promotionVo = promotionVoBase;
                switch (promotionVoBase.getType()) {
                    case ClientPromotion:
                        ClientPromotionVo clientPromotionVo = (ClientPromotionVo) promotionVoBase;
                        tfGiftToken.setText(clientPromotionVo.getCouponPrice() + "");
                        new ListHandler().change();
                        addGiftListItems(promotionToCommodityItem(promotionVoBase.getPromotionCommodities()));
                        break;
                    case ComSalePromotion:
                        ComSalePromotionVo comSalePromotionVo = (ComSalePromotionVo) promotionVoBase;
                        break;
                    case TotalPricePromotion:
                        TotalPricePromotionVo totalPricePromotionVo = (TotalPricePromotionVo) promotionVoBase;
                        tfGiftToken.setText((((int) Double.parseDouble(tfUltiTotal.getText())) / totalPricePromotionVo.getTotalPrice()) * totalPricePromotionVo.getCouponPrice() + "");
                        addGiftListItems(promotionToCommodityItem(promotionVoBase.getPromotionCommodities()));
                        break;
                }
                tfPromotionId.setText(promotionVoBase.getId());
                tfMinusProfits.setText(calculateOnSalePrice(promotionVoBase)+"");
                (new ListHandler()).change();//修改总价格
            },
            getCurrentSaleBillVo()
        );
        promotionVo = null;
        tfPromotionId.clear();
        tfGiftToken.setText("0");
    }

    private double calculateOnSalePrice(PromotionVoBase promotionVoBase) {
        double minusProfit = 0;
        if(promotionVoBase==null){
           return minusProfit;
        }
        switch (promotionVoBase.getType()) {
            case ClientPromotion:
                ClientPromotionVo clientPromotionVo = (ClientPromotionVo) promotionVoBase;
                minusProfit = clientPromotionVo.getSalePrice();
                new ListHandler().change();
                break;
            case ComSalePromotion:
                ComSalePromotionVo comSalePromotionVo = (ComSalePromotionVo) promotionVoBase;
                minusProfit = calculateMinusProfit(promotionToCommodityItem(promotionVoBase.getPromotionCommodities()), comSalePromotionVo.getOnSalePrice());
                break;
            case TotalPricePromotion:
                minusProfit = 0;
                break;
        }
        return minusProfit;
    }

    @FXML
    private void onBtnSubmitClicked() {
        try {
            SaleBillVo saleBillVo = getCurrentSaleBillVo();

            if (currentEmployee.getValue().getPosition() == EmployeePosition.SaleStaff) {
                int staffOnSale = ((SaleStaffVo) currentEmployee.getValue()).getSaleStaffType() == SaleStaffType.Manager ? 5000 : 1000;
                if (calculateOnSalePrice(promotionVo) + staffOnSale < Double.parseDouble(tfMinusProfits.getText())) {
                    PromptDialogHelper.start("失败", "超出权限可折扣金额")
                        .addCloseButton("确定", "CHECK", null)
                        .createAndShow();
                    return;
                }
            }

            PromptDialogHelper.start("确认单据", "").setContent(
                saleBillVo.billDetailUi().showContent(saleBillVo).getComponent())
                .addCloseButton("确定", "CHECK", e -> {
                    submit();
                })
                .addCloseButton("取消", "CLOSE", null)
                .createAndShow();
            FrameworkUiManager.getWholePane().setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    initHotKey();
                    FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                    submit();
                }
            });
        } catch (NotCompleteException ignored) {

        }

    }

    private void submit() {
        try {
            blService.submit(getCurrentSaleBillVo());
            PromptDialogHelper.start("提交成功！", "你的单据已经提交成功！")
                .addCloseButton("继续填写", "EDIT", e1 -> {
                    onBtnResetClicked();
                    autofill();
                })
                .addCloseButton("返回主界面", "CHECK", e1 -> FrameworkUiManager.switchBackToHome())
                .createAndShow();
            FrameworkUiManager.getWholePane().setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    initHotKey();
                    FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                    onBtnResetClicked();
                    autofill();
                }
            });
        } catch (UncheckedRemoteException e1) {
            PromptDialogHelper.start("提交失败！", "网络错误。详细信息：\n" + e1.getRemoteException().getMessage())
                .addCloseButton("好的", "CHECK", null)
                .createAndShow();
        } catch (IdExistsException e1) {
            PromptDialogHelper.start("提交失败", "ID已经存在，请重新获取ID！")
                .addCloseButton("好的", "CHECK", null)
                .createAndShow();
        }
    }

    public boolean validateAll() {
        return tfClientId.validate()&&tfClientName.validate()&&tfSalesmanId.validate()&&tfSalesmanName.validate()&&tfGiftToken.validate()&&tfToken.validate()&&tfMinusProfits.validate()&&tfToken.validate();
    }

    private SaleBillVo getCurrentSaleBillVo() {
        if (!validateAll()) {
            PromptDialogHelper.start("提交失败！", "请先填写完单据。")
                .addCloseButton("好的", "CHECK", null)
                .createAndShow();
            throw new NotCompleteException();
        }
        return new SaleBillVo(
            blService.getId(),
            currentDate.getValue(),
            BillState.Draft,
            tfClientId.getText(),
            tfSalesmanId.getText(),
            currentEmployee.getValue().getId(),
            Integer.parseInt(cbRepository.getValue()),
            commodityItemModelObservableList.stream().map(CommodityItemModel::toCommodityItem).toArray(CommodityItem[]::new),
            Double.parseDouble(tfOriginTotal.getText()),
            Double.parseDouble(tfMinusProfits.getText()),
            Double.parseDouble(tfToken.getText()),
            Double.parseDouble(tfUltiTotal.getText()),
            tfComment.getText(),
            Integer.parseInt(tfClientLevel.getText()),
            tfPromotionId.getText(),
            giftItemModelObservableList.stream().map(CommodityItemModel::toCommodityItem).toArray(CommodityItem[]::new),
            Double.parseDouble(tfGiftToken.getText())
        );
    }

    private void autofill() {
        try {
            tfBillId.setText(blService.getId());
            currentDate.setValue(new Date());
            currentEmployee.setValue(FrameworkUiManager.getCurrentEmployee());
        } catch (NoMoreBillException e) {
            PromptDialogHelper.start("ID不够！", "当日ID已经达到99999，无法增加新的单据。")
                .addCloseButton("好的", "CHECK", null)
                .createAndShow();
        } catch (Exception e) {
            PromptDialogHelper.start("初始化失败！", "请重试！")
                .addCloseButton("好的", "CHECK", null)
                .createAndShow();
        }

    }

    @FXML
    private void onBtnSaveAsDraftClicked() {
        saveAsDraft();
    }

    private void saveAsDraft() {
        try {
            blService.saveAsDraft(getCurrentSaleBillVo());
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

    @FXML
    private void onBtnResetClicked() {
        PromptDialogHelper.start("是否要重置", null)
            .addCloseButton("确定", "DONE", e -> reset())
            .addCloseButton("取消", "UNDO", null)
            .createAndShow();
        FrameworkUiManager.getWholePane().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                initHotKey();
                FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                reset();
            }
        });
    }

    private void reset() {
        tfComment.clear();
        tfOriginTotal.clear();
        tfUltiTotal.clear();
        tfSalesmanId.clear();
        tfSalesmanName.clear();
        tfClientId.clear();
        tfClientName.clear();
        cbRepository.setValue("1");
        tfToken.setText("0");
        tfMinusProfits.setText("0");
        commodityItemModelObservableList.clear();
        giftItemModelObservableList.clear();
    }

    class ListHandler implements ListChangeListener<CommodityItemModel> {

        /**
         * Called after a change has been made to an ObservableList.
         *
         * @param c an object representing the change that was done
         * @see ListChangeListener.Change
         */
        @Override
        public void onChanged(ListChangeListener.Change<? extends CommodityItemModel> c) {
            change();
        }

        public void change() {
            ObservableList<? extends CommodityItemModel> list = commodityItemModelObservableList;
            double total = 0;
            for (CommodityItemModel commodityItemModel : list) {
                total += commodityItemModel.getCommodityItemObjectProperty().getPrice() * commodityItemModel.getCommodityItemObjectProperty().getNumber();
            }
            tfOriginTotal.setText(total + "");
            double tempMinusProfit = tfMinusProfits.getText().length() == 0 ? 0.0 : Double.parseDouble(tfMinusProfits.getText());
            tfUltiTotal.setText(total - tempMinusProfit - Double.parseDouble(tfToken.getText()) + "");
        }
    }
}
