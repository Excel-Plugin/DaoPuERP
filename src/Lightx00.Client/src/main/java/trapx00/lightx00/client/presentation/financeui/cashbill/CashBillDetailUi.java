package trapx00.lightx00.client.presentation.financeui.cashbill;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import trapx00.lightx00.client.presentation.helpui.BillDetailUi;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.presentation.helpui.UiLoader;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.financestaff.CashBillVo;
import trapx00.lightx00.shared.po.financestaff.CashBillItem;
import trapx00.lightx00.shared.util.DateHelper;

/**
 * 现金费用单详细信息UI。注意，单据UI详细信息实现了BIllDetailUi后，不需要再实现DraftDetailUi。
 */
public class CashBillDetailUi extends BillDetailUi {
    public JFXTextField tfBillState;
    public JFXTextField tfBankaccountId;
    public JFXTextField tfOperator;
    public JFXTextField tfDate;
    public JFXTextField tfId;
    public JFXTreeTableView<CashBillItemModel> tbCashBillItems;
    public JFXTreeTableColumn<CashBillItemModel, String> tcName;
    public JFXTreeTableColumn<CashBillItemModel, String> tcPrice;
    public JFXTreeTableColumn<CashBillItemModel, String> tcComment;

    private ObservableList<CashBillItemModel> cashBillItemModelObservableList = FXCollections.observableArrayList();

    @Override
    public ExternalLoadedUiPackage showContent(BillVo arg) {
        CashBillVo cashBillVo = (CashBillVo) arg;
        ExternalLoadedUiPackage externalLoadedUiPackage = load();
        CashBillDetailUi cashBillDetailUi = externalLoadedUiPackage.getController();
        cashBillDetailUi.tfId.setText(cashBillVo.getId());
        cashBillDetailUi.tfDate.setText(DateHelper.fromDate(cashBillVo.getDate()));
        cashBillDetailUi.tfBankaccountId.setText(String.valueOf(cashBillVo.getAccountId()));
        cashBillDetailUi.tfBillState.setText(cashBillVo.getState().toString());
        cashBillDetailUi.tfOperator.setText(cashBillVo.getOperatorId());
        cashBillDetailUi.addCashBillItems(cashBillVo.getItems());
        return externalLoadedUiPackage;
    }

    public void addCashBillItems(CashBillItem[] cashBillItems) {
        for (CashBillItem cashBillItem : cashBillItems) {
            cashBillItemModelObservableList.add(new CashBillItemModel(cashBillItem));
        }

    }

    public void initialize() {
        tcName.setCellValueFactory(cellData -> cellData.getValue().getValue().nameProperty());
        tcPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue().getAmount())));
        tcComment.setCellValueFactory(cellData -> cellData.getValue().getValue().commentProperty());
        TreeItem<CashBillItemModel> root = new RecursiveTreeItem<>(cashBillItemModelObservableList, RecursiveTreeObject::getChildren);
        tbCashBillItems.setRoot(root);
        tbCashBillItems.setShowRoot(false);

    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/CashBillDetailUi.fxml").loadAndGetPackageWithoutException();
    }
}
