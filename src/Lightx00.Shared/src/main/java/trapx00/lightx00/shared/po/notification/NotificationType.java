package trapx00.lightx00.shared.po.notification;

public enum NotificationType {
    BillApproval("单据审批"),
    Others("其他通知");

    private String chinese;

    NotificationType(String chinese) {
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return chinese;
    }
}
