package trapx00.lightx00.shared.po.salestaff;

public enum SaleStaffType {
    Manager("销售主管"),
    Staff("销售员");

    SaleStaffType(String chinese) {
        this.chinese = chinese;
    }
    private String chinese;

    @Override
    public String toString() {
        return chinese;
    }

}
