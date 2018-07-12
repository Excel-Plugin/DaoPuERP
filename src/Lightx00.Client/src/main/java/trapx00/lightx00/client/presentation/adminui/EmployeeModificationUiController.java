package trapx00.lightx00.client.presentation.adminui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import trapx00.lightx00.client.blservice.adminblservice.UserManagementBlService;
import trapx00.lightx00.client.blservice.adminblservice.UserManagementBlServiceFactory;
import trapx00.lightx00.client.presentation.helpui.*;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.client.vo.admin.AdminVo;
import trapx00.lightx00.client.vo.financestaff.FinanceStaffVo;
import trapx00.lightx00.client.vo.inventorystaff.InventoryStaffVo;
import trapx00.lightx00.client.vo.manager.ManagerVo;
import trapx00.lightx00.client.vo.salestaff.SaleStaffVo;
import trapx00.lightx00.shared.exception.presentation.NotCompleteException;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;
import trapx00.lightx00.shared.po.salestaff.SaleStaffType;
import trapx00.lightx00.shared.util.DateHelper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EmployeeModificationUiController implements ExternalLoadableUiController {
    @FXML private JFXTextField tfId;
    @FXML private JFXDatePicker tfWorkDate;
    @FXML private JFXTextField tfName;
    @FXML private JFXTextField tfPassword;
    @FXML private JFXComboBox<EmployeePosition> tfPosition = new JFXComboBox<>();
    @FXML private JFXComboBox<EmployeeState> tfState = new JFXComboBox<>();
    @FXML private JFXComboBox<String> tfRoot = new JFXComboBox<>();
    @FXML private JFXComboBox<SaleStaffType> tfSaleType = new JFXComboBox<>();
    @FXML public JFXButton btnSubmit;
    @FXML public JFXButton btnCancel;

    private UserManagementBlService blService = UserManagementBlServiceFactory.getInstance();
    private Runnable runnable;
    private ObservableList<String> root = FXCollections.observableArrayList("否","是");
    private EmployeeVo oldEmployee;
    private boolean transPerson = false;
    /**
     * Loads the controller.
     *
     * @return external loaded ui controller and component
     */
    @Override
    public ExternalLoadedUiPackage load() {
        return new UiLoader("/fxml/adminui/EmployeeModificationUi.fxml").loadAndGetPackageWithoutException();
    }

    public void show(EmployeeVo employee, Runnable runnable) {
        ExternalLoadedUiPackage uiPackage = load();
        EmployeeModificationUiController ui = uiPackage.getController();
        //设置初始化
        ui.oldEmployee = employee;
        ui.tfId.setText(employee.getId());
        ui.tfName.setText(employee.getName());
        ui.tfWorkDate.setValue(dateToLocalDate(employee.getWorkSince()));

        ui.tfPosition.getItems().addAll(FXCollections.observableArrayList(EmployeePosition.values()));
        ui.tfState.getItems().addAll(FXCollections.observableArrayList(EmployeeState.values()));
        ui.tfRoot.getItems().addAll(root);
        ui.tfSaleType.getItems().addAll(FXCollections.observableArrayList(SaleStaffType.values()));

        ui.tfPassword.setText(employee.getPassword());
        ui.tfPosition.getSelectionModel().select(employee.getPosition());
        ui.tfState.getSelectionModel().select(employee.getState());

        ui.tfRoot.setVisible(false);
        ui.tfSaleType.setVisible(false);
        ui.tfPosition.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue.equals(EmployeePosition.SaleStaff)) {
                ui.tfRoot.setVisible(true);
                ui.tfSaleType.setVisible(true);
            }
            else if(newValue.equals(EmployeePosition.FinanceStaff)) {
                ui.tfRoot.setVisible(true);
                ui.tfSaleType.setVisible(false);
            }
            else {
                ui.tfRoot.setVisible(false);
                ui.tfSaleType.setVisible(false);
            }
        });
        ui.tfPosition.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            ui.transPerson = !newValue.equals(ui.oldEmployee.getPosition());
        });
        PromptDialogHelper.start("","").setContent(uiPackage.getComponent()).createAndShow();
        ((EmployeeModificationUiController)uiPackage.getController()).runnable = runnable;
    }

    private EmployeeVo getCurrentEmployeeVo() {
        switch (tfPosition.getValue()) {
            case Admin:
                return new AdminVo(tfId.getText(),tfName.getText(),DateHelper.fromLocalDate(tfWorkDate.getValue()),tfPassword.getText(),tfState.getValue());
            case Manager:
                return new ManagerVo(tfId.getText(),tfName.getText(),DateHelper.fromLocalDate(tfWorkDate.getValue()),tfPassword.getText(),tfState.getValue());
            case InventoryStaff:
                return new InventoryStaffVo(tfId.getText(),tfName.getText(),DateHelper.fromLocalDate(tfWorkDate.getValue()),tfPassword.getText(),tfState.getValue());
            case SaleStaff:
                return new SaleStaffVo(tfId.getText(),tfName.getText(),DateHelper.fromLocalDate(tfWorkDate.getValue()),tfPassword.getText(),tfState.getValue(),boolRoot(tfRoot.getVisibleRowCount()),tfSaleType.getValue());
            case FinanceStaff:
                return new FinanceStaffVo(tfId.getText(),tfName.getText(),DateHelper.fromLocalDate(tfWorkDate.getValue()),tfPassword.getText(),tfState.getValue(),boolRoot(tfRoot.getVisibleRowCount()));
                default:
                    throw new NotCompleteException();
        }

    }

    public void onBtnSubmitClicked() {
        if (tfName.getText().length() == 0) {
            PromptDialogHelper.start("提交失败！", "请先输入职员姓名。")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
        else if (tfPassword.getText().length() == 0) {
            PromptDialogHelper.start("提交失败！", "请先输入职员登录密码。")
                    .addCloseButton("好的", "CHECK", null)
                    .createAndShow();
            throw new NotCompleteException();
        }
        else {
            if(transPerson){
                blService.delete(oldEmployee);
                blService.add(getCurrentEmployeeVo());
            }
            else {
                blService.modify(getCurrentEmployeeVo());
            }
            PromptDialogHelper.start("修改成功！","职员信息已修改。")
                    .addCloseButton("好的","CHECK",e -> close())
                    .createAndShow();
        }
    }

    public void onBtnCancelClicked() {
        close();
    }

    private void close() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
        runnable.run();
    }

    private boolean boolRoot(int index){
        return index!=0;
    }

    private LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        return instant.atZone(zoneId).toLocalDate();
    }
}
