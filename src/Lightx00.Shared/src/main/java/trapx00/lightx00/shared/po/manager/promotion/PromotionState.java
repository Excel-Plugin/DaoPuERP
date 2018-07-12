package trapx00.lightx00.shared.po.manager.promotion;

public enum PromotionState {
    Draft("草稿"),
    Waiting("等待生效"),
    Active("生效中"),
    Overdue("已过期"),
    Abandoned("已作废");

    PromotionState(String chinese) {
        this.chinese = chinese;
    }

    private String chinese;

    @Override
    public String toString() {
        return chinese;
    }
}

