package suwu.daopuerp.publicdata;

public enum BillType {
    OIL("油"),
    LIQUID("液");

    private String name;

    BillType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
