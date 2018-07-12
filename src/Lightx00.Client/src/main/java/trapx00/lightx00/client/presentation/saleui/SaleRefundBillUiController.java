package trapx00.lightx00.client.presentation.saleui;

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
import trapx00.lightx00.client.blservice.saleblservice.SaleRefundBillBlService;
import trapx00.lightx00.client.blservice.saleblservice.SaleRefundBillBlServiceFactory;
import trapx00.lightx00.client.presentation.adminui.EmployeeSelection;
import trapx00.lightx00.client.presentation.adminui.factory.UserManagementUiFactory;
import trapx00.lightx00.client.presentation.clientui.ClientInfoUi;
import trapx00.lightx00.client.presentation.clientui.factory.ClientInfoUiFactory;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelection;
import trapx00.lightx00.client.presentation.commodityui.factory.CommodityUiFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.presentation.helpui.validator.ValidatorHelper;
import trapx00.lightx00.client.presentation.inventoryui.CommodityItemModel;
import trapx00.lightx00.client.presentation.saleui.factory.SaleCommodityFillUiFactory;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.client.vo.salestaff.SaleRefundBillVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.exception.database.NoMoreBillException;
import trapx00.lightx00.shared.exception.presentation.NotCompleteException;
import trapx00.lightx00.shared.po.bill.BillState;
import trapx00.lightx00.shared.po.salestaff.CommodityItem;
import trapx00.lightx00.shared.util.BillHelper;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Date;

public class SaleRefundBillUiController implements DraftContinueWritableUiController, ExternalLoadableUiController, ReversibleUi {

    @FXML
    private VBox dialogContainer;
    @FXML
    private JFXTextField tfBillId;
    @FXML
    private JFXTextField tfSalesmanId;
    @FXML
    private JFXTextField tfSalesmanName;
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
    private JFXTextField tfOriginTotal;
    @FXML
    private JFXTextField tfMinusProfits;
    @FXML
    private JFXTextField tfToken;
    @FXML
    private JFXTextField tfUltiTotal;
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
    private SaleRefundBillBlService blService = SaleRefundBillBlServiceFactory.getInstance();
    private EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
    private ClientBlService clientBlService = ClientBlServiceFactory.getInstance();
    private ObservableList<CommodityItemModel> commodityItemModelObservableList = FXCollections.observableArrayList();
    private ClientInfoUi clientInfoUi = ClientInfoUiFactory.getClientInfoUi();
    private EmployeeSelection employeeSelection = UserManagementUiFactory.getEmployeeSelectionUi();
    private CommoditySelection commoditySelection = CommodityUiFactory.getCommoditySelectionUi();
    private SaleCommodityFillUiController saleCommodityFillUiController = SaleCommodityFillUiFactory.getSaleCommodityFillUiController();
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
        SaleRefundBillVo saleRefundBillVo = (SaleRefundBillVo) draft;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        SaleRefundBillUiController saleRefundBillUiController = externalLoadedUiPackage.getController();
        saleRefundBillUiController.tfBillId.setText(saleRefundBillVo.getId().equals(BillHelper.refreshIdRequest) ? blService.getId() : draft.getId());
        saleRefundBillUiController.tfDate.setText(saleRefundBillVo.getDate().toString());
        saleRefundBillUiController.tfSalesmanId.setText(saleRefundBillVo.getSalesmanId());
        saleRefundBillUiController.tfSalesmanName.setText(employeeInfo.queryById(saleRefundBillVo.getSalesmanId()).getName());
        saleRefundBillUiController.tfOperator.setText(String.format("%s(id: %s)", currentEmployee.getValue().getName(), currentEmployee.getValue().getId()));
        saleRefundBillUiController.tfClientId.setText(saleRefundBillVo.getClientId());
        saleRefundBillUiController.tfClientName.setText(clientBlService.queryById(saleRefundBillVo.getClientId()).getName());
        saleRefundBillUiController.cbRepository.setValue(saleRefundBillVo.getRepository() + "");
        saleRefundBillUiController.tfOriginTotal.setText(saleRefundBillVo.getOriginTotal() + "");
        saleRefundBillUiController.tfMinusProfits.setText(saleRefundBillVo.getMinusProfits() + "");
        saleRefundBillUiController.tfToken.setText(saleRefundBillVo.getToken() + "");
        saleRefundBillUiController.tfComment.setText(saleRefundBillVo.getComment());
        saleRefundBillUiController.addCommodityListItems(saleRefundBillVo.getCommodityList());
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
        return new UiLoader("/fxml/saleui/SaleRefundBillUi.fxml").loadAndGetPackageWithoutException();
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

        commodityItemModelObservableList.addListener(new SaleRefundBillUiController.ListHandler());
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
        autofill();

        ValidatorHelper.addDefaultRequiredValidator(tfClientId);
        ValidatorHelper.addDefaultRequiredValidator(tfClientName);
        ValidatorHelper.addDefaultRequiredValidator(tfSalesmanId);
        ValidatorHelper.addDefaultRequiredValidator(tfSalesmanName);
        ValidatorHelper.addDefaultDoubleValidator(tfToken);
        ValidatorHelper.addDefaultDoubleValidator(tfMinusProfits);
        ValidatorHelper.addDefaultDoubleValidator(tfToken);

        tfMinusProfits.setOnKeyReleased(event -> {
            if(tfMinusProfits.validate() || tfMinusProfits.getText().length()==0) {
                new SaleRefundBillUiController.ListHandler().change();
            }
        });
        tfToken.setOnKeyReleased(event -> {
            if(tfMinusProfits.validate() || tfMinusProfits.getText().length()==0) {
                new SaleRefundBillUiController.ListHandler().change();
            }
        });
        initHotKey();
    }

    private void initHotKey(){
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
        SaleRefundBillVo saleRefundBillVo = (SaleRefundBillVo) reversible;
        saleRefundBillVo.setUltiTotal(-saleRefundBillVo.getUltiTotal());
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        SaleRefundBillUiController saleRefundBillUiController = externalLoadedUiPackage.getController();
        saleRefundBillUiController.tfBillId.setText(blService.getId());
        saleRefundBillUiController.tfDate.setText(saleRefundBillVo.getDate().toString());
        saleRefundBillUiController.tfSalesmanId.setText(saleRefundBillVo.getSalesmanId());
        saleRefundBillUiController.tfSalesmanName.setText(employeeInfo.queryById(saleRefundBillVo.getSalesmanId()).getName());
        saleRefundBillUiController.tfOperator.setText(String.format("%s(id: %s)", currentEmployee.getValue().getName(), currentEmployee.getValue().getId()));
        saleRefundBillUiController.tfClientId.setText(saleRefundBillVo.getClientId());
        saleRefundBillUiController.tfClientName.setText(clientBlService.queryById(saleRefundBillVo.getClientId()).getName());
        saleRefundBillUiController.cbRepository.setValue(saleRefundBillVo.getRepository() + "");
        saleRefundBillUiController.tfOriginTotal.setText(saleRefundBillVo.getOriginTotal() + "");
        saleRefundBillUiController.tfMinusProfits.setText(saleRefundBillVo.getMinusProfits() + "");
        saleRefundBillUiController.tfToken.setText(saleRefundBillVo.getToken() + "");
        saleRefundBillUiController.tfComment.setText(saleRefundBillVo.getComment());
        saleRefundBillUiController.addCommodityListItems(saleRefundBillVo.getCommodityList());
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
    private void onEmployeeClicked() {
        employeeSelection.showEmployeeSelectDialog(x -> {
            tfSalesmanId.setText(x.get(0).getId());
            tfSalesmanName.setText(x.get(0).getName());
            tfSalesmanId.validate();
            tfSalesmanName.validate();
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
                commodityItemModelObservableList.add(new CommodityItemModel(new CommodityItem(x.getId(), x.getName(), x.getType(), y.getNumber(), y.getPrice(), y.getPrice() * y.getNumber(), y.getComment())));
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
            SaleRefundBillVo saleRefundBillVo = getCurrentSaleRefundBillVo();
            PromptDialogHelper.start("确认单据", "").setContent(
                    saleRefundBillVo.billDetailUi().showContent(saleRefundBillVo).getComponent())
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

    private void submit(){
        try {
            blService.submit(getCurrentSaleRefundBillVo());
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
        return tfClientId.validate()&&tfClientName.validate()&&tfSalesmanId.validate()&&tfSalesmanName.validate()&&tfToken.validate()&&tfMinusProfits.validate()&&tfToken.validate();
    }

    private SaleRefundBillVo getCurrentSaleRefundBillVo() {
        if (!validateAll()) {
            PromptDialogHelper.start("提交失败！", "请先填写完单据。")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
        return new SaleRefundBillVo(
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
                tfComment.getText()
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
            blService.saveAsDraft(getCurrentSaleRefundBillVo());
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
            double tempMinusProfit=tfMinusProfits.getText().length()==0?0.0:Double.parseDouble(tfMinusProfits.getText());
            tfUltiTotal.setText(total - tempMinusProfit - Double.parseDouble(tfToken.getText()) + "");
        }
    }
}
