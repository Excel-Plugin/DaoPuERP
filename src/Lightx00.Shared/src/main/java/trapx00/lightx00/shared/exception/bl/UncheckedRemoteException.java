package trapx00.lightx00.shared.exception.bl;

import java.rmi.RemoteException;

public class UncheckedRemoteException extends RuntimeException {
    private RemoteException remoteException;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public UncheckedRemoteException(RemoteException remoteException) {
        this.remoteException = remoteException;
    }

    public RemoteException getRemoteException() {
        return remoteException;
    }
}
