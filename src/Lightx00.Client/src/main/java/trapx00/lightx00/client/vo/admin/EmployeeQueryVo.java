package trapx00.lightx00.client.vo.admin;

public class EmployeeQueryVo { //职员管理查询条件
    private String name;

    public EmployeeQueryVo(String name) {
        this.name = name;
    }

    public EmployeeQueryVo() {}

    public void setName(String name) {
        this.name = name;
    }
}
