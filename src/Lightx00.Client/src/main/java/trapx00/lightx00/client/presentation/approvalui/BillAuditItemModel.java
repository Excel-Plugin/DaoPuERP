package trapx00.lightx00.client.presentation.approvalui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.UserManagementBlFactory;
import trapx00.lightx00.client.bl.financebl.BillInfo;
import trapx00.lightx00.client.bl.financebl.factory.BillInfoBlFactory;
import trapx00.lightx00.client.vo.BillVo;
import trapx00.lightx00.client.vo.manager.AuditIdVo;
import trapx00.lightx00.shared.po.bill.BillType;

import java.util.Date;

public class BillAuditItemModel extends RecursiveTreeObject<BillAuditItemModel> {
    private ObjectProperty<Date> date;
    private StringProperty id;
    private StringProperty operator;
    private ObjectProperty<BillType> type;
    private ObjectProperty<BillVo> bill;

    public BillAuditItemModel(AuditIdVo auditIdVo) {
        BillInfo billInfo = BillInfoBlFactory.getBillInfo();
        BillVo bill = billInfo.queryBill(auditIdVo.getId());
        EmployeeInfo employeeInfo = UserManagementBlFactory.getController();
        date = new SimpleObjectProperty<>(auditIdVo.getApprovalTime());
        id = new SimpleStringProperty(bill.getId());
        operator = new SimpleStringProperty(employeeInfo.queryById(bill.getOperatorId()).getName());
        type = new SimpleObjectProperty<>(bill.getBillType());
        this.bill = new SimpleObjectProperty<>(bill);
    }

    public Date getDate() {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getOperator() {
        return operator.get();
    }

    public StringProperty operatorProperty() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator.set(operator);
    }

    public BillType getType() {
        return type.get();
    }

    public ObjectProperty<BillType> typeProperty() {
        return type;
    }

    public void setType(BillType type) {
        this.type.set(type);
    }

    public BillVo getBill() {
        return bill.get();
    }

    public ObjectProperty<BillVo> billProperty() {
        return bill;
    }

    public void setBill(BillVo bill) {
        this.bill.set(bill);
    }
}

