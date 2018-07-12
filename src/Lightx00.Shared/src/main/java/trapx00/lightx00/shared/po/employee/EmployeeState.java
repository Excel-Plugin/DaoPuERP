package trapx00.lightx00.shared.po.employee;

public enum EmployeeState {
    Practice("实习生"),
    Active("正式职员"),
    Dismissed("已离职");

    private String chinese;

    EmployeeState(String chinese) {
        this.chinese = chinese;
    }

    @Override
    public String toString(){
        return chinese;
    }

}
