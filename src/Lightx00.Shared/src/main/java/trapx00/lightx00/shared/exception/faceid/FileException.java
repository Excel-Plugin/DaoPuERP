package trapx00.lightx00.shared.exception.faceid;

import java.io.IOException;

public class FileException extends RuntimeException {
    private IOException ioException;

    public IOException getIoException() {
        return ioException;
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public FileException(IOException ioException) {
        this.ioException = ioException;
    }
}
