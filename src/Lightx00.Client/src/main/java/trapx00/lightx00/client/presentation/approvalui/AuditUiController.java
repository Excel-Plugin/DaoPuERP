package trapx00.lightx00.client.presentation.approvalui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.blservice.approvalblservice.AuditBlService;
import trapx00.lightx00.client.blservice.approvalblservice.AuditBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadableUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.PromptDialogHelper;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.manager.AuditIdVo;
import trapx00.lightx00.shared.po.ResultMessage;
import trapx00.lightx00.shared.queryvo.AuditIdQueryVo;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Date;

public class AuditUiController implements ExternalLoadableUiController {
    public JFXButton btnRefresh;
    public JFXButton btnApproval;
    public JFXTreeTableView<BillAuditItemModel> tbBill;
    public JFXTreeTableColumn<BillAuditItemModel, String> tcId;
    public JFXTreeTableColumn<BillAuditItemModel, String> tcType;
    public JFXTreeTableColumn<BillAuditItemModel, String> tcOperator;
    public JFXTreeTableColumn<BillAuditItemModel, String> tcCommitTime;

    private ObservableList<BillAuditItemModel> billTableItemModels = FXCollections.observableArrayList();
    private AuditBlService blService = AuditBlServiceFactory.getInstance();

    public void initialize() {
        initTable();
        updateItems();
    }

    public void initTable() {
        tcCommitTime.setCellValueFactory(cellData -> new SimpleStringProperty(DateHelper.fromDate(cellData.getValue().getValue().getDate())));
        tcType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getType().toString()));
        tcId.setCellValueFactory(cellData -> cellData.getValue().getValue().idProperty());
        tcOperator.setCellValueFactory(cellData -> cellData.getValue().getValue().operatorProperty());
        TreeItem<BillAuditItemModel> root = new RecursiveTreeItem<>(billTableItemModels, RecursiveTreeObject::getChildren);
        tbBill.setRoot(root);
        tbBill.setShowRoot(false);
        tbBill.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tbBill.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<BillAuditItemModel> selectedItem = tbBill.getSelectionModel().getSelectedItem();
                showDetail(selectedItem.getValue());
            }
        });
    }

    private void showDetail(BillAuditItemModel model) {
        if (model != null) {
            BillVo selected = model.getBill();
            PromptDialogHelper.start("单据详细信息","")
                    .setContent(selected.billDetailUi().showContent(selected).getComponent())
                    .addButton("拒绝","CLOSE",e->{
                        ResultMessage result = blService.reject(new AuditIdVo(selected.getId(),new Date()));
                        if(result.equals(ResultMessage.Success)) {
                            PromptDialogHelper.start("拒绝通过成功", "已发送通知。")
                                    .addCloseButton("完成", "DONE", e2 -> updateItems())
                                    .createAndShow();
                        }
                        else{
                            PromptDialogHelper.start("拒绝通过失败", "")
                                    .addCloseButton("好的", "DONE", null)
                                    .createAndShow();
                        }

                    })
                    .addButton("通过","CHECK",e->{
                        ResultMessage result = blService.pass(new AuditIdVo(selected.getId(),new Date()));
                        if(result.equals(ResultMessage.Success)) {
                            PromptDialogHelper.start("审批通过成功", "已发送通知。")
                                    .addCloseButton("完成", "DONE", e2->updateItems())
                                    .createAndShow();
                        }
                        else{
                            PromptDialogHelper.start("审批通过失败", "")
                                    .addCloseButton("好的", "DONE", null)
                                    .createAndShow();
                        }

                    })
                    .addCloseButton("返回","REFRESH",null)
                    .createAndShow();
        } else {
            PromptDialogHelper.start("错误","请至少选一张单据。")
                    .addCloseButton("好的","DONE",null)
                    .createAndShow();
        }
    }

    private void updateItems() {
        billTableItemModels.clear();
        AuditIdQueryVo queryVo = new AuditIdQueryVo();
        AuditIdVo[] result = blService.query(queryVo);
        for(AuditIdVo auditIdVo:result) {
            billTableItemModels.add(new BillAuditItemModel(auditIdVo));
        }

    }

    public void onApproveClicked() {
        ObservableList<Integer> list = tbBill.getSelectionModel().getSelectedIndices();
        for(Integer e:list) {
            BillVo bill = billTableItemModels.get(e).getBill();
            ResultMessage result = blService.pass(new AuditIdVo(bill.getId(),new Date()));
            if(!result.isSuccess()) {
                PromptDialogHelper.start("错误", "单据" + bill.getId() + "审批失败！")
                        .addCloseButton("好的", "DONE", null)
                        .createAndShow();
            }
        }
        PromptDialogHelper.start("审批通过成功", "已发送通知。")
                .addCloseButton("完成", "DONE", e -> updateItems())
                .createAndShow();

    }

    public void onBtnRefreshClicked() {
        updateItems();
    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/approvalui/AuditUi.fxml").loadAndGetPackageWithoutException();
    }
}
