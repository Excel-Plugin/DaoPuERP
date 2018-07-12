package trapx00.lightx00.client.presentation.adminui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import trapx00.lightx00.client.bl.adminbl.EmployeeInfo;
import trapx00.lightx00.client.bl.adminbl.factory.EmployeeInfoFactory;
import trapx00.lightx00.client.vo.EmployeeVo;
import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;

public class EmployeeInfoModel extends RecursiveTreeObject<EmployeeInfoModel> {
    private StringProperty id;
    private StringProperty name;
    private ObjectProperty<Date> workDate;
    private ObjectProperty<EmployeePosition> position;
    private ObjectProperty<EmployeeState> state;

    public EmployeeInfoModel(EmployeeVo employeeVo) {
        this.id = new SimpleStringProperty(employeeVo.getId());
        this.name = new SimpleStringProperty(employeeVo.getName());
        this.workDate = new SimpleObjectProperty<>(employeeVo.getWorkSince());
        this.position = new SimpleObjectProperty<>(employeeVo.getPosition());
        this.state = new SimpleObjectProperty<>(employeeVo.getState());
    }

    public EmployeeVo toEmployeeVo() {
        EmployeeInfo employeeInfo = EmployeeInfoFactory.getEmployeeInfo();
        return employeeInfo.queryById(id.getValue());
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public EmployeePosition getPosition() {
        return position.get();
    }

    public ObjectProperty<EmployeePosition> positionProperty() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position.set(position);
    }

    public EmployeeState getState() {
        return state.get();
    }

    public ObjectProperty<EmployeeState> stateProperty() {
        return state;
    }

    public void setState(EmployeeState state) {
        this.state.set(state);
    }

    public Date getWorkDate() {
        return workDate.get();
    }

    public ObjectProperty<Date> workDateProperty() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate.set(workDate);
    }
}
