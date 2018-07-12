package trapx00.lightx00.shared.po.employee;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.util.Date;

public class EmployeePo implements Serializable {
    @DatabaseField
    private String name;
    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private Date workSince;
    @DatabaseField
    private String password;
    @DatabaseField
    private EmployeePosition position;
    @DatabaseField
    private EmployeeState state;

    public EmployeePo(String id, String name, Date workSince, EmployeePosition position, String password, EmployeeState state) {
        this.name = name;
        this.id = id;
        this.workSince = workSince;
        this.password = password;
        this.position = position;
        this.state = state;
    }

    public EmployeePo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getWorkSince() {
        return workSince;
    }

    public void setWorkSince(Date workSince) {
        this.workSince = workSince;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public EmployeeState getState() {
        return state;
    }

    public void setState(EmployeeState state) {
        this.state = state;
    }
}
