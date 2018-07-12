package trapx00.lightx00.shared.exception.faceid;

public class NetworkException extends RuntimeException {
    private int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public NetworkException(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "NetworkException{" +
            "statusCode=" + statusCode +
            "} " + super.toString();
    }
}
