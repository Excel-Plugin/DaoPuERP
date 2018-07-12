package trapx00.lightx00.shared.exception.database;

import trapx00.lightx00.shared.po.bill.BillState;

public class BillInvalidStateException extends DatabaseException {
    private BillState[] expecteds;
    private BillState actual;

    public BillInvalidStateException(BillState actual, BillState... expecteds) {
        this.actual = actual;
        this.expecteds = expecteds;
    }

    public BillState[] getExpecteds() {
        return expecteds;
    }

    public BillState getActual() {
        return actual;
    }
}
