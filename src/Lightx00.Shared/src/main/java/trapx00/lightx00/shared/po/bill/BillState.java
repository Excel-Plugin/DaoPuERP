package trapx00.lightx00.shared.po.bill;

public enum BillState {
    Draft("草稿"),
    WaitingForApproval("等待审批"),
    Rejected("未通过"),
    Approved("已通过"),
    Activated("已入账"),
    Abandoned("已废弃");

    private String chinese;

    BillState(String chinese) {
        this.chinese = chinese;
    }


    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return chinese;
    }
}
