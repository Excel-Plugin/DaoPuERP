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
import trapx00.lightx00.client.bl.util.ExcelOutput;
import trapx00.lightx00.client.blservice.financeblservice.SaleDetailBlService;
import trapx00.lightx00.client.blservice.financeblservice.SaleDetailBlServiceFactory;
import trapx00.lightx00.client.presentation.adminui.EmployeeSelection;
import trapx00.lightx00.client.presentation.adminui.factory.UserManagementUiFactory;
import trapx00.lightx00.client.presentation.clientui.ClientInfoUi;
import trapx00.lightx00.client.presentation.clientui.factory.ClientInfoUiFactory;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelection;
import trapx00.lightx00.client.presentation.commodityui.factory.CommodityUiFactory;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadableUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.PromptDialogHelper;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.financestaff.SaleDetailQueryVo;
import trapx00.lightx00.client.vo.financestaff.SaleDetailVo;
import trapx00.lightx00.client.vo.inventorystaff.CommodityVo;
import trapx00.lightx00.client.vo.salestaff.ClientVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SaleDetailUiController implements ExternalLoadableUiController {
    public JFXDatePicker dpStart;
    public JFXDatePicker dpEnd;
    public JFXTextField tfCommodity;
    public JFXTextField tfClient;
    public JFXTextField tfOperator;
    public JFXTextField tfRepository;
    public JFXButton btnClearFilter;
    public JFXTreeTableView<SaleRecordModel> tbSaleDetails;
    public JFXTreeTableColumn<SaleRecordModel, String> tcDate;
    public JFXTreeTableColumn<SaleRecordModel, String> tcName;
    public JFXTreeTableColumn<SaleRecordModel, String> tcModel;
    public JFXTreeTableColumn<SaleRecordModel, String> tcAmount;
    public JFXTreeTableColumn<SaleRecordModel, String> tcUnitPrice;
    public JFXTreeTableColumn<SaleRecordModel, String> tcTotal;

    public ObjectProperty<ClientVo> client = new SimpleObjectProperty<>();
    public ObjectProperty<CommodityVo> commodities = new SimpleObjectProperty<>();
    public ObjectProperty<List<EmployeeVo>> operators = new SimpleObjectProperty<>();

    private SaleDetailBlService blService = SaleDetailBlServiceFactory.getInstance();
    private CommoditySelection commoditySelection = CommodityUiFactory.getCommoditySelectionUi();
    private ClientInfoUi clientInfoUi = ClientInfoUiFactory.getClientInfoUi();
    private EmployeeSelection employeeSelection = UserManagementUiFactory.getEmployeeSelectionUi();

    private ObservableList<SaleRecordModel> saleRecordModels = FXCollections.observableArrayList();
    private SaleDetailVo saleDetailVo;


    public void initialize() {
        initTable();
        initProperties();
    }

    public void initProperties() {
        client.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfClient.setText(String.format("%s(id: %s)", newValue.getId(), newValue.getName()));
            } else {
                tfClient.setText("未选择！");
            }
        });

        commodities.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfCommodity.setText(String.format("%s(id: %s)", newValue.getName(), newValue.getId()));
            }
        });

        operators.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.size() == 1) {
                    EmployeeVo selected = newValue.get(0);
                    tfOperator.setText(String.format("%s(id: %s)", selected.getName(), selected.getId()));
                } else {
                    tfOperator.setText(String.format("已选择%d位操作员", newValue.size()));
                }
            }
        });
    }

    public void initTable() {
        tcDate.setCellValueFactory(x -> new SimpleStringProperty(DateHelper.fromDate(x.getValue().getValue().getDate())));
        tcName.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getValue().getCommodityVo().getName()));
        tcModel.setCellValueFactory(x -> new SimpleStringProperty(x.getValue().getValue().getCommodityVo().getType()));
        tcAmount.setCellValueFactory(x -> new SimpleStringProperty(String.valueOf(x.getValue().getValue().getAmount())));
        tcUnitPrice.setCellValueFactory(x -> new SimpleStringProperty(String.valueOf(x.getValue().getValue().getUnitPrice())));
        tcTotal.setCellValueFactory(x -> new SimpleStringProperty(String.valueOf(x.getValue().getValue().getTotal())));
        TreeItem<SaleRecordModel> root = new RecursiveTreeItem<>(saleRecordModels, RecursiveTreeObject::getChildren);
        tbSaleDetails.setRoot(root);
        tbSaleDetails.setShowRoot(false);
        tbSaleDetails.setEditable(true);
    }

    public SaleDetailQueryVo constructQueryVo() {
        SaleDetailQueryVo queryVo = new SaleDetailQueryVo();
        LocalDate start = dpStart.getValue();
        LocalDate end = dpEnd.getValue();
        if (start != null && end != null) {
            queryVo.setStart(DateHelper.fromLocalDate(start));
            queryVo.setEnd(DateHelper.fromLocalDate(end));
        }
        if (client.get() != null) {
            queryVo.setClient(new ClientVo[] { client.get() });
        }
        List<EmployeeVo> operatorss = operators.get();
        queryVo.setOperator(operatorss == null ? null : operatorss.toArray(new EmployeeVo[operatorss.size()]));
        CommodityVo commoditiess = commodities.get();
        queryVo.setCommodityName(commoditiess == null ? null : commoditiess.getName());
        return queryVo;
    }

    public void update() {
        SaleDetailQueryVo queryVo = constructQueryVo();
        saleDetailVo = blService.query(queryVo);
        saleRecordModels.clear();
        saleRecordModels.addAll(Arrays.stream(saleDetailVo.getRecords()).map(SaleRecordModel::new).collect(Collectors.toList()));
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/SaleDetailUi.fxml").loadAndGetPackageWithoutException();
    }

    public void onRefreshButtonClicked(ActionEvent actionEvent) {
        update();
    }

    public void onExportClicked(ActionEvent actionEvent) {
        if (saleDetailVo != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择路径");
            fileChooser.setInitialFileName(String.format("销售明细表-%s.xls", DateHelper.currentDateString("yyyy_MM_dd-HH_mm_ss")));
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                ExcelOutput.createExcel(file.getParent(), saleDetailVo.toExcel(), file.getName());

                blService.export(saleDetailVo);

                PromptDialogHelper.start("导出成功！",String.format("销售明细表已经导出到%s。", file.getAbsolutePath()))
                    .addCloseButton("好","CHECK",null)
                    .createAndShow();
            }

        } else {
            PromptDialogHelper.start("导出失败！","请先查询报表！")
                .addCloseButton("好","CHECK",null)
                .createAndShow();
        }
    }

    public void onTfCommodityClicked(MouseEvent mouseEvent) {
        commoditySelection.showCommoditySelectDialog(x -> commodities.set(x));
    }

    public void onTfClientClicked(MouseEvent mouseEvent) {
        clientInfoUi.showClientSelectDialog(x -> client.set(x));
    }

    public void onBtnClearFilterClicked(ActionEvent actionEvent) {
        dpStart.setValue(null);
        dpEnd.setValue(null);
        client.set(null);
        operators.set(null);
        commodities.set(null);
    }

    public void onTfOperatorClicked(MouseEvent mouseEvent) {
        employeeSelection.showEmployeeSelectDialog(x -> operators.set(x));
    }
}
