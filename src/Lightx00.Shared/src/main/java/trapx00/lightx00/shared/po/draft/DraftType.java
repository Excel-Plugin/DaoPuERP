package trapx00.lightx00.shared.po.draft;

public enum DraftType {
    Bill("单据"),
    Promotion("促销策略"),
    Client("客户");
    
    private String chinese;

    DraftType(String chinese){
        this.chinese = chinese;
    }


    @Override
    public String toString() {
        return chinese;
    }
}
