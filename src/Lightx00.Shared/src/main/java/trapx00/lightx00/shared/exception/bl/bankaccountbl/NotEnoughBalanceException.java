package trapx00.lightx00.shared.exception.bl.bankaccountbl;

public class NotEnoughBalanceException extends RuntimeException {
    private double balance;
    private double intendedDelta;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NotEnoughBalanceException(double balance, double intendedDelta) {
        this.balance = balance;
        this.intendedDelta = intendedDelta;
    }

    public double getBalance() {
        return balance;
    }

    public double getIntendedDelta() {
        return intendedDelta;
    }

    @Override
    public String toString() {
        return "NotEnoughBalanceException{" +
            "balance=" + balance +
            ", intendedDelta=" + intendedDelta +
            "} " + super.toString();
    }
}
