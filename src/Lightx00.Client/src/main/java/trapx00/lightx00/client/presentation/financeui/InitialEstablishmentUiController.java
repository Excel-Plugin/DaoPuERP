package trapx00.lightx00.client.presentation.financeui;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import trapx00.lightx00.client.bl.financebl.factory.InitialEstablishmentBlFactory;
import trapx00.lightx00.client.blservice.financeblservice.InitialEstablishmentBlService;
import trapx00.lightx00.client.presentation.bankaccountui.BankAccountModel;
import trapx00.lightx00.client.presentation.clientui.ClientSelectionItemModel;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelectionItemModel;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.Draftable;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.financestaff.SystemSnapshotVo;
import trapx00.lightx00.shared.exception.bl.UncheckedRemoteException;
import trapx00.lightx00.shared.exception.database.IdExistsException;
import trapx00.lightx00.shared.util.DateHelper;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class InitialEstablishmentUiController implements DraftContinueWritableUiController, ExternalLoadableUiController {
    public JFXTextField tfId;
    public JFXTextField tfDate;
    public JFXTextField tfOperator;
    @FXML private JFXTreeTableView<BankAccountModel> tableBankAccounts;
    @FXML private JFXTreeTableColumn<BankAccountModel, Integer> tcBankId;
    @FXML private JFXTreeTableColumn<BankAccountModel, String> tcBankName;
    @FXML private JFXTreeTableColumn<BankAccountModel, Double> tcBankBalance;

    @FXML
    private TreeTableView<ClientSelectionItemModel> tableClient;
    @FXML
    private TreeTableColumn<ClientSelectionItemModel, String> tcClientId;
    @FXML
    private TreeTableColumn<ClientSelectionItemModel, String> tcClientName;
    @FXML
    private TreeTableColumn<ClientSelectionItemModel, String> tcClientType;

    public JFXTreeTableView<CommoditySelectionItemModel> tableCommodity;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tcCommodityName;
    public JFXTreeTableColumn<CommoditySelectionItemModel, String> tcCommodityId;

    private ObservableList<BankAccountModel> bankAccountModels = FXCollections.observableArrayList();
    private ObservableList<ClientSelectionItemModel> clientModels = FXCollections.observableArrayList();
    private ObservableList<CommoditySelectionItemModel> commodityModels = FXCollections.observableArrayList();


    private ObjectProperty<SystemSnapshotVo> systemSnapshot = new SimpleObjectProperty<>();
    private ObjectProperty<Date> date = new SimpleObjectProperty<>();
    private ObjectProperty<EmployeeVo> operator = new SimpleObjectProperty<>();

    private InitialEstablishmentBlService blService = InitialEstablishmentBlFactory.getController();

    /**
     * Start continuing write a draft. Returns a ExternalLoadableUiController. It can be used to set the stage without casting to specific ui controller.
     * Overrides to return a specific ui controller.
     *
     * @param draft draft
     * @return a ExternalLoadableUiController
     */
    @Override
    public ExternalLoadedUiPackage continueWriting(Draftable draft) {
        return null;
    }

    public void initialize() {
        TableInitialization.initBankAccountTable(tableBankAccounts, tcBankId, tcBankName, tcBankBalance, bankAccountModels);
        TableInitialization.initClientTable(tableClient, tcClientId, tcClientName, tcClientType, clientModels);
        TableInitialization.initCommodityTable(tableCommodity, tcCommodityName, tcCommodityId, commodityModels);

        systemSnapshot.addListener((observable, oldValue, newValue) -> {
            bankAccountModels.clear();
            bankAccountModels.addAll(Arrays.stream(newValue.getBankAccounts()).map(BankAccountModel::new).collect(Collectors.toList()));

            clientModels.clear();
            clientModels.addAll(Arrays.stream(newValue.getClients()).map(ClientSelectionItemModel::new).collect(Collectors.toList()));

            commodityModels.clear();
            commodityModels.addAll(Arrays.stream(newValue.getCommodities()).map(CommoditySelectionItemModel::new).collect(Collectors.toList()));

            tfId.setText(newValue.getId());
        });

        date.addListener((observable, oldValue, newValue) -> {
            tfDate.setText(DateHelper.fromDate(newValue));
        });

        operator.addListener((observable, oldValue, newValue) -> {
            tfOperator.setText(String.format("%s(id: %s)", newValue.getName(), newValue.getId()));
        });

    }

    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/financeui/InitialEstablishmentUi.fxml").loadAndGetPackageWithoutException();
    }

    public void onBtnSubmitClicked(ActionEvent actionEvent) {
        if (systemSnapshot.get() == null) {
            PromptDialogHelper.start("请先点击自动填写填充信息！","请先点击自动填写填充信息！")
                .addCloseButton("好","CHECK",null)
                .createAndShow();
            return;
        }
        SystemSnapshotVo vo = systemSnapshot.get();
        vo.setDate(date.get());
        vo.setOperatorId(operator.get().getId());
        try {
            blService.submit(vo);
            PromptDialogHelper.start("提交成功！","提交成功")
                .addCloseButton("返回主界面","CHECK", e -> FrameworkUiManager.switchBackToHome())
                .createAndShow();
        } catch (UncheckedRemoteException e) {
            PromptDialogHelper.start("提交失败！", "网络错误。详细信息：\n" + e.getRemoteException().getMessage())
                .addCloseButton("好的", "CHECK", null)
                .createAndShow();

        } catch (IdExistsException e) {
            PromptDialogHelper.start("提交失败！", "ID已存在。请重新填写。")
                .addCloseButton("好的", "CHECK", null)
                .createAndShow();
        } catch (Exception e) {
            PromptDialogHelper.start("提交失败！", "详细信息：\n" + e.getMessage())
                .addCloseButton("好的", "CHECK", null)
                .createAndShow();
        }

    }


    public void onBtnCancelClicked(ActionEvent actionEvent) {
        FrameworkUiManager.switchBackToHome();
    }

    public void onBtnAutofillClicked(ActionEvent actionEvent) {
        systemSnapshot.set(blService.autofill());
        date.set(new Date());
        operator.set(FrameworkUiManager.getCurrentEmployee());
    }
}
