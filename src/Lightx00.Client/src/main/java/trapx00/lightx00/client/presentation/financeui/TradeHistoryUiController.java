package trapx00.lightx00.client.presentation.financeui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import trapx00.lightx00.client.bl.financebl.factory.TradeHistoryBlFactory;
import trapx00.lightx00.client.bl.util.ExcelOutput;
import trapx00.lightx00.client.blservice.financeblservice.TradeHistoryBlService;
import trapx00.lightx00.client.presentation.adminui.EmployeeSelection;
import trapx00.lightx00.client.presentation.adminui.factory.UserManagementUiFactory;
import trapx00.lightx00.client.presentation.clientui.ClientInfoUi;
import trapx00.lightx00.client.presentation.clientui.factory.ClientInfoUiFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.Reversible;
import trapx00.lightx00.client.vo.financestaff.TradeHistoryQueryVo;
import trapx00.lightx00.client.vo.financestaff.TradeHistoryVo;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.po.bill.BillType;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.util.DateHelper;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TradeHistoryUiController implements ExternalLoadableUiController {
    public JFXDatePicker dpStart;
    public JFXDatePicker dpEnd;
    public JFXTextField tfClient;
    public JFXTextField tfBillType;
    public JFXTextField tfRepository;
    public JFXButton btnUpdate;
    public JFXButton btnRevert;
    public JFXTreeTableView<BillTableItemModel> tbBill;
    public JFXTreeTableColumn<BillTableItemModel, String> tcBillType;
    public JFXTreeTableColumn<BillTableItemModel, String> tcId;
    public JFXTreeTableColumn<BillTableItemModel, String> tcDate;
    public JFXTreeTableColumn<BillTableItemModel, String> tcOperator;
    public JFXTreeTableColumn<BillTableItemModel, String> tcState;
    public JFXButton btnExport;
    public JFXButton btnDetail;
    public JFXCheckBox cbFilter;
    public JFXTextField tfOperator;
    private EmployeeSelection employeeSelection = UserManagementUiFactory.getEmployeeSelectionUi();
    private ObservableList<BillTableItemModel> billTableItemModels = FXCollections.observableArrayList();
    private ClientInfoUi clientInfoUi = ClientInfoUiFactory.getClientInfoUi();

    private ObjectProperty<ClientVo> client = new SimpleObjectProperty<>();
    private ObjectProperty<List<BillType>> billTypes = new SimpleObjectProperty<>();

    private TradeHistoryBlService blService = TradeHistoryBlFactory.getController();
    private ObjectProperty<TradeHistoryVo> tradeHistory = new SimpleObjectProperty<>();
    private ObjectProperty<List<EmployeeVo>> operator = new SimpleObjectProperty<>();

    public void initialize() {
        initTable();
        if (FrameworkUiManager.getCurrentEmployee().getPosition().equals(EmployeePosition.Manager)) {
            btnRevert.setDisable(true);
        }
    }

    public void initTable() {
        tcDate.setCellValueFactory(cellData -> new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getDate())));
        tcBillType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getType().toString()));
        tcId.setCellValueFactory(cellData -> cellData.getValue().getValue().idProperty());
        tcOperator.setCellValueFactory(cellData -> {
            EmployeeVo operator = cellData.getValue().getValue().getOperator();
            if (operator != null) {
                return new SimpleStringProperty(String.format("%s(id: %s)", operator.getName(), operator.getId()));
            } else {
                return new SimpleStringProperty("");
            }

        });
        tcState.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getState().toString()));
        TreeItem<BillTableItemModel> root = new RecursiveTreeItem<>(billTableItemModels, RecursiveTreeObject::getChildren);
        tbBill.setRoot(root);
        tbBill.setShowRoot(false);
        tbBill.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                showDetail();
            }
        });

        client.addListener((observable, oldValue, newValue) -> {
            tfClient.setText(newValue == null ? "" : String.format("%s(id: %s)", newValue.getName(), newValue.getId()));
        });
        billTypes.addListener((observable, oldValue, newValue) -> {
            tfBillType.setText(newValue == null ? "" : (
                newValue.size() == 1 ? newValue.get(0).toString()
                    : String.format("选择了%s项种类", newValue.size())
                ));
        });

        operator.addListener((observable, oldValue, newValue) -> {
                tfOperator.setText(newValue == null ? "" : (
                    newValue.size() == 1 ? String.format("%s(id: %s)", newValue.get(0).getName(), newValue.get(0).getId())
                        : String.format("选择了%s项操作员", newValue.size())
                ));
            });

        tradeHistory.addListener(((observable, oldValue, newValue) -> {
            billTableItemModels.clear();
            billTableItemModels.addAll(Arrays.stream(newValue.getBills()).map(BillTableItemModel::new).collect(Collectors.toList()));
        }));
    }

    public void showDetail() {
        BillVo selected = getSelected();
        if (selected != null) {
            PromptDialogHelper.start("单据详细信息","")
                    .setContent(selected.billDetailUi().showContent(selected).getComponent())
                    .addCloseButton("好","CHECK",null)
                    .createAndShow();
        } else {
            PromptDialogHelper.start("错误","请至少选一个条目。")
                .addCloseButton("好的","DONE",null)
                .createAndShow();
        }
    }

    public void updateItems() {

        TradeHistoryQueryVo queryVo = new TradeHistoryQueryVo();
        if (cbFilter.isSelected()) {
            LocalDate startDate = dpStart.getValue();
            LocalDate endDate = dpEnd.getValue();
            if (startDate != null && endDate != null) {
                queryVo.setStart(DateHelper.fromLocalDate(startDate));
                queryVo.setEnd(DateHelper.fromLocalDate(endDate));
            }

            if (client.get() != null) {
                queryVo.setClientIds(new String[] {client.get().getId()});
            }

            if (billTypes.get() != null && billTypes.get().size() != 0) {
                queryVo.setBillTypes(billTypes.get().toArray(new BillType[billTypes.get().size()]));
            }

            if (operator.get() != null) {
                queryVo.setOperatorIds(operator.get().stream().map(EmployeeVo::getId).toArray(String[]::new));
            }
        }
        tradeHistory.set(blService.query(queryVo));

    }

    public BillVo getSelected() {
        TreeItem<BillTableItemModel> model = tbBill.getSelectionModel().getSelectedItem();
        if (model != null) {
            return model.getValue().getBill();
        } else {
            return null;
        }

    }

    public void onRefreshButtonClicked(ActionEvent actionEvent) {
        updateItems();
    }

    public void onRevertClicked(ActionEvent actionEvent) {
        BillVo selected = getSelected();

        if (selected != null) {
            if (selected instanceof Reversible) {
                Reversible reversible = (Reversible) selected;
                FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
                FrameworkUiManager.switchFunction(reversible.reversibleUi().revertReversible(reversible), "红冲", true);
            } else {
                PromptDialogHelper.start("您所选的单据不可红冲！","您所选的单据不可红冲！")
                    .addCloseButton("好","CHECK",null)
                    .createAndShow();
            }

        } else {
            PromptDialogHelper.start("错误","请至少选一个条目。")
                .addCloseButton("好的","DONE",null)
                .createAndShow();
        }
    }

    public void onExportClicked(ActionEvent actionEvent) {
        if (tradeHistory.get() == null) {
            PromptDialogHelper.start("请先查询表！","请先查询表！")
                .addCloseButton("好","CHECK",null)
                .createAndShow();
            return;
        }
        TradeHistoryVo tradeHistoryVo = tradeHistory.get();
        blService.export(tradeHistoryVo);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择路径");
        fileChooser.setInitialFileName(String.format("经营历程表-%s.xls", DateHelper.currentDateString("yyyy_MM_dd-HH_mm_ss")));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            ExcelOutput.createExcel(file.getParent(), toExcel(), file.getName(), "//");

            blService.export(tradeHistoryVo);

            PromptDialogHelper.start("导出成功！",String.format("经营历程表已经导出到%s。", file.getAbsolutePath()))
                .addCloseButton("好","CHECK",null)
                .createAndShow();
        }
    }

    public String[] toExcel() {
        List<String> content = new ArrayList<>();
        content.add("日期//ID//操作员//单据类型//单据状态");
        for(BillTableItemModel record : billTableItemModels) {
            content.add(String.format("%s//%s//%s//%s//%s\n",
                DateHelper.fromDate(record.getDate()),
                record.getId(),
                String.format("%s(id: %s)", record.getOperator().getName(), record.getOperator().getId()),
                record.getBill().getBillType(),
                record.getState()
            ));
        }
        return content.toArray(new String[content.size()]);
    }

    public void onDetailClicked(ActionEvent actionEvent) {
        showDetail();
    }

    public void onClientClicked(MouseEvent mouseEvent) {
        clientInfoUi.showClientSelectDialog(x -> client.set(x));
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/TradeHistoryUi.fxml").loadAndGetPackageWithoutException();
    }

    public void onTypeClicked(MouseEvent mouseEvent) {
        new BillTypeSelectionDialog().show(billTypes.getValue(), list -> billTypes.set(list));
    }

    public void onBtnClearFilterClicked(ActionEvent actionEvent) {
        dpStart.setValue(null);
        dpEnd.setValue(null);
        client.setValue(null);
        billTypes.setValue(null);
        operator.set(null);
    }

    public void onOperatorClicked(MouseEvent mouseEvent) {
        employeeSelection.showEmployeeSelectDialog(employeeVos -> {
            operator.set(employeeVos);
        });
    }
}
