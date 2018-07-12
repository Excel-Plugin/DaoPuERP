package trapx00.lightx00.client.presentation.inventoryui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
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
import trapx00.lightx00.client.blservice.clientblservice.ClientBlService;
import trapx00.lightx00.client.blservice.clientblservice.ClientBlServiceFactory;
import trapx00.lightx00.client.blservice.inventoryblservice.PurchaseRefundBillBlService;
import trapx00.lightx00.client.blservice.inventoryblservice.PurchaseRefundBillBlServiceFactory;
import trapx00.lightx00.client.presentation.clientui.ClientInfoUi;
import trapx00.lightx00.client.presentation.clientui.factory.ClientInfoUiFactory;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelection;
import trapx00.lightx00.client.presentation.commodityui.factory.CommodityUiFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.presentation.inventoryui.factory.CommodityFillUiFactory;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.client.vo.salestaff.PurchaseRefundBillVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.exception.presentation.NotCompleteException;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.util.BillHelper;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Date;

public class PurchaseRefundBillUiController implements DraftContinueWritableUiController, ExternalLoadableUiController, ReversibleUi {

    @FXML
    private VBox dialogContainer;
    @FXML
    private JFXTextField tfBillId;
    @FXML
    private JFXTextField tfOperator;
    @FXML
    private JFXTextField tfDate;
    @FXML
    private JFXTextField tfClientId;
    @FXML
    private JFXTextField tfClientName;
    @FXML
    private JFXComboBox<String> cbRepository;
    @FXML
    private JFXTextField tfBillTotal;
    @FXML
    private JFXTextField tfComment;
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

    private ObjectProperty<Date> currentDate = new SimpleObjectProperty<>();
    private ObjectProperty<EmployeeVo> currentEmployee = new SimpleObjectProperty<>();
    private PurchaseRefundBillBlService blService = PurchaseRefundBillBlServiceFactory.getInstance();
    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
    private ClientBlService clientBlService = ClientBlServiceFactory.getInstance();
    private ObservableList<CommodityItemModel> commodityItemModelObservableList = FXCollections.observableArrayList();
    private ClientInfoUi clientInfoUi = ClientInfoUiFactory.getClientInfoUi();
    private CommoditySelection commoditySelection = CommodityUiFactory.getCommoditySelectionUi();
    private CommodityFillUiController commodityFillUiController = CommodityFillUiFactory.getCommodityFillUiController();
    private StringProperty tfClientIdProperty = new SimpleStringProperty("");
    private StringProperty tfClientNameProperty = new SimpleStringProperty("");

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
        PurchaseRefundBillVo purchaseRefundBillVo = (PurchaseRefundBillVo) draft;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        PurchaseRefundBillUiController purchaseRefundBillUiController = externalLoadedUiPackage.getController();
        purchaseRefundBillUiController.tfBillId.setText(purchaseRefundBillVo.getId().equals(BillHelper.refreshIdRequest) ? blService.getId() : purchaseRefundBillVo.getId());
        purchaseRefundBillUiController.tfDate.setText(purchaseRefundBillVo.getDate().toString());
        purchaseRefundBillUiController.tfOperator.setText(String.format("%s(id: %s)", currentEmployee.getValue().getName(), currentEmployee.getValue().getId()));
        purchaseRefundBillUiController.tfClientId.setText(purchaseRefundBillVo.getClientId());
        purchaseRefundBillUiController.tfClientName.setText(clientBlService.queryById(purchaseRefundBillVo.getClientId()).getName());
        purchaseRefundBillUiController.cbRepository.setValue(purchaseRefundBillVo.getRepository() + "");
        purchaseRefundBillUiController.tfBillTotal.setText(purchaseRefundBillVo.getTotal() + "");
        purchaseRefundBillUiController.addCommodityListItems(purchaseRefundBillVo.getCommodityList());
        return externalLoadedUiPackage;
    }

    public void addCommodityListItems(CommodityItem[] commodityItems) {
        for (CommodityItem commodityItem : commodityItems) {
            commodityItemModelObservableList.add(new CommodityItemModel(commodityItem));
        }
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/inventoryui/PurchaseRefundBillUi.fxml").loadAndGetPackageWithoutException();
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

        commodityItemModelObservableList.addListener(new PurchaseRefundBillUiController.ListHandler());
        tfClientId.textProperty().bindBidirectional(tfClientIdProperty);
        tfClientName.textProperty().bindBidirectional(tfClientNameProperty);

        ObservableList<String> stringObservableList = FXCollections.observableArrayList(
                "1", "2", "3"
        );
        cbRepository.setItems(stringObservableList);
        cbRepository.setValue("1");
        autofill();

        NumberValidator numberValidator = new NumberValidator();
        numberValidator.setMessage("请输入数字类型");
        RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
        requiredValidator.setMessage("请输入信息");

        tfClientId.getValidators().add(requiredValidator);
        tfClientName.getValidators().add(requiredValidator);

        tfClientIdProperty.addListener(event -> {
            if (tfClientIdProperty == null || tfClientIdProperty.get().length() == 0) {
                tfClientId.validate();
            }

        });
        tfClientNameProperty.addListener(event -> {
            if (tfClientNameProperty == null || tfClientNameProperty.get().length() == 0) {
                tfClientName.validate();
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
        PurchaseRefundBillVo purchaseRefundBillVo = (PurchaseRefundBillVo) reversible;
        purchaseRefundBillVo.setTotal(-purchaseRefundBillVo.getTotal());
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        PurchaseRefundBillUiController purchaseRefundBillUiController = externalLoadedUiPackage.getController();
        purchaseRefundBillUiController.tfBillId.setText(blService.getId());
        purchaseRefundBillUiController.tfDate.setText(purchaseRefundBillVo.getDate().toString());
        purchaseRefundBillUiController.tfOperator.setText(String.format("%s(id: %s)", currentEmployee.getValue().getName(), currentEmployee.getValue().getId()));
        purchaseRefundBillUiController.tfClientId.setText(purchaseRefundBillVo.getClientId());
        purchaseRefundBillUiController.tfClientName.setText(clientBlService.queryById(purchaseRefundBillVo.getClientId()).getName());
        purchaseRefundBillUiController.cbRepository.setValue(purchaseRefundBillVo.getRepository() + "");
        purchaseRefundBillUiController.tfBillTotal.setText(purchaseRefundBillVo.getTotal() + "");
        purchaseRefundBillUiController.addCommodityListItems(purchaseRefundBillVo.getCommodityList());
        return externalLoadedUiPackage;
    }

    @FXML
    private void onClientClicked() {
        clientInfoUi.showClientSelectDialog(x -> {
            tfClientId.setText(x.getId());
            tfClientName.setText(x.getName());
            tfClientId.validate();
            tfClientName.validate();
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
            commodityFillUiController.showCommodityFillDialog(y -> {
                double tempAmount = y.getNumber();
                for (CommodityItemModel commodityItemModel : commodityItemModelObservableList) {
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

    @FXML
    private void onBtnSubmitClicked() {
        try {
            PurchaseRefundBillVo purchaseRefundBillVo = getCurrentPurchaseRefundBillVo();
            PromptDialogHelper.start("确认单据", "").setContent(
                    purchaseRefundBillVo.billDetailUi().showContent(purchaseRefundBillVo).getComponent())
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
            blService.submit(getCurrentPurchaseRefundBillVo());
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

    private boolean validateAll(){
        return tfClientId.validate()&&tfClientName.validate();
    }

    private PurchaseRefundBillVo getCurrentPurchaseRefundBillVo() {
        if (!validateAll()) {
            PromptDialogHelper.start("提交失败！", "请先填写完单据。")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
        return new PurchaseRefundBillVo(
                blService.getId(),
                currentDate.getValue(),
                BillState.Draft,
                tfClientId.getText(),
                Integer.parseInt(cbRepository.getValue()),
                currentEmployee.getValue().getId(),
                tfComment.getText(),
                Double.parseDouble(tfBillTotal.getText()),
                commodityItemModelObservableList.stream().map(CommodityItemModel::toCommodityItem).toArray(CommodityItem[]::new)
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
            blService.saveAsDraft(getCurrentPurchaseRefundBillVo());
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
        tfBillTotal.clear();
        tfClientId.clear();
        tfClientName.clear();
        cbRepository.setValue("1");
        commodityItemModelObservableList.clear();
    }

    class ListHandler implements ListChangeListener<CommodityItemModel> {

        /**
         * Called after a change has been made to an ObservableList.
         *
         * @param c an object representing the change that was done
         * @see ListChangeListener.Change
         */
        @Override
        public void onChanged(Change<? extends CommodityItemModel> c) {
            ObservableList<? extends CommodityItemModel> list = c.getList();
            double total = 0;
            for (CommodityItemModel commodityItemModel : list) {
                total += commodityItemModel.getCommodityItemObjectProperty().getPrice() * commodityItemModel.getCommodityItemObjectProperty().getNumber();
            }
            tfBillTotal.setText(total + "");
        }
    }
}
