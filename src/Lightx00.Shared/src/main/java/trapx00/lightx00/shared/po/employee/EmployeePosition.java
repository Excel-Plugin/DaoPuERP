package trapx00.lightx00.shared.po.employee;

public enum EmployeePosition {
    InventoryStaff("库存管理部"),
    SaleStaff("进货销售部"),
    FinanceStaff("财务部"),
    Manager("总经理"),
    Admin("系统管理");

    EmployeePosition(String chinese) {
        this.chinese = chinese;
    }

    private String chinese;

    @Override
    public String toString() {
        return chinese;
    }
}
