package trapx00.lightx00.shared.po.log;

public enum LogSeverity {
    Info("信息"),
    Success("成功"),
    Warning("警告"),
    Failure("失败");

    private String chinese;

    LogSeverity(String chinese) {
        this.chinese = chinese;
    }


    @Override
    public String toString() {
        return chinese;
    }
}
