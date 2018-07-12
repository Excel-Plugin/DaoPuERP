package trapx00.lightx00.client.vo;

import trapx00.lightx00.shared.po.employee.EmployeePosition;
import trapx00.lightx00.shared.po.employee.EmployeeState;

import java.util.Date;

public abstract class EmployeeVo {
    private String id;
    private String name;
    private Date workSince;
    private EmployeePosition position;
    private String password;
    private EmployeeState state;

    public EmployeeVo(String id, String name, Date workSince, EmployeePosition position, String password, EmployeeState state) {
        this.name = name;
        this.id = id;
        this.workSince = workSince;
        this.position = position;
        this.password = password;
        this.state = state;
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

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public EmployeeState getState() {
        return state;
    }

    public void setState(EmployeeState state) {
        this.state = state;
    }
}
