package trapx00.lightx00.client.presentation.adminui;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import trapx00.lightx00.client.vo.EmployeeVo;

public class EmployeeSelectionItemModel extends RecursiveTreeObject<EmployeeSelectionItemModel> {
    public ObjectProperty<EmployeeVo> employeeVoObjectProperty;

    public EmployeeSelectionItemModel(EmployeeVo employee) {
        this.employeeVoObjectProperty = new SimpleObjectProperty<>(employee);
    }

    public EmployeeVo getEmployeeVoObjectProperty() {
        return employeeVoObjectProperty.get();
    }

    public ObjectProperty<EmployeeVo> employeeVoObjectPropertyProperty() {
        return employeeVoObjectProperty;
    }

    public void setEmployeeVoObjectProperty(EmployeeVo employee) {
        this.employeeVoObjectProperty.set(employee);
    }
}
