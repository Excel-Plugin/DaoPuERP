package trapx00.lightx00.shared.po;

public enum ResultMessage {
    Success,
    Failure;

    public boolean isSuccess() {
        return this.equals(ResultMessage.Success);
    }

}
