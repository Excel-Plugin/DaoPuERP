package trapx00.lightx00.shared.exception.database;

import trapx00.lightx00.shared.po.manager.promotion.PromotionState;

public class PromotionInvalidStateException extends DatabaseException {
    private PromotionState[] expecteds;
    private PromotionState actual;

    public PromotionInvalidStateException(PromotionState actual, PromotionState... expecteds) {
        this.actual = actual;
        this.expecteds = expecteds;
    }

    public PromotionState[] getExpecteds() {
        return expecteds;
    }

    public PromotionState getActual() {
        return actual;
    }
}
