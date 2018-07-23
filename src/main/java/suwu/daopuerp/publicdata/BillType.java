package suwu.daopuerp.publicdata;

public enum BillType {
    OIL("油", "A"),
    LIQUID("液", "B");

    private String name;
    private String code;

    BillType(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
